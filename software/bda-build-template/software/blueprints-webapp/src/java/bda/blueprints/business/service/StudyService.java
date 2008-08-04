package bda.blueprints.business.service;

import java.util.Collection;

import bda.blueprints.business.domain.Study;

public interface StudyService {

	int create(Study study);

	Collection findAll();

	Collection findAllStates();

}
