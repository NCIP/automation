package test.gov.nih.nci.bda.certification.domain;

import gov.nih.nci.bda.certification.domain.TargetLookup;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * TargetLookup Tester.
 *
 * @author <Authors name>
 * @since <pre>07/20/2010</pre>
 * @version 1.0
 */
public class TargetLookupTest extends TestCase {
    public TargetLookupTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public static Test suite() {
        return new TestSuite(TargetLookupTest.class);
    }

    public void testSaveProperties() {
        String value = "xyz";
        TargetLookup target = new TargetLookup();
        target.setSaveProperties(value);
        assertEquals(value,target.getSaveProperties());
    }

    public void testSaveExpressionsReturnsSomething() {
        TargetLookup target = new TargetLookup();
        target.setSaveProperties("x,y");
        String[] o = target.SaveExpressions();
        assertNotNull(o);
    }

    public void testSaveExpressionsCountCorrect() {
        TargetLookup target = new TargetLookup();
        target.setSaveProperties("x,y");
        String[] o = target.SaveExpressions();
        assertEquals(2,o.length);
    }
    public void testSaveExpressionsNullSavePropertiesNoException() {
        TargetLookup target = new TargetLookup();
        String[] o = target.SaveExpressions();
    }

    public void testSavePropertiesDoesNotReturnNull() {
        TargetLookup target = new TargetLookup();
        assertNotNull(target.getSaveProperties());
    }

    public void testUseProperties() {
            String value = "xyz";
            TargetLookup target = new TargetLookup();
            target.setUseProperties(value);
            assertEquals(value,target.getUseProperties());
        }


        public void testUseExpressionsReturnsSomething() {
            TargetLookup target = new TargetLookup();
            target.setUseProperties("x,y");
            String[] o = target.UseExpressions();
            assertNotNull(o);
        }

        public void testUseExpressionsCountCorrect() {
            TargetLookup target = new TargetLookup();
            target.setUseProperties("x,y");
            String[] o = target.UseExpressions();
            assertEquals(2,o.length);
        }
        public void testUseExpressionsNullUsePropertiesNoException() {
            TargetLookup target = new TargetLookup();
            String[] o = target.UseExpressions();
        }

        public void testUsePropertiesDoesNotReturnNull() {
            TargetLookup target = new TargetLookup();
            assertNotNull(target.getUseProperties());
        }
}
