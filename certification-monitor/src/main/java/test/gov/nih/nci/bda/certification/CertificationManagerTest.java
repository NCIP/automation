package test.gov.nih.nci.bda.certification;

import gov.nih.nci.bda.certification.CertificationManager;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

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

    public void testAddPropertyValueThrowExceptionOnNullPropertyName() {
        CertificationManager target = new CertificationManager();
        Exception caught = null;

        try {
            target.addPropertyValue(null, "propertyvalue");
        }
        catch (Exception ex) {
            caught = ex;
        }

        assertNotNull(caught);

    }

    public void testAddPropertyValueThrowExceptionOnStringEmptyPropertyName() {
        CertificationManager target = new CertificationManager();
        Exception caught = null;

        try {
            target.addPropertyValue("", "propertyvalue");
        }
        catch (Exception ex) {
            caught = ex;
        }

        assertNotNull(caught);

    }
}
