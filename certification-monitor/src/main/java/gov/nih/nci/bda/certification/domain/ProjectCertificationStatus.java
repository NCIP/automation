package gov.nih.nci.bda.certification.domain;


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
    public void setProduct(String productName, String productSvnUrl) {
        this.product = "[" + productName + "|" + productSvnUrl + "]" ;
	}
	public String getCertificationStatus() {
        if (certificationStatus == null) {
            certificationStatus = "";
        }
		return certificationStatus;
	}
	public void setCertificationStatus(String certificationStatus) {
		this.certificationStatus = certificationStatus;
	}
	public String getSingleCommandBuild() {
        if (singleCommandBuild == null) {
            singleCommandBuild = "";
        }
		return singleCommandBuild;
	}
	public void setSingleCommandBuild(String singleCommandBuild) {
		this.singleCommandBuild = singleCommandBuild;
	}
	public String getSingleCommandDeployment() {
        if (singleCommandDeployment == null) {
            singleCommandDeployment = "";
        }
		return singleCommandDeployment;
	}
	public void setSingleCommandDeployment(String singleCommandDeployment) {
		this.singleCommandDeployment = singleCommandDeployment;
	}
	public String getDatabaseIntegration() {
        if (databaseIntegration == null) {
            databaseIntegration = "";
        }
		return databaseIntegration;
	}
	public void setDatabaseIntegration(String databaseIntegration) {
		this.databaseIntegration = databaseIntegration;
	}
	public String getTemplateValidation() {
        if (templateValidation == null) {
            templateValidation = "";
        }
		return templateValidation;
	}
	public void setTemplateValidation(String templateValidation) {
		this.templateValidation = templateValidation;
	}
	public String getPrivateRepositoryProperties() {
        if (privateRepositoryProperties==null) {
            privateRepositoryProperties = "";
        }
		return privateRepositoryProperties;
	}
	public void setPrivateRepositoryProperties(String privateRepositoryProperties) {
		this.privateRepositoryProperties = privateRepositoryProperties;
	}
	public String getLatestCIBuild() {
        if (latestCIBuild == null) {
            latestCIBuild = "";
        }
		return latestCIBuild;
	}
	public void setLatestCIBuild(String latestCIBuild) {
		this.latestCIBuild = latestCIBuild;
	}
	public String getDeploymentShakeout() {
        if (deploymentShakeout == null) {
            deploymentShakeout = "";
        }
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
        if (commandLineInstaller == null) {
            commandLineInstaller = "";
        }
		return commandLineInstaller;
	}
	public void setCommandLineInstaller(String commandLineInstaller) {
		this.commandLineInstaller = commandLineInstaller;
	}
	public String getRemoteUpgrade() {
        if( remoteUpgrade == null) {
            remoteUpgrade = "";
        }
		return remoteUpgrade;
	}
	public void setRemoteUpgrade(String remoteUpgrade) {
		this.remoteUpgrade = remoteUpgrade;
	}
		
}
