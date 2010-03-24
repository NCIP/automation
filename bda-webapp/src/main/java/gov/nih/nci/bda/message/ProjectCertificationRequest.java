package gov.nih.nci.bda.message;

public class ProjectCertificationRequest {

	public ProjectCertificationRequest() {
	}

	public ProjectCertificationRequest(String projectName) {
		this.projectName = projectName;
	}

	private String projectName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
