package gov.nih.nci.bda.certification.business;



public class BuildCertificationBean {
	private boolean buildSuccessful;	
	private String projectName;
	private String targetName;
	private String mapName;
	private boolean isValue;
	private String propertyValue;
	private String projectRepoUrl;
	private String failureMessage;
	
	public boolean isBuildSuccessful() {
		return buildSuccessful;
	}
	public void setBuildSuccessful(boolean buildSuccessful) {
		this.buildSuccessful = buildSuccessful;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public boolean isValue() {
		return isValue;
	}
	public void setValue(boolean isValue) {
		this.isValue = isValue;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getProjectRepoUrl() {
		return projectRepoUrl;
	}
	public void setProjectRepoUrl(String projectRepoUrl) {
		this.projectRepoUrl = projectRepoUrl;
	}
	public String getFailureMessage() {
		return failureMessage;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	
	
	
}
