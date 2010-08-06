package test.gov.nih.nci.wiki;

import gov.nih.nci.bda.certification.util.HibernateUtil;
import gov.nih.nci.wiki.History;
import gov.nih.nci.wiki.ProjectCertificationStatus;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import org.hibernate.Session;

import java.util.Date;

/**
 * History Tester.
 *
 * @author <Authors name>
 * @since <pre>07/16/2010</pre>
 * @version 1.0
 */
public class HistoryTest extends TestCase {
    public HistoryTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     *
     * Method: getMostRecent()
     *
     */
    public void testGetMostRecent() throws Exception {
        Session s = HibernateUtil.getSession();
        org.hibernate.Query q = s.createQuery(" from ProjectCertificationStatus ");
        String projectName = "xxx" ;
        s.beginTransaction();
        ProjectCertificationStatus testStatus = new ProjectCertificationStatus();
        testStatus.setProduct(projectName);
        testStatus.setCertificationStatus( History.WIKI_CERTIFICATION_GREEN );
        Date d = new Date();
        testStatus.setCertificationDate(d);
        s.save(testStatus);
        s.getTransaction().commit();

        try {

            History target = new History();
            ProjectCertificationStatus recent = target.getMostRecentSuccess(projectName);
            assertNotNull(recent);
            assertEquals(d,recent.getCertificationDate());
            
        }
        finally {

        }
    }

    public void testGetsNothing() throws Exception {
        String projectName = "zzzzzzzzzzzzzzzz" ;

        try {

            History target = new History();
            ProjectCertificationStatus recent = target.getMostRecentSuccess(projectName);
            assertNull(recent);
        }
        finally {

        }
    }


    public static Test suite() {
        return new TestSuite(HistoryTest.class);
    }
}
