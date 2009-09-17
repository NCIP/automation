package bda.blueprints.business.data;

import java.util.Collection;

import junit.framework.TestCase;

public class StudyDaoComponentTest extends TestCase {

	private StudyDaoImpl studyData;

	public StudyDaoComponentTest(String name) {
		super(name);
	}

	@Override
	public void setUp() {
		studyData = new StudyDaoImpl();
	}

	public void testGetAllStates() {
		Collection states = studyData.findAllStates(StudyDao.ALL_STATES);
		assertTrue("No states found!", states != null && states.size() > 0);
	}

	public void testInvalidSql() {
		Collection states = null;
		try {
			states = studyData.findAllStates("select * from junk");

		} catch (Exception e) {
			assertTrue("No states found!", states != null && states.size() > 0);
		}

	}

}
