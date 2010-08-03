package test.gov.nih.nci.bda.certification;

import gov.nih.nci.bda.certification.BuildCertificationHelper;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

/**
 * BuildCertificationHelper Tester.
 *
 * @author <Authors name>
 * @since <pre>08/03/2010</pre>
 * @version 1.0
 */
public class BuildCertificationHelperTest extends TestCase {
    public BuildCertificationHelperTest(String name) {
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
     * Method: formatMessage(String message)
     *
     */
    public void testFormatMessageEscapesBrackets() throws Exception {

        BuildCertificationHelper target = new BuildCertificationHelper();
        String rawMessage = "this has [brackets] in it." ;
        String expected ="this has \\[brackets\\] in it." ;
        String actual = target.formatMessage(rawMessage);
        assertEquals(expected,actual);
    }

    public void testFormatMessageEscapesCurlies() throws Exception {

        BuildCertificationHelper target = new BuildCertificationHelper();
        String rawMessage = "this has {curlies} in it." ;
        String expected ="this has \\{curlies\\} in it." ;
        String actual = target.formatMessage(rawMessage);
        assertEquals(expected,actual);
    }


    public void testFormatMessageEscapesPipes() throws Exception {

        BuildCertificationHelper target = new BuildCertificationHelper();
        String rawMessage = "this has |pipes| in it." ;
        String expected ="this has \\|pipes\\| in it." ;
        String actual = target.formatMessage(rawMessage);
        assertEquals(expected,actual);
    }


    public static Test suite() {
        return new TestSuite(BuildCertificationHelperTest.class);
    }
}
