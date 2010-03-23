package gov.nih.nci.bda.types;

import gov.nih.nci.bda.types.Practice;

public class Product {
	public Product() {

	}
	public Product(String name) {
		this.name = name;
	}
    private String id;
    public Practice getCertificationStatus() {
		return certificationStatus;
	}
	public void setCertificationStatus(Practice certificationStatus) {
		this.certificationStatus = certificationStatus;
	}
	public Practice getBdaEnabled() {
		return bdaEnabled;
	}
	public void setBdaEnabled(Practice bdaEnabled) {
		this.bdaEnabled = bdaEnabled;
	}
	public Practice getSingleCommandBuild() {
		return singleCommandBuild;
	}
	public void setSingleCommandBuild(Practice singleCommandBuild) {
		this.singleCommandBuild = singleCommandBuild;
	}
	public Practice getSingleCommandDeploy() {
		return singleCommandDeploy;
	}
	public void setSingleCommandDeploy(Practice singleCommandDeploy) {
		this.singleCommandDeploy = singleCommandDeploy;
	}
	public Practice getRemoteUpgrade() {
		return remoteUpgrade;
	}
	public void setRemoteUpgrade(Practice remoteUpgrade) {
		this.remoteUpgrade = remoteUpgrade;
	}
	public Practice getDbIntegration() {
		return dbIntegration;
	}
	public void setDbIntegration(Practice dbIntegration) {
		this.dbIntegration = dbIntegration;
	}
	public Practice getPrivateProperties() {
		return privateProperties;
	}
	public void setPrivateProperties(Practice privateProperties) {
		this.privateProperties = privateProperties;
	}
	public Practice getDeploymentShakeout() {
		return deploymentShakeout;
	}
	public void setDeploymentShakeout(Practice deploymentShakeout) {
		this.deploymentShakeout = deploymentShakeout;
	}
	public Practice getTemplateValidation() {
		return templateValidation;
	}
	public void setTemplateValidation(Practice templateValidation) {
		this.templateValidation = templateValidation;
	}
	public Practice getCiBuild() {
		return ciBuild;
	}
	public void setCiBuild(Practice ciBuild) {
		this.ciBuild = ciBuild;
	}
	public Practice getCommandLineInstall() {
		return commandLineInstall;
	}
	public void setCommandLineInstall(Practice commandLineInstall) {
		this.commandLineInstall = commandLineInstall;
	}
	private Practice certificationStatus;
    private Practice bdaEnabled;
    private Practice singleCommandBuild;
    private Practice singleCommandDeploy;
    private Practice remoteUpgrade;
    private Practice dbIntegration;
    private Practice privateProperties;
    private Practice deploymentShakeout;
    private Practice templateValidation;
    private Practice ciBuild;
    private Practice commandLineInstall;
    private String name;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
