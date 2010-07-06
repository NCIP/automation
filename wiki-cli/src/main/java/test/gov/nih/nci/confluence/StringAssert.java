package test.gov.nih.nci.confluence;

import junit.framework.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Jul 6, 2010
 * Time: 7:33:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class StringAssert extends Assert {

    public static void assertStartsWith(String expectedToStartWith, String value) {
        if (!value.startsWith(expectedToStartWith))
        {
            fail("String '" + value + "' did not start with '" + expectedToStartWith + "' as expected.");
        }
    }

    public static void assertEndsWith(String expectedToEndWith, String value) {
        if (!value.endsWith(expectedToEndWith))
        {
            fail("String '" + value + "' did not end with '" + expectedToEndWith + "' as expected.");
        }
    }

    public static void assertContains(String expectedToContain, String value) {
        if (!value.contains(expectedToContain))
        {
            fail("String '" + value + "' did not contain '" + expectedToContain + "' as expected.");
        }
    }
}
