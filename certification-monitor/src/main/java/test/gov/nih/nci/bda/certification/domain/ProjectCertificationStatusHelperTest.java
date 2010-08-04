package test.gov.nih.nci.bda.certification.domain;

import gov.nih.nci.bda.certification.domain.ProjectCertificationStatus;
import gov.nih.nci.bda.certification.domain.ProjectCertificationStatusHelper;
import gov.nih.nci.bda.certification.util.HibernateUtil;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import org.hibernate.id.GUIDGenerator;

import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * ProjectCertificationStatusHelper Tester.
 *
 * @author <Authors name>
 * @since <pre>08/03/2010</pre>
 * @version 1.0
 */
public class ProjectCertificationStatusHelperTest extends TestCase {


    public ProjectCertificationStatusHelperTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    private static final String TEST_PROJECT_NAME = "testproject";
    private static final String TEST_PROJECT_URL = "http://NotAValidSvnUrl.com" ;

    /**
     *
     * Method: getByProjectName(String projectName, String projectUrl)
     *
     */
    // proves that calling getProject will create the project
    public void testGetProject() throws Exception {

        String projectName = UUID.randomUUID().toString();
        ProjectCertificationStatus expected = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL);
        assertNotNull(expected);
    }


    // this test proves that you can call
    // getProject with noCreate=true and still get a null back
    public void testGetProjectNoCreateReturnsNull() {

        String projectName = UUID.randomUUID().toString();

        ProjectCertificationStatus expected =
                ProjectCertificationStatusHelper.getProject(
                        projectName
                        ,TEST_PROJECT_URL
                        , false);

        assertNull(expected);

    }


    // this test proves you can call getProject with
    // allowCreate = true and it creates the project
    public void testGetProjectCreates() {

        String projectName = UUID.randomUUID().toString();
        ProjectCertificationStatus expected =
                ProjectCertificationStatusHelper.getProject(
                        projectName
                        ,TEST_PROJECT_URL
                        , true);

        assertNotNull(expected);

    }

    public static Test suite() {
        return new TestSuite(ProjectCertificationStatusHelperTest.class);
    }
}
