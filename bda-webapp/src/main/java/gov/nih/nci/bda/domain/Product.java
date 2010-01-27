package gov.nih.nci.bda.domain;

import static gov.nih.nci.bda.domain.PracticeStatus.NOT_SUCCESSFUL;

public class Product {
    private PracticeStatus certificationStatus;
    private PracticeStatus bdaEnabled;
    private PracticeStatus singleCommandBuild;
    private PracticeStatus singleCommandDeploy;
    private PracticeStatus remoteUpgrade;
    private PracticeStatus dbIntegration;
    private PracticeStatus privateProperties;
    private PracticeStatus deploymentShakeout;
    private PracticeStatus templateValidation;
    private PracticeStatus ciBuild;
    private PracticeStatus commandLineInstall;

    public Product() {
        certificationStatus = NOT_SUCCESSFUL;
        bdaEnabled = NOT_SUCCESSFUL;
        singleCommandBuild = NOT_SUCCESSFUL;
        singleCommandDeploy = NOT_SUCCESSFUL;
        remoteUpgrade = NOT_SUCCESSFUL;
        dbIntegration = NOT_SUCCESSFUL;
        privateProperties = NOT_SUCCESSFUL;
        deploymentShakeout = NOT_SUCCESSFUL;
        templateValidation = NOT_SUCCESSFUL;
        ciBuild = NOT_SUCCESSFUL;
        commandLineInstall = NOT_SUCCESSFUL;
    }

    public PracticeStatus getCertificationStatus() {
        return certificationStatus;
    }

    public PracticeStatus getBdaEnabled() {
        return bdaEnabled;
    }

    public PracticeStatus getSingleCommandBuild() {
        return singleCommandBuild;
    }

    public PracticeStatus getSingleCommandDeploy() {
        return singleCommandDeploy;
    }

    public PracticeStatus getRemoteUpgrade() {
        return remoteUpgrade;
    }

    public PracticeStatus getDbIntegration() {
        return dbIntegration;
    }

    public PracticeStatus getPrivateProperties() {
        return privateProperties;
    }

    public PracticeStatus getDeploymentShakeout() {
        return deploymentShakeout;
    }

    public PracticeStatus getTemplateValidation() {
        return templateValidation;
    }

    public PracticeStatus getCiBuild() {
        return ciBuild;
    }

    public PracticeStatus getCommandLineInstall() {
        return commandLineInstall;
    }

    public void setCertificationStatus(PracticeStatus certificationStatus) {
        this.certificationStatus = certificationStatus;
    }

    public void setBdaEnabled(PracticeStatus bdaEnabled) {
        this.bdaEnabled = bdaEnabled;
    }

    public void setSingleCommandBuild(PracticeStatus singleCommandBuild) {
        this.singleCommandBuild = singleCommandBuild;
    }

    public void setSingleCommandDeploy(PracticeStatus singleCommandDeploy) {
        this.singleCommandDeploy = singleCommandDeploy;
    }

    public void setRemoteUpgrade(PracticeStatus remoteUpgrade) {
        this.remoteUpgrade = remoteUpgrade;
    }

    public void setDbIntegration(PracticeStatus dbIntegration) {
        this.dbIntegration = dbIntegration;
    }

    public void setPrivateProperties(PracticeStatus privateProperties) {
        this.privateProperties = privateProperties;
    }

    public void setDeploymentShakeout(PracticeStatus deploymentShakeout) {
        this.deploymentShakeout = deploymentShakeout;
    }

    public void setTemplateValidation(PracticeStatus templateValidation) {
        this.templateValidation = templateValidation;
    }

    public void setCiBuild(PracticeStatus ciBuild) {
        this.ciBuild = ciBuild;
    }

    public void setCommandLineInstall(PracticeStatus commandLineInstall) {
        this.commandLineInstall = commandLineInstall;
    }

}
