package test.gov.nih.nci.bda.certification;

import gov.nih.nci.bda.certification.BuildCertificationConstants;
import gov.nih.nci.bda.certification.CertificationManager;
import gov.nih.nci.bda.certification.business.BuildCertificationBean;
import gov.nih.nci.bda.certification.domain.ProjectCertificationStatus;
import gov.nih.nci.bda.certification.domain.ProjectCertificationStatusHelper;
import gov.nih.nci.bda.certification.domain.TargetLookup;
import gov.nih.nci.bda.certification.util.HibernateUtil;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.tools.ant.Project;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.UUID;

/**
 * CertificationManager Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>07/21/2010</pre>
 */
public class CertificationManagerTest extends TestCase {
    public CertificationManagerTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public static Test suite() {
        return new TestSuite(CertificationManagerTest.class);
    }

    public void testLoadSavePropertiesNoFile() throws ConfigurationException {
        CertificationManager target = new CertificationManager();
        target.loadSavedProperties("x.xxxx",new Project(), "blah");
    }

    private static final String TEST_PROJECT_NAME = "testproject";
    private static final String TEST_PROJECT_URL = "http://NotAValidSvnUrl.com" ;

    public void testMarkAllAsFailedBdaEnabled() throws FileNotFoundException {
        String projectName = UUID.randomUUID().toString();
        CertificationManager target = new CertificationManager();
        target.markAllAsFailed(projectName,TEST_PROJECT_URL);

        ProjectCertificationStatus projectStatus = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL,false);
        
        assertNotNull(projectStatus);
        assertTrue(projectStatus.getBdaEnabled().contains(BuildCertificationConstants.WIKI_FAILED));
        assertTrue(projectStatus.getBdaEnabled().contains(BuildCertificationConstants.CERTIFICATION_INCOMPLETE));
        assertTrue(projectStatus.getBdaEnabled().contains("http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-" + projectName + "/lastBuild/console"));
        assertEquals("'[(x)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-" + projectName + "/lastBuild/console|Status unknown; certification build incomplete (canceled or failed).]'",projectStatus.getBdaEnabled());
        
    }

    public void testMarkAllAsFailedCertificationStatus() throws FileNotFoundException {
        String projectName = UUID.randomUUID().toString();
        CertificationManager target = new CertificationManager();
        target.markAllAsFailed(projectName,TEST_PROJECT_URL);

        ProjectCertificationStatus projectStatus = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL,false);

        assertNotNull(projectStatus);
        assertTrue("getCertificationStatus does not contain '" + BuildCertificationConstants.WIKI_FAILED + "' as expected.", projectStatus.getCertificationStatus().contains(BuildCertificationConstants.WIKI_FAILED));
        assertTrue("getCertificationStatus does not contain '" + BuildCertificationConstants.CERTIFICATION_INCOMPLETE + "' as expected.", projectStatus.getCertificationStatus().contains(BuildCertificationConstants.CERTIFICATION_INCOMPLETE));
        assertTrue("getCertificationStatus does not contain '" + "http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-" + projectName + "/lastBuild/console" + "' as expected.", projectStatus.getCertificationStatus().contains("http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-" + projectName + "/lastBuild/console"));
        assertEquals("'[(x)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-" + projectName + "/lastBuild/console|Status unknown; certification build incomplete (canceled or failed).]'",projectStatus.getCertificationStatus());

    }

    // proves that MarkAllAsFailed() correctly marks CommandLineInstaller
    // as failed with the correct wikitip
    public void testMarkAllAsFailedCommandLineInstaller() throws FileNotFoundException {
        String projectName = UUID.randomUUID().toString();
        CertificationManager target = new CertificationManager();
        target.markAllAsFailed(projectName,TEST_PROJECT_URL);

        ProjectCertificationStatus projectStatus = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL,false);
        String actual = projectStatus.getCommandLineInstaller(); 

        assertNotNull(projectStatus);

        assertTrue( "getCommandLineInstaller does not contain '"
                    + BuildCertificationConstants.WIKI_FAILED
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.WIKI_FAILED));

        assertTrue( "getCommandLineInstaller does not contain '"
                    + BuildCertificationConstants.CERTIFICATION_INCOMPLETE
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.CERTIFICATION_INCOMPLETE));

        assertTrue( "getCommandLineInstaller does not contain 'http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                    + projectName
                    + "/lastBuild/console' as expected."
                    , actual.contains(  "http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                                        + projectName
                                        + "/lastBuild/console"));

        assertEquals(   "'[(x)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                        + projectName
                        + "/lastBuild/console|Status unknown; certification build incomplete (canceled or failed).]'"
                        ,actual);

    }

    // proves that MarkAllAsFailed() correctly marks DatabaseIntegration
    // as failed with the correct wikitip
    public void testMarkAllAsFailedDatabaseIntegration() throws FileNotFoundException {
        String projectName = UUID.randomUUID().toString();
        CertificationManager target = new CertificationManager();
        target.markAllAsFailed(projectName,TEST_PROJECT_URL);

        ProjectCertificationStatus projectStatus = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL,false);
        String actual = projectStatus.getDatabaseIntegration(); 

        assertNotNull(projectStatus);

        assertTrue( "getDatabaseIntegration does not contain '"
                    + BuildCertificationConstants.WIKI_FAILED
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.WIKI_FAILED));

        assertTrue( "getDatabaseIntegration does not contain '"
                    + BuildCertificationConstants.CERTIFICATION_INCOMPLETE
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.CERTIFICATION_INCOMPLETE));

        assertTrue( "getDatabaseIntegration does not contain 'http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                    + projectName
                    + "/lastBuild/console' as expected."
                    , actual.contains(  "http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                                        + projectName
                                        + "/lastBuild/console"));

        assertEquals(   "'[(x)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                        + projectName
                        + "/lastBuild/console|Status unknown; certification build incomplete (canceled or failed).]'"
                        ,actual);

    }

    // proves that MarkAllAsFailed() correctly marks DeploymentShakeout
    // as failed with the correct wikitip
    public void testMarkAllAsFailedDeploymentShakeout() throws FileNotFoundException {
        String projectName = UUID.randomUUID().toString();
        CertificationManager target = new CertificationManager();
        target.markAllAsFailed(projectName,TEST_PROJECT_URL);

        ProjectCertificationStatus projectStatus = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL,false);
        String actual = projectStatus.getDeploymentShakeout(); 

        assertNotNull(projectStatus);

        assertTrue( "getDeploymentShakeout does not contain '"
                    + BuildCertificationConstants.WIKI_FAILED
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.WIKI_FAILED));

        assertTrue( "getDeploymentShakeout does not contain '"
                    + BuildCertificationConstants.CERTIFICATION_INCOMPLETE
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.CERTIFICATION_INCOMPLETE));

        assertTrue( "getDeploymentShakeout does not contain 'http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                    + projectName
                    + "/lastBuild/console' as expected."
                    , actual.contains(  "http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                                        + projectName
                                        + "/lastBuild/console"));

        assertEquals(   "'[(x)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                        + projectName
                        + "/lastBuild/console|Status unknown; certification build incomplete (canceled or failed).]'"
                        ,actual);

    }

    // proves that MarkAllAsFailed() correctly marks LatestCIBuild
    // as failed with the correct wikitip
    public void testMarkAllAsFailedLatestCIBuild() throws FileNotFoundException {
        String projectName = UUID.randomUUID().toString();
        CertificationManager target = new CertificationManager();
        target.markAllAsFailed(projectName,TEST_PROJECT_URL);

        ProjectCertificationStatus projectStatus = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL,false);
        String actual = projectStatus.getLatestCIBuild(); 

        assertNotNull(projectStatus);

        assertTrue( "getLatestCIBuild does not contain '"
                    + BuildCertificationConstants.WIKI_FAILED
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.WIKI_FAILED));

        assertTrue( "getLatestCIBuild does not contain '"
                    + BuildCertificationConstants.CERTIFICATION_INCOMPLETE
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.CERTIFICATION_INCOMPLETE));

        assertTrue( "getLatestCIBuild does not contain 'http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                    + projectName
                    + "/lastBuild/console' as expected."
                    , actual.contains(  "http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                                        + projectName
                                        + "/lastBuild/console"));

        assertEquals(   "'[(x)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                        + projectName
                        + "/lastBuild/console|Status unknown; certification build incomplete (canceled or failed).]'"
                        ,actual);

    }

    // proves that MarkAllAsFailed() correctly marks PrivateRepositoryProperties
    // as failed with the correct wikitip
    public void testMarkAllAsFailedPrivateRepositoryProperties() throws FileNotFoundException {
        String projectName = UUID.randomUUID().toString();
        CertificationManager target = new CertificationManager();
        target.markAllAsFailed(projectName,TEST_PROJECT_URL);

        ProjectCertificationStatus projectStatus = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL,false);
        String actual = projectStatus.getPrivateRepositoryProperties(); 

        assertNotNull(projectStatus);

        assertTrue( "getPrivateRepositoryProperties does not contain '"
                    + BuildCertificationConstants.WIKI_FAILED
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.WIKI_FAILED));

        assertTrue( "getPrivateRepositoryProperties does not contain '"
                    + BuildCertificationConstants.CERTIFICATION_INCOMPLETE
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.CERTIFICATION_INCOMPLETE));

        assertTrue( "getPrivateRepositoryProperties does not contain 'http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                    + projectName
                    + "/lastBuild/console' as expected."
                    , actual.contains(  "http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                                        + projectName
                                        + "/lastBuild/console"));

        assertEquals(   "'[(x)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                        + projectName
                        + "/lastBuild/console|Status unknown; certification build incomplete (canceled or failed).]'"
                        ,actual);

    }
   
    // proves that MarkAllAsFailed() correctly marks RemoteUpgrade
    // as failed with the correct wikitip
    public void testMarkAllAsFailedRemoteUpgrade() throws FileNotFoundException {
        String projectName = UUID.randomUUID().toString();
        CertificationManager target = new CertificationManager();
        target.markAllAsFailed(projectName,TEST_PROJECT_URL);

        ProjectCertificationStatus projectStatus = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL,false);
        String actual = projectStatus.getRemoteUpgrade(); 

        assertNotNull(projectStatus);

        assertTrue( "getRemoteUpgrade does not contain '"
                    + BuildCertificationConstants.WIKI_FAILED
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.WIKI_FAILED));

        assertTrue( "getRemoteUpgrade does not contain '"
                    + BuildCertificationConstants.CERTIFICATION_INCOMPLETE
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.CERTIFICATION_INCOMPLETE));

        assertTrue( "getRemoteUpgrade does not contain 'http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                    + projectName
                    + "/lastBuild/console' as expected."
                    , actual.contains(  "http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                                        + projectName
                                        + "/lastBuild/console"));

        assertEquals(   "'[(x)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                        + projectName
                        + "/lastBuild/console|Status unknown; certification build incomplete (canceled or failed).]'"
                        ,actual);

    }

    // proves that MarkAllAsFailed() correctly marks SingleCommandBuild
    // as failed with the correct wikitip
    public void testMarkAllAsFailedSingleCommandBuild() throws FileNotFoundException {
        String projectName = UUID.randomUUID().toString();
        CertificationManager target = new CertificationManager();
        target.markAllAsFailed(projectName,TEST_PROJECT_URL);

        ProjectCertificationStatus projectStatus = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL,false);
        String actual = projectStatus.getSingleCommandBuild(); 

        assertNotNull(projectStatus);

        assertTrue( "getSingleCommandBuild does not contain '"
                    + BuildCertificationConstants.WIKI_FAILED
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.WIKI_FAILED));

        assertTrue( "getSingleCommandBuild does not contain '"
                    + BuildCertificationConstants.CERTIFICATION_INCOMPLETE
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.CERTIFICATION_INCOMPLETE));

        assertTrue( "getSingleCommandBuild does not contain 'http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                    + projectName
                    + "/lastBuild/console' as expected."
                    , actual.contains(  "http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                                        + projectName
                                        + "/lastBuild/console"));

        assertEquals(   "'[(x)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                        + projectName
                        + "/lastBuild/console|Status unknown; certification build incomplete (canceled or failed).]'"
                        ,actual);

    }

    // proves that MarkAllAsFailed() correctly marks SingleCommandDeployment
    // as failed with the correct wikitip
    public void testMarkAllAsFailedSingleCommandDeployment() throws FileNotFoundException {
        String projectName = UUID.randomUUID().toString();
        CertificationManager target = new CertificationManager();
        target.markAllAsFailed(projectName,TEST_PROJECT_URL);

        ProjectCertificationStatus projectStatus = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL,false);
        String actual = projectStatus.getSingleCommandDeployment(); 

        assertNotNull(projectStatus);

        assertTrue( "getSingleCommandDeployment does not contain '"
                    + BuildCertificationConstants.WIKI_FAILED
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.WIKI_FAILED));

        assertTrue( "getSingleCommandDeployment does not contain '"
                    + BuildCertificationConstants.CERTIFICATION_INCOMPLETE
                    + "' as expected."
                    , actual.contains(BuildCertificationConstants.CERTIFICATION_INCOMPLETE));

        assertTrue( "getSingleCommandDeployment does not contain 'http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                    + projectName
                    + "/lastBuild/console' as expected."
                    , actual.contains(  "http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                                        + projectName
                                        + "/lastBuild/console"));

        assertEquals(   "'[(x)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                        + projectName
                        + "/lastBuild/console|Status unknown; certification build incomplete (canceled or failed).]'"
                        ,actual);

    }

    // proves that MarkAllAsFailed() correctly marks TemplateValidation
     // as failed with the correct wikitip
     public void testMarkAllAsFailedTemplateValidation() throws FileNotFoundException {
         String projectName = UUID.randomUUID().toString();
         CertificationManager target = new CertificationManager();
         target.markAllAsFailed(projectName,TEST_PROJECT_URL);

         ProjectCertificationStatus projectStatus = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL,false);
         String actual = projectStatus.getTemplateValidation();

         assertNotNull(projectStatus);

         assertTrue( "getTemplateValidation does not contain '"
                     + BuildCertificationConstants.WIKI_FAILED
                     + "' as expected."
                     , actual.contains(BuildCertificationConstants.WIKI_FAILED));

         assertTrue( "getTemplateValidation does not contain '"
                     + BuildCertificationConstants.CERTIFICATION_INCOMPLETE
                     + "' as expected."
                     , actual.contains(BuildCertificationConstants.CERTIFICATION_INCOMPLETE));

         assertTrue( "getTemplateValidation does not contain 'http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                     + projectName
                     + "/lastBuild/console' as expected."
                     , actual.contains(  "http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                                         + projectName
                                         + "/lastBuild/console"));

         assertEquals(   "'[(x)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-"
                         + projectName
                         + "/lastBuild/console|Status unknown; certification build incomplete (canceled or failed).]'"
                         ,actual);

     }

 }