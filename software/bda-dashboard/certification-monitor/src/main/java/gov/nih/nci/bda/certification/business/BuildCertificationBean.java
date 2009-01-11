package gov.nih.nci.bda.certification.business;



public class BuildCertificationBean {
	private boolean buildSuccessful;	
	private String projectName;
	private String targetName;
	private String mapName;
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
}
