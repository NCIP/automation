package test.gov.nih.nci.bda.domain;

import gov.nih.nci.bda.domain.ProjectActionType;
import gov.nih.nci.bda.domain.ProjectActionTypeHelper;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * ProjectActionTypeHelper Tester.
 *
 * @author <Authors name>
 * @since <pre>08/06/2010</pre>
 * @version 1.0
 */
public class ProjectActionTypeHelperTest extends TestCase {
    public ProjectActionTypeHelperTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public static Test suite() {
        return new TestSuite(ProjectActionTypeHelperTest.class);
    }

    public void testGetById() throws Exception {
        ProjectActionType actual = ProjectActionTypeHelper.getById(0);
        assertNull(actual);
    }

    public void testGetByDescription() throws FileNotFoundException {
        HibernateUtil.setConnectionUrl("jdbc:mysql://localhost/test");
        Session s = HibernateUtil.getSession();
        String patDescription = UUID.randomUUID().toString();
        ProjectActionType expected = new ProjectActionType();
        s.clear();
        expected.setDescription(patDescription);
        Transaction t = s.beginTransaction();
        s.save(expected);
        t.commit();

        ProjectActionType actual = ProjectActionTypeHelper.getByDescription(patDescription);

        assertNotNull(actual);
        assertEquals(patDescription, actual.getDescription());
    }
}
