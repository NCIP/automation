package gov.nih.nci.bda.monitor.business;



public class BuildMonitorBean {
	private boolean buildSuccessful;
	private String propertiesFileName;
	private String projectName;
	public boolean isBuildSuccessful() {
		return buildSuccessful;
	}
	public void setBuildSuccessful(boolean buildSuccessful) {
		this.buildSuccessful = buildSuccessful;
	}
	public String getPropertiesFileName() {
		return propertiesFileName;
	}
	public void setPropertiesFileName(String propertiesFileName) {
		this.propertiesFileName = propertiesFileName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}	
}
