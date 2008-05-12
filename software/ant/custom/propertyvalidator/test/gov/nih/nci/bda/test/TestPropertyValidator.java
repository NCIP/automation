package gov.nih.nci.bda.test;

import gov.nih.nci.bda.PropertyValidator;
import junit.framework.TestCase;

import org.apache.tools.ant.BuildException;
import org.junit.After;
import org.junit.Before;

/**
 * @author Levent.Gurses
 * 
 */
public class TestPropertyValidator extends TestCase {
	private String userdir = System.getProperty("user.dir");
	private PropertyValidator propertyValidator;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.propertyValidator = new PropertyValidator();
		this.propertyValidator.setKeyFile(this.userdir + "/properties/keyfile.properties");
		this.propertyValidator.setCompareFile(this.userdir + "/properties/dev.properties");
		this.propertyValidator.setMatch("exactly");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	public void testPropertyChecker() {
		try {
			this.propertyValidator.execute();
		}

		catch (BuildException e) {
			fail(e.getMessage());
		}

	}
}
