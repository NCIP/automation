package bda.blueprints.business.service;

import java.util.Collection;

import junit.framework.TestCase;

public class StudyServiceUnitTest extends TestCase {

	private StudyDaoStub studyData;

	public StudyServiceUnitTest(String name) {
		super(name);
	}

	@Override
	public void setUp() {
		studyData = new StudyDaoStub();
	}

	public void testUnitGetStudy() {
		Collection studies = studyData.findAll();
		assertTrue("Unable to find studies!", studies != null && studies.size() > 0);
	}
}
