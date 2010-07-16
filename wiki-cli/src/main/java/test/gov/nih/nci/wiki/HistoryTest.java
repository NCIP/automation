package test.gov.nih.nci.wiki;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

/**
 * History Tester.
 *
 * @author <Authors name>
 * @since <pre>07/16/2010</pre>
 * @version 1.0
 */
public class HistoryTest extends TestCase {
    public HistoryTest(String name) {
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
     * Method: getMostRecent()
     *
     */
    public void testGetMostRecent() throws Exception {
        //TODO: Test goes here...
    }



    public static Test suite() {
        return new TestSuite(HistoryTest.class);
    }
}
