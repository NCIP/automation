package gov.nih.nci.bda.certification.business;


/**
 * @author narram
 *
 */
public class BuildCertificationBean {
	private boolean buildSuccessful;	
	private boolean certificationStatus;
	private String failureMessage;
	private boolean isValue;
	private String mapName;
	private boolean optional;
	private String projectName;
	private String projectRepoUrl;
	private String propertyValue;
	private String targetName;
	
	public String getFailureMessage() {
		return failureMessage;
	}
	public String getMapName() {
		return mapName;
	}
	public String getProjectName() {
		return projectName;
	}
	public String getProjectRepoUrl() {
		return projectRepoUrl;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public String getTargetName() {
		return targetName;
	}
	public boolean isBuildSuccessful() {
		return buildSuccessful;
	}
	public boolean isCertificationStatus() {
		return certificationStatus;
	}
	public boolean isOptional() {
		return optional;
	}
	public boolean isValue() {
		return isValue;
	}
	public void setBuildSuccessful(boolean buildSuccessful) {
		this.buildSuccessful = buildSuccessful;
	}
	public void setCertificationStatus(boolean certificationStatus) {
		this.certificationStatus = certificationStatus;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setProjectRepoUrl(String projectRepoUrl) {
		this.projectRepoUrl = projectRepoUrl;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public void setValue(boolean isValue) {
		this.isValue = isValue;
	}
		
}
