package gov.nih.nci.bda.certification.domain;

public class ProjectProperties {
	private int id;
	private String projectName;
	private String databaseType;
	private String svnUsername;
	private String svnPassword;
	private String svnProjectUrl;
	private String svnLocalCheckout;
	private String masterBuildLocation;
	private String masterInstallLocation;
	private String singleCommandBuildTarget;
	private String singleCommandDeploymentTarget;
	private String databaseIntegrationTarget;
	private String masterBuildFile;
	private String explodedDirLocation;
	private String privateRepository;
	private String localPrivateCheckout;
	private String ciServerHostname;
	private String ciServerPortnumber;
	private String ciServerJobname;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDatabaseType() {
		return databaseType;
	}
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
	public String getSvnUsername() {
		return svnUsername;
	}
	public void setSvnUsername(String svnUsername) {
		this.svnUsername = svnUsername;
	}
	public String getSvnPassword() {
		return svnPassword;
	}
	public void setSvnPassword(String svnPassword) {
		this.svnPassword = svnPassword;
	}
	public String getSvnProjectUrl() {
		return svnProjectUrl;
	}
	public void setSvnProjectUrl(String svnProjectUrl) {
		this.svnProjectUrl = svnProjectUrl;
	}
	public String getSvnLocalCheckout() {
		return svnLocalCheckout;
	}
	public void setSvnLocalCheckout(String svnLocalCheckout) {
		this.svnLocalCheckout = svnLocalCheckout;
	}
	public String getMasterBuildLocation() {
		return masterBuildLocation;
	}
	public void setMasterBuildLocation(String masterBuildLocation) {
		this.masterBuildLocation = masterBuildLocation;
	}
	public String getMasterInstallLocation() {
		return masterInstallLocation;
	}
	public void setMasterInstallLocation(String masterInstallLocation) {
		this.masterInstallLocation = masterInstallLocation;
	}
	public String getSingleCommandBuildTarget() {
		return singleCommandBuildTarget;
	}
	public void setSingleCommandBuildTarget(String singleCommandBuildTarget) {
		this.singleCommandBuildTarget = singleCommandBuildTarget;
	}
	public String getSingleCommandDeploymentTarget() {
		return singleCommandDeploymentTarget;
	}
	public void setSingleCommandDeploymentTarget(
			String singleCommandDeploymentTarget) {
		this.singleCommandDeploymentTarget = singleCommandDeploymentTarget;
	}
	public String getDatabaseIntegrationTarget() {
		return databaseIntegrationTarget;
	}
	public void setDatabaseIntegrationTarget(String databaseIntegrationTarget) {
		this.databaseIntegrationTarget = databaseIntegrationTarget;
	}
	public String getMasterBuildFile() {
		return masterBuildFile;
	}
	public void setMasterBuildFile(String masterBuildFile) {
		this.masterBuildFile = masterBuildFile;
	}
	public String getExplodedDirLocation() {
		return explodedDirLocation;
	}
	public void setExplodedDirLocation(String explodedDirLocation) {
		this.explodedDirLocation = explodedDirLocation;
	}
	public String getPrivateRepository() {
		return privateRepository;
	}
	public void setPrivateRepository(String privateRepository) {
		this.privateRepository = privateRepository;
	}
	public String getLocalPrivateCheckout() {
		return localPrivateCheckout;
	}
	public void setLocalPrivateCheckout(String localPrivateCheckout) {
		this.localPrivateCheckout = localPrivateCheckout;
	}
	public String getCiServerHostname() {
		return ciServerHostname;
	}
	public void setCiServerHostname(String ciServerHostname) {
		this.ciServerHostname = ciServerHostname;
	}
	public String getCiServerPortnumber() {
		return ciServerPortnumber;
	}
	public void setCiServerPortnumber(String ciServerPortnumber) {
		this.ciServerPortnumber = ciServerPortnumber;
	}
	public String getCiServerJobname() {
		return ciServerJobname;
	}
	public void setCiServerJobname(String ciServerJobname) {
		this.ciServerJobname = ciServerJobname;
	}
}
