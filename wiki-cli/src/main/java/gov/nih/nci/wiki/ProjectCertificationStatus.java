package gov.nih.nci.wiki;

import java.util.Date;


public class ProjectCertificationStatus {
	private int id;
	private String product;
	private String certificationStatus;
	private String singleCommandBuild;
	private String singleCommandDeployment;
	private String remoteUpgrade;
	private String databaseIntegration;
	private String templateValidation;
	private String privateRepositoryProperties;
	private String latestCIBuild;
	private String deploymentShakeout;
	private String bdaEnabled;
	private String commandLineInstaller;
    private Date certificationDate;

    public Date getCertificationDate() {
        return certificationDate;
    }

    public void setCertificationDate(Date certificationDate) {
        this.certificationDate = certificationDate;
    }

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getCertificationStatus() {
		return certificationStatus;
	}
	public void setCertificationStatus(String certificationStatus) {
		this.certificationStatus = certificationStatus;
	}
	public String getSingleCommandBuild() {
		return singleCommandBuild;
	}
	public void setSingleCommandBuild(String singleCommandBuild) {
		this.singleCommandBuild = singleCommandBuild;
	}
	public String getSingleCommandDeployment() {
		return singleCommandDeployment;
	}
	public void setSingleCommandDeployment(String singleCommandDeployment) {
		this.singleCommandDeployment = singleCommandDeployment;
	}
	public String getDatabaseIntegration() {
		return databaseIntegration;
	}
	public void setDatabaseIntegration(String databaseIntegration) {
		this.databaseIntegration = databaseIntegration;
	}
	public String getTemplateValidation() {
		return templateValidation;
	}
	public void setTemplateValidation(String templateValidation) {
		this.templateValidation = templateValidation;
	}
	public String getPrivateRepositoryProperties() {
		return privateRepositoryProperties;
	}
	public void setPrivateRepositoryProperties(String privateRepositoryProperties) {
		this.privateRepositoryProperties = privateRepositoryProperties;
	}
	public String getLatestCIBuild() {
		return latestCIBuild;
	}
	public void setLatestCIBuild(String latestCIBuild) {
		this.latestCIBuild = latestCIBuild;
	}
	public String getDeploymentShakeout() {
		return deploymentShakeout;
	}
	public void setDeploymentShakeout(String deploymentShakeout) {
		this.deploymentShakeout = deploymentShakeout;
	}
	public String getBdaEnabled() {
		return bdaEnabled;
	}
	public void setBdaEnabled(String bdaEnabled) {
		this.bdaEnabled = bdaEnabled;
	}
	public String getCommandLineInstaller() {
		return commandLineInstaller;
	}
	public void setCommandLineInstaller(String commandLineInstaller) {
		this.commandLineInstaller = commandLineInstaller;
	}
	public String getRemoteUpgrade() {
		return remoteUpgrade;
	}
	public void setRemoteUpgrade(String remoteUpgrade) {
		this.remoteUpgrade = remoteUpgrade;
	}
		
}
