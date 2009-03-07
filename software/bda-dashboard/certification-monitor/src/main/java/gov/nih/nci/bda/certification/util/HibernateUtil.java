package gov.nih.nci.bda.certification.util;
  import java.io.File;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

  public class HibernateUtil 
  {	  
        private static final SessionFactory sessionFactory;
        private static Configuration configuration;
        public static final ThreadLocal session = new ThreadLocal();
        
        static {
            try {
                // Create the SessionFactory from hibernate.cfg.xml
            	File f =new File("hibernate.cfg.xml");
            	System.out.println("Absolute Path::"+f.getAbsolutePath());
            	configuration = new Configuration().configure(f);
                sessionFactory = configuration.buildSessionFactory();
            } catch (Throwable ex) {
                // Make sure you log the exception, as it might be swallowed
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        public static SessionFactory getSessionFactory() {
            return sessionFactory;
        }

        public static Session getSession() throws HibernateException {
               Session s = (Session) session.get();
               // Open a new Session, if this thread has none yet
               if (s == null) {
                   s = sessionFactory.openSession();
                   // Store it in the ThreadLocal variable
                   session.set(s);
               }
               return s;
           }

           public static void closeSession() throws HibernateException {
               Session s = (Session) session.get();
               if (s != null)
                   s.close();
               session.set(null);
           }


       /**
            * This is a simple method to reduce the amount of
       code that needs
            * to be written every time hibernate is used.
            */
           public static void rollback(org.hibernate.Transaction
       tx) {
               if (tx != null) {
                   try {
                       tx.rollback( );
                   }
                   catch (HibernateException ex) {
                	   //handle the exception
                   }
               }
           }
       /**
            * This is a simple method to reduce the amount of
       code that needs
            * to be written every time hibernate is used.
            */
           public static void commit(org.hibernate.Transaction tx) {
               if (tx != null) {
                   try {
                       tx.commit( );
                   }
                   catch (HibernateException ex) {
                	   //handle the exception
                   }
               }
           }

           /**
            * @return the configuration
            */
           public static Configuration getConfiguration() {
               return configuration;
           }

 
  }
