package bda.blueprints.business.service;

import java.util.Collection;
import java.util.Iterator;

import bda.blueprints.business.data.StudyDao;
import bda.blueprints.business.data.StudyDaoImpl;
import bda.blueprints.business.domain.Study;

public class StudyServiceImpl implements StudyService {

	public int create(Study study) {
		StudyDao studyData = new StudyDaoImpl();
		studyData.create(StudyDao.CREATE_STUDY, study);
		return 0;
	}

	public Collection findAll() {
		StudyDao studyData = new StudyDaoImpl();
		Collection studies = studyData.findAll(StudyDao.ALL_STUDIES);
		for (Iterator it = studies.iterator(); it.hasNext();) {
			Study study = (Study) it.next();
			System.out.println("study.getName()=" + study.getName() + "study.getResearcher()=" + study.getResearcher());
		}
		return studies;
	}

	public Collection findAllStates() {
		StudyDao studyData = new StudyDaoImpl();
		Collection states = studyData.findAllStates(StudyDao.ALL_STATES);
		return states;
	}

	public void runThis() {
		System.out.println("test");
	}

}
