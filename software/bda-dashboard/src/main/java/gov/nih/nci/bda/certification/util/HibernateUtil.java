package gov.nih.nci.bda.certification.util;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

/**
 * @author narram
 */
public class HibernateUtil {
    private static Log certLogger = LogFactory.getLog(HibernateUtil.class);
    private static Configuration configuration;
    public static ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static SessionFactory sessionFactory;
    private static String connectionUrl;

    public static void closeSession() throws HibernateException {
        Session s = session.get();
        if (s != null)
            s.close();
        session.set(null);
    }

    public static void commit(org.hibernate.Transaction tx) {
        if (tx != null) {
            try {
                tx.commit();
            } catch (HibernateException ex) {
                certLogger.error("Exception Occured while commiting the transaction :: " + ex.getMessage());
            }
        }
    }

    public static Configuration getConfiguration() throws FileNotFoundException {
        if (configuration == null) {
            File f = new File("hibernate.cfg.xml");
            if (!f.exists()) {
                f = new File("src/main/resources/hibernate.cfg.xml");
            }
            if (!f.exists()) {
                throw new FileNotFoundException("hibernate.cfg.xml");
            }
            configuration = new Configuration().configure(f);
        }

        return configuration;
    }

    public static Session getSession() throws HibernateException, FileNotFoundException {

        Session s = session.get();

        certLogger.info("Open a new Session, if this thread has none yet");

        if (s == null) {
            s = getSessionFactory().openSession();
            certLogger.info("Store session in  ThreadLocal variable");
            session.set(s);
        }
        return s;
    }

    private static SessionFactory getSessionFactory() throws FileNotFoundException {
        if (sessionFactory == null) {
            sessionFactory = getConfiguration().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void rollback(org.hibernate.Transaction tx) {
        if (tx != null) {
            try {
                tx.rollback();
            } catch (HibernateException ex) {
                certLogger.error("Exception Occurred while rolling the transaction :: " + ex.getMessage());
            }
        }
    }

    public static void setConnectionUrl(String url) throws FileNotFoundException {
        closeSession();
        sessionFactory = null;
        configuration = null;
        getConfiguration().setProperty("connection.url",url);
    }
}
