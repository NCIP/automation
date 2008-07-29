package bda.blueprints.business.service;

import java.util.Collection;

import junit.framework.TestCase;
import bda.blueprints.business.domain.Study;
import bda.blueprints.business.domain.State;

public class StudyServiceComponentTest extends TestCase {

	private StudyService studyService;

	public StudyServiceComponentTest(String name) {
		super(name);
	}

	@Override
	public void setUp() {
		studyService = new StudyServiceImpl();
	}

	public void testComponentGetStudy() {
		Collection studies = studyService.findAll();
		assertTrue(studies != null && studies.size() > 0);
	}

	public void testComponentCreateStudy() {
		Study study = new Study();
		study.setResearcher("My Researcher 11");
		study.setName("My Ale 11");
		if (studyService.create(study) == 0) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	public void testGetStates() {
		Collection states = studyService.findAllStates();
		java.util.Iterator itor = states.iterator();
		String name = null;
		State state = null;
		while (itor.hasNext()) {
			state = (State) itor.next();
			name = state.getState();
			System.out.println("name1=" + name);
		}
		if (states != null && states.size() > 0) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

}
