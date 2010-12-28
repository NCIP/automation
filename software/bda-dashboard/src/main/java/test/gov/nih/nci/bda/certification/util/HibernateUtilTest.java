package test.gov.nih.nci.bda.certification.util;

import gov.nih.nci.bda.certification.util.HibernateUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Aug 4, 2010
 * Time: 6:26:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateUtilTest extends TestCase {

    public HibernateUtilTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }


    public static Test suite() {
        return new TestSuite(HibernateUtilTest.class);
    }

//    public void testConfigHoldsConnectionUrl() throws FileNotFoundException {
//
//        String expected = "jdbc:mysql://localhost/testDatabase";
//        HibernateUtil.getConfiguration().setProperty("connection.url", expected);
//        assertEquals(expected, HibernateUtil.getConfiguration().getProperty("connection.url"));
//    }


    // this test proves that when setting the connectionUrl
    // the session is closed and a new session object is created
    public void testConfigEffectsSession() throws FileNotFoundException {

        Session session1 = HibernateUtil.getSession();
        Query q = session1.createSQLQuery("SELECT now()");
        Object o = q.uniqueResult();
        assertTrue(session1.isOpen());
        String expected = "jdbc:mysql://localhost/testDatabase";
        HibernateUtil.setConnectionUrl(expected);
        Session session2 = HibernateUtil.getSession();
        assertFalse(session1.isOpen());
        assertNotSame(session1,session2);
    }
}

