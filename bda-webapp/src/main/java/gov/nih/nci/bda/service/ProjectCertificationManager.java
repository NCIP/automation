package gov.nih.nci.bda.service;

import gov.nih.nci.bda.types.ProjectCertification;

public interface ProjectCertificationManager {
	ProjectCertification getProjectCertification(String projectName);
}
