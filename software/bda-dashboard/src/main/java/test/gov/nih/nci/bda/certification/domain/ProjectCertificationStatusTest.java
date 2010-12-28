package test.gov.nih.nci.bda.certification.domain;

import gov.nih.nci.bda.certification.domain.ProjectCertificationStatus;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

/**
 * ProjectCertificationStatus Tester.
 *
 * @author <Authors name>
 * @since <pre>08/03/2010</pre>
 * @version 1.0
 */
public class ProjectCertificationStatusTest extends TestCase {
    public ProjectCertificationStatusTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public static Test suite() {
        return new TestSuite(ProjectCertificationStatusTest.class);
    }


    private static final String TEST_PROJECT_NAME = "test";
    private static final String TEST_PROJECT_URL = "http://NotAValidSVNUrl.com" ;
//    proves that setting the projectname and projecturl
//    correctly sets the product
    public void testSetProduct() {

        ProjectCertificationStatus target = new ProjectCertificationStatus();
        target.setProduct(TEST_PROJECT_NAME,TEST_PROJECT_URL);
        String expected = "[" + TEST_PROJECT_NAME + "|" + TEST_PROJECT_URL + "]";
        String actual = target.getProduct();
        assertEquals(expected,actual);
        

    }

    // proves that certificationStatus is not null
    // after instantiation
    public void testCertificationStatusNotNull() {
        ProjectCertificationStatus target = new ProjectCertificationStatus();
        assertNotNull(target.getCertificationStatus());
    }

    // proves that getCommandLineInstaller is not null
    // after instantiation
    public void testGetCommandLineInstallerNotNull() {
        ProjectCertificationStatus target = new ProjectCertificationStatus();
        assertNotNull(target.getCommandLineInstaller());
    }

    // proves that getDatabaseIntegration is not null
    // after instantiation
    public void testGetDatabaseIntegrationNotNull() {
        ProjectCertificationStatus target = new ProjectCertificationStatus();
        assertNotNull(target.getDatabaseIntegration());
    }

    // proves that getDeploymentShakeout is not null
    // after instantiation
    public void testGetDeploymentShakeoutNotNull() {
        ProjectCertificationStatus target = new ProjectCertificationStatus();
        assertNotNull(target.getDeploymentShakeout());
    }
    
    // proves that getLatestCIBuild is not null
    // after instantiation
    public void testGetLatestCIBuildNotNull() {
        ProjectCertificationStatus target = new ProjectCertificationStatus();
        assertNotNull(target.getLatestCIBuild());
    }

    // proves that getPrivateRepositoryProperties is not null
    // after instantiation
    public void testGetPrivateRepositoryPropertiesNotNull() {
        ProjectCertificationStatus target = new ProjectCertificationStatus();
        assertNotNull(target.getPrivateRepositoryProperties());
    }

    // proves that getRemoteUpgrade is not null
    // after instantiation
    public void testGetRemoteUpgradeNotNull() {
        ProjectCertificationStatus target = new ProjectCertificationStatus();
        assertNotNull(target.getRemoteUpgrade());
    }
    
    // proves that getSingleCommandBuild is not null
    // after instantiation
    public void testGetSingleCommandBuildNotNull() {
        ProjectCertificationStatus target = new ProjectCertificationStatus();
        assertNotNull(target.getSingleCommandBuild());
    }
    
    // proves that getSingleCommandDeployment is not null
    // after instantiation
    public void testGetSingleCommandDeploymentNotNull() {
        ProjectCertificationStatus target = new ProjectCertificationStatus();
        assertNotNull(target.getSingleCommandDeployment());
    }
    
    // proves that getTemplateValidation is not null
    // after instantiation
    public void testGetTemplateValidationNotNull() {
        ProjectCertificationStatus target = new ProjectCertificationStatus();
        assertNotNull(target.getTemplateValidation());
    }
    
}
