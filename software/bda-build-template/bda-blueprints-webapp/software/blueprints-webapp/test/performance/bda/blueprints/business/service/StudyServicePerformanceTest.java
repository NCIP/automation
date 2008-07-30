package bda.blueprints.business.service;

import java.util.Collection;

import junit.framework.TestCase;
import bda.blueprints.business.domain.Study;

public class StudyServicePerformanceTest extends TestCase {

	private StudyService studyService;

	public StudyServicePerformanceTest(String name) {
		super(name);
	}

	@Override
	public void setUp() {
		studyService = new StudyServiceImpl();
	}

	public void testPerformanceGetStudy() {
		Collection studies = studyService.findAll();
		java.util.Iterator itor = studies.iterator();
		String name = null;
		Study study = null;
		while (itor.hasNext()) {
			study = (Study) itor.next();
			name = study.getName();
			System.out.println("name1=" + name);
		}

		if (studies != null && studies.size() > 0) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

}
