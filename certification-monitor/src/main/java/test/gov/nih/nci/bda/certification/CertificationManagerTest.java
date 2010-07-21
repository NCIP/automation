package test.gov.nih.nci.bda.certification;

import gov.nih.nci.bda.certification.CertificationManager;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

import java.util.Map;

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

    public void testAddPropertyValueThrowExceptionOnNullValue() {
        CertificationManager target = new CertificationManager();
        Exception caught = null;

        try {
            target.addPropertyValue("propertyname", null);
        }
        catch (Exception ex) {
            caught = ex;
        }

        assertNotNull(caught);

    }

    public void testAddPropertyValueThrowExceptionOnStringEmptyValue() {
        CertificationManager target = new CertificationManager();
        Exception caught = null;

        try {
            target.addPropertyValue("propertyname", null);
        }
        catch (Exception ex) {
            caught = ex;
        }

        assertNotNull(caught);

    }

    public void testAddPropertyValueAddsAndRetrievesCorrectly() throws Exception {

        CertificationManager target = new CertificationManager();
        String key = "mykey";
        String value = "myvalue";
        target.addPropertyValue(key, value);
        assertEquals(1, target.getPropertyValues().size());
        String actual = target.getPropertyValues().get(key);
        assertEquals(value, actual);

    }

    public void testPropertyValuesNotNull() {

//        proves that propertyValues is not null, even though nothing has been added

        CertificationManager target = new CertificationManager();
        assertNotNull(target.getPropertyValues());

    }

    public void testSameKeyTwice() throws Exception {

        Exception caught = null;
        CertificationManager target = new CertificationManager();
        String key = "mykey";
        String value = "myvalue";
        target.addPropertyValue(key, value);
        try {
            target.addPropertyValue(key, value);
        }
        catch (Exception ex) {
            caught = ex;
        }

        assertNotNull(caught);


    }

}
