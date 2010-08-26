package gov.nih.nci.bda.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author narram
 */
public class HibernateUtil {
    private static Log certLogger = LogFactory.getLog(HibernateUtil.class);
    private static Configuration configuration;
    public static ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static SessionFactory sessionFactory;

    public static void closeSession() throws HibernateException {
        Session s = session.get();
        if (s != null)
            s.close();
        session.set(null);
    }


    @SuppressWarnings("unused")
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
                f = new File("software/src/hibernate.cfg.xml");
            }
            if (!f.exists()) {
                f = new File("src/hibernate.cfg.xml");
            }

            if (!f.exists()) {
                File root = new File(".");
                throw new FileNotFoundException("hibernate.cfg.xml in " + root.getAbsolutePath());
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

    @SuppressWarnings("unused")
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