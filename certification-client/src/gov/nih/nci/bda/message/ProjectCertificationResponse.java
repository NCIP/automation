package gov.nih.nci.bda.message;

import gov.nih.nci.bda.types.ProjectCertification;

public class ProjectCertificationResponse {

	public ProjectCertificationResponse() {
	}

	private ProjectCertification projectCertification;

	public ProjectCertificationResponse(ProjectCertification projectCertification) {
		this.projectCertification = projectCertification;
	}

	public ProjectCertification getProjectCertification() {
		return projectCertification;
	}

	public void setProjectCertification(ProjectCertification projectCertification) {
		this.projectCertification = projectCertification;
	}

}
