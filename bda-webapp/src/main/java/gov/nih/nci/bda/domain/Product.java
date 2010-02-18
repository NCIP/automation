package gov.nih.nci.bda.domain;

import static gov.nih.nci.bda.domain.PracticeStatus.NOT_SUCCESSFUL;
import static gov.nih.nci.bda.domain.PracticeStatus.valueOf;

public class Product {

    public static final PracticeStatus DEFAULT_STATUS = NOT_SUCCESSFUL;

    private int id;
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
    private final String name;

    public Product(String name) {
        this.name = name;
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("'Name' cannot be null or blank.");
        }

        certificationStatus = DEFAULT_STATUS;
        bdaEnabled = DEFAULT_STATUS;
        singleCommandBuild = DEFAULT_STATUS;
        singleCommandDeploy = DEFAULT_STATUS;
        remoteUpgrade = DEFAULT_STATUS;
        dbIntegration = DEFAULT_STATUS;
        privateProperties = DEFAULT_STATUS;
        deploymentShakeout = DEFAULT_STATUS;
        templateValidation = DEFAULT_STATUS;
        ciBuild = DEFAULT_STATUS;
        commandLineInstall = DEFAULT_STATUS;
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

    public void setBdaEnabled(String bdaEnabled) {
        if (bdaEnabled == null) {
            setBdaEnabled(DEFAULT_STATUS);
        } else {
            setBdaEnabled(valueOf(bdaEnabled));
        }
    }

    public void setCertificationStatus(String certificationStatus) {
        if (certificationStatus == null) {
            setCertificationStatus(DEFAULT_STATUS);
        } else {
            setCertificationStatus(valueOf(certificationStatus));
        }
    }

    public void setSingleCommandDeploy(String singleCommandDeploy) {
        if (singleCommandDeploy == null) {
            setSingleCommandDeploy(DEFAULT_STATUS);
        } else {
            setSingleCommandDeploy(valueOf(singleCommandDeploy));
        }
    }

    public void setTemplateValidation(String templateValidation) {
        if (templateValidation == null) {
            setTemplateValidation(DEFAULT_STATUS);
        } else {
            setTemplateValidation(valueOf(templateValidation));
        }
    }

    public void setSingleCommandBuild(String singleCommandBuild) {
        if (singleCommandBuild == null) {
            setSingleCommandBuild(DEFAULT_STATUS);
        } else {
            setSingleCommandBuild(valueOf(singleCommandBuild));
        }
    }

    public void setRemoteUpgrade(String remoteUpgrade) {
        if (remoteUpgrade == null) {
            setRemoteUpgrade(DEFAULT_STATUS);
        } else {
            setRemoteUpgrade(valueOf(remoteUpgrade));
        }
    }

    public void setPrivateProperties(String privateProperties) {
        if (privateProperties == null) {
            setPrivateProperties(DEFAULT_STATUS);
        } else {
            setPrivateProperties(valueOf(privateProperties));
        }
    }

    public void setDeploymentShakeout(String deploymentShakeout) {
        if (deploymentShakeout == null) {
            setDeploymentShakeout(DEFAULT_STATUS);
        } else {
            setDeploymentShakeout(valueOf(deploymentShakeout));
        }
    }

    public void setDbIntegration(String dbIntegration) {
        if (dbIntegration == null) {
            setDbIntegration(DEFAULT_STATUS);
        } else {
            setDbIntegration(valueOf(dbIntegration));
        }
    }

    public void setCommandLineInstall(String commandLineInstall) {
        if (commandLineInstall == null) {
            setCommandLineInstall(DEFAULT_STATUS);
        } else {
            setCommandLineInstall(valueOf(commandLineInstall));
        }
    }

    public void setCiBuild(String ciBuild) {
        if (ciBuild == null) {
            setCiBuild(DEFAULT_STATUS);
        } else {
            setCiBuild(valueOf(ciBuild));
        }
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
