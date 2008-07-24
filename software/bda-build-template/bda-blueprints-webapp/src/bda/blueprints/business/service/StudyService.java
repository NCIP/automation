package bda.blueprints.business.service;

import java.util.Collection;

import bda.blueprints.business.domain.Study;

public interface StudyService {

	Collection findAll();

	Collection findAllStates();

	int create(Study study);

}
