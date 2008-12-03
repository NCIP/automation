package bda.blueprints.business.service;

import java.util.ArrayList;
import java.util.List;

import bda.blueprints.business.domain.Study;

public class StudyDaoStub {

	public static List FIND_ALL = createList();

	private static ArrayList createList() {
		ArrayList list = new ArrayList();
		list.add(createStudy());
		return list;
	}

	private static Study createStudy() {
		Study study = new Study();
		study.setResearcher("New Researcher");
		study.setName("John Doe");
		study.setDateReceived("2006-08-24");
		return study;
	}

	public List findAll() {
		return FIND_ALL;
	}
}
