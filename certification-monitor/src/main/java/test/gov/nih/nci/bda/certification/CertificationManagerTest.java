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

    public void testMarkAllAsFailed() throws FileNotFoundException {
        String projectName = UUID.randomUUID().toString();
        CertificationManager target = new CertificationManager();
        target.markAllAsFailed(projectName,TEST_PROJECT_URL);

        ProjectCertificationStatus projectStatus = ProjectCertificationStatusHelper.getProject(projectName,TEST_PROJECT_URL,false);
        
        assertNotNull(projectStatus);
        assertTrue(projectStatus.getBdaEnabled().contains(BuildCertificationConstants.WIKI_FAILED));
    }

}
