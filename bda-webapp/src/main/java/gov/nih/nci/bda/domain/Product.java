package gov.nih.nci.bda.domain;

import static gov.nih.nci.bda.domain.PracticeStatus.NOT_SUCCESSFUL;
import static gov.nih.nci.bda.domain.PracticeStatus.valueOf;

import java.net.URL;

public class Product {

    public static final PracticeStatus DEFAULT_STATUS = NOT_SUCCESSFUL;

    private int id;
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
    private final String name;

    public Product(String name) {
        this.name = name;
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("'Name' cannot be null or blank.");
        }
    }

    public Practice getCertificationStatus() {
        return certificationStatus;
    }

    public Practice getBdaEnabled() {
        return bdaEnabled;
    }

    public Practice getSingleCommandBuild() {
        return singleCommandBuild;
    }

    public Practice getSingleCommandDeploy() {
        return singleCommandDeploy;
    }

    public Practice getRemoteUpgrade() {
        return remoteUpgrade;
    }

    public Practice getDbIntegration() {
        return dbIntegration;
    }

    public Practice getPrivateProperties() {
        return privateProperties;
    }

    public Practice getDeploymentShakeout() {
        return deploymentShakeout;
    }

    public Practice getTemplateValidation() {
        return templateValidation;
    }

    public Practice getCiBuild() {
        return ciBuild;
    }

    public Practice getCommandLineInstall() {
        return commandLineInstall;
    }

    public void setCertificationStatus(Practice certificationStatus) {
        this.certificationStatus = certificationStatus;
    }

    public void setBdaEnabled(Practice bdaEnabled) {
        this.bdaEnabled = bdaEnabled;
    }

    public void setSingleCommandBuild(Practice singleCommandBuild) {
        this.singleCommandBuild = singleCommandBuild;
    }

    public void setSingleCommandDeploy(Practice singleCommandDeploy) {
        this.singleCommandDeploy = singleCommandDeploy;
    }

    public void setRemoteUpgrade(Practice remoteUpgrade) {
        this.remoteUpgrade = remoteUpgrade;
    }

    public void setDbIntegration(Practice dbIntegration) {
        this.dbIntegration = dbIntegration;
    }

    public void setPrivateProperties(Practice privateProperties) {
        this.privateProperties = privateProperties;
    }

    public void setDeploymentShakeout(Practice deploymentShakeout) {
        this.deploymentShakeout = deploymentShakeout;
    }

    public void setTemplateValidation(Practice templateValidation) {
        this.templateValidation = templateValidation;
    }

    public void setCiBuild(Practice ciBuild) {
        this.ciBuild = ciBuild;
    }

    public void setCommandLineInstall(Practice commandLineInstall) {
        this.commandLineInstall = commandLineInstall;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
