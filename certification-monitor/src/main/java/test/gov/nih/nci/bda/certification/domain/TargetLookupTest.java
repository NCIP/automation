package test.gov.nih.nci.bda.certification.domain;

import gov.nih.nci.bda.certification.domain.TargetLookup;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

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
        target.setId(1);
//        assertEquals(value,target.getSaveProperties());
    }

    
}
