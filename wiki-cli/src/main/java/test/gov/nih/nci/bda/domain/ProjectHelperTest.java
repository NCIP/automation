package test.gov.nih.nci.bda.domain;

import gov.nih.nci.bda.certification.util.HibernateUtil;
import gov.nih.nci.bda.domain.Project;
import gov.nih.nci.bda.domain.ProjectHelper;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * ProjectHelper Tester.
 *
 * @author <Authors name>
 * @since <pre>08/06/2010</pre>
 * @version 1.0
 */
public class ProjectHelperTest extends TestCase {
    public ProjectHelperTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public static Test suite() throws FileNotFoundException {
        HibernateUtil.setConnectionUrl("jdbc:mysql://localhost/test");
        return new TestSuite(ProjectHelperTest.class);
    }

    // proves that calling getByName for a
    // non-existing project returns a null
    public void testGetByName() throws Exception {
        String projectName = UUID.randomUUID().toString();
        Project actual = ProjectHelper.getByName(projectName);
        assertNull(actual);
    }

    // proves that calling getByName for an
    // existing project returns a project
    public void testGetByNameReturnsProject() throws FileNotFoundException {

        Project actual = ProjectHelper.getByName("caarray");
        assertNotNull(actual);
        assertEquals("caarray", actual.getName());
        assertEquals("caArray",actual.getFriendlyName());
    }




}
