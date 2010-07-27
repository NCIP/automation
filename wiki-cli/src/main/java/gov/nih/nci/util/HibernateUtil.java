package gov.nih.nci.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtil {
	private static Log certLogger = LogFactory.getLog(HibernateUtil.class);
	private static Configuration configuration;
	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();
	private static final SessionFactory sessionFactory;

	static {
		try {
            File x = new File(".");
            System.out.println(x.getAbsolutePath());
			certLogger.info("Create the SessionFactory from hibernate.cfg.xml");

			File f = new File("src/main/resources/hibernate.cfg.xml");
            if (!f.exists())
            {
                f = new File("hibernate.cfg.xml");
            }
            if (!f.exists())
            {
                throw new ExceptionInInitializerError("Cannot find hibernate.cfg.xml");
            }
			configuration = new Configuration().configure(f);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Throwable ex) {
            System.out.print(ex.toString());
//			certLogger.error("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

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

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static Session getSession() throws HibernateException {
		Session s = session.get();
		certLogger.info("Open a new Session, if this thread has none yet");
		if (s == null) {
			s = sessionFactory.openSession();
			certLogger.info("Store session in  ThreadLocal variable");
			session.set(s);
		}
		return s;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void rollback(org.hibernate.Transaction tx) {
		if (tx != null) {
			try {
				tx.rollback();
			} catch (HibernateException ex) {
				certLogger.error("Exception Occured while rolling the transaction :: " + ex.getMessage());
			}
		}
	}

}
