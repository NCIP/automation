package bda.blueprints.business.data;

import java.util.Collection;

import bda.blueprints.business.domain.Study;

public interface StudyDao {

	String ALL_STUDIES = "SELECT * from STUDY ";
	String ALL_STATES = "SELECT * from state ";
	String CREATE_STUDY = "INSERT INTO STUDY(id, study_name, researcher, date_received) VALUES(?,?,?,'2006-08-20');  ";

	int create(String sql, Study study);

	Collection findAll(String sql);

	Collection findAllStates(String sql);

}
