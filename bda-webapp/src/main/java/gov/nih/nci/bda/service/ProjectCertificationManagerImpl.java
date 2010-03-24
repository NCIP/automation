package gov.nih.nci.bda.service;

import gov.nih.nci.bda.types.Practice;
import gov.nih.nci.bda.types.ProjectCertification;
import gov.nih.nci.bda.types.Product;


public class ProjectCertificationManagerImpl implements ProjectCertificationManager {
	private ProductManager productManager;
	
	public ProjectCertification getProjectCertification(String projectName) {
		Product product = new Product(projectName);
		getCertificationStatus(projectName, product);
		ProjectCertification projectCertification = new ProjectCertification(product);
		return projectCertification;
	}


	public void getCertificationStatus(String projectName,Product product) {
		
        gov.nih.nci.bda.domain.Product domainProd = productManager.getProduct(projectName);
        
        product.setId(Integer.toString(domainProd.getId()));		
		
		Practice statusPractice = new Practice("CertificationStatus");
		statusPractice.setStatus(domainProd.getCertificationStatus().getStatus() != null ?  domainProd.getCertificationStatus().getStatus().name():"");
		statusPractice.setErrorMessage(domainProd.getCertificationStatus().getAlternateText() != null ?  domainProd.getCertificationStatus().getAlternateText():"");
		statusPractice.setErrorLocation(domainProd.getCertificationStatus().getUrl() != null ?  domainProd.getCertificationStatus().getUrl().toString():"");
		product.setCertificationStatus(statusPractice);

		Practice singleBuildPractice = new Practice("SingleCommandBuild");
		singleBuildPractice.setStatus(domainProd.getSingleCommandBuild().getStatus() != null ?  domainProd.getSingleCommandBuild().getStatus().name():"");
		singleBuildPractice.setErrorMessage(domainProd.getSingleCommandBuild().getAlternateText() != null ?  domainProd.getSingleCommandBuild().getAlternateText():"");
		singleBuildPractice.setErrorLocation(domainProd.getSingleCommandBuild().getUrl() != null ?  domainProd.getSingleCommandBuild().getUrl().toString():"");		
		product.setSingleCommandBuild(singleBuildPractice);
		
		Practice statusDeployPractice = new Practice("SingleCommandDeploy");
		statusDeployPractice.setStatus(domainProd.getSingleCommandDeploy().getStatus() != null ?  domainProd.getSingleCommandDeploy().getStatus().name():"");
		statusDeployPractice.setErrorMessage(domainProd.getSingleCommandDeploy().getAlternateText() != null ?  domainProd.getSingleCommandDeploy().getAlternateText():"");
		statusDeployPractice.setErrorLocation(domainProd.getSingleCommandDeploy().getUrl() != null ?  domainProd.getSingleCommandDeploy().getUrl().toString():"");		
		product.setSingleCommandDeploy(statusDeployPractice);
		
		Practice upgradePractice = new Practice("RemoteUpgrade");
		upgradePractice.setStatus(domainProd.getRemoteUpgrade().getStatus() != null ?  domainProd.getRemoteUpgrade().getStatus().name():"");
		upgradePractice.setErrorMessage(domainProd.getRemoteUpgrade().getAlternateText() != null ?  domainProd.getRemoteUpgrade().getAlternateText():"");
		upgradePractice.setErrorLocation(domainProd.getRemoteUpgrade().getUrl() != null ?  domainProd.getRemoteUpgrade().getUrl().toString():"");		
		product.setRemoteUpgrade(upgradePractice);
	
		Practice dbIntegrationPractice = new Practice("DbIntegration");
		dbIntegrationPractice.setStatus(domainProd.getDbIntegration().getStatus() != null ?  domainProd.getDbIntegration().getStatus().name():"");
		dbIntegrationPractice.setErrorMessage(domainProd.getDbIntegration().getAlternateText() != null ?  domainProd.getDbIntegration().getAlternateText():"");
		dbIntegrationPractice.setErrorLocation(domainProd.getDbIntegration().getUrl() != null ?  domainProd.getDbIntegration().getUrl().toString():"");	
		product.setDbIntegration(dbIntegrationPractice);

		Practice templatePractice = new Practice("TemplateValidation");
		templatePractice.setStatus(domainProd.getTemplateValidation().getStatus() != null ?  domainProd.getTemplateValidation().getStatus().name():"");
		templatePractice.setErrorMessage(domainProd.getTemplateValidation().getAlternateText() != null ?  domainProd.getTemplateValidation().getAlternateText():"");
		templatePractice.setErrorLocation(domainProd.getTemplateValidation().getUrl() != null ?  domainProd.getTemplateValidation().getUrl().toString():"");
		product.setTemplateValidation(templatePractice);
		
		Practice privatePropertiesPractice = new Practice("PrivateProperties");
		privatePropertiesPractice.setStatus(domainProd.getPrivateProperties().getStatus() != null ?  domainProd.getPrivateProperties().getStatus().name():"");
		privatePropertiesPractice.setErrorMessage(domainProd.getPrivateProperties().getAlternateText() != null ?  domainProd.getPrivateProperties().getAlternateText():"");
		privatePropertiesPractice.setErrorLocation(domainProd.getPrivateProperties().getUrl() != null ?  domainProd.getPrivateProperties().getUrl().toString():"");		
		product.setPrivateProperties(privatePropertiesPractice);
		
		Practice ciPractice = new Practice("CiBuild");
		ciPractice.setStatus(domainProd.getCiBuild().getStatus() != null ?  domainProd.getCiBuild().getStatus().name():"");
		ciPractice.setErrorMessage(domainProd.getCiBuild().getAlternateText() != null ?  domainProd.getCiBuild().getAlternateText():"");
		ciPractice.setErrorLocation(domainProd.getCiBuild().getUrl() != null ?  domainProd.getCiBuild().getUrl().toString():"");		
		product.setCiBuild(ciPractice);
	
		Practice shakeoutPractice = new Practice("DeploymentShakeout");
		shakeoutPractice.setStatus(domainProd.getDeploymentShakeout().getStatus() != null ?  domainProd.getDeploymentShakeout().getStatus().name():"");
		shakeoutPractice.setErrorMessage(domainProd.getDeploymentShakeout().getAlternateText() != null ?  domainProd.getDeploymentShakeout().getAlternateText():"");
		shakeoutPractice.setErrorLocation(domainProd.getDeploymentShakeout().getUrl() != null ?  domainProd.getDeploymentShakeout().getUrl().toString():"");		
		product.setDeploymentShakeout(shakeoutPractice);

		Practice bdaEnabledPractice = new Practice("BdaEnabled");
		bdaEnabledPractice.setStatus(domainProd.getBdaEnabled().getStatus() != null ?  domainProd.getBdaEnabled().getStatus().name():"");
		bdaEnabledPractice.setErrorMessage(domainProd.getBdaEnabled().getAlternateText() != null ?  domainProd.getBdaEnabled().getAlternateText():"");
		bdaEnabledPractice.setErrorLocation(domainProd.getBdaEnabled().getUrl() != null ?  domainProd.getBdaEnabled().getUrl().toString():"");		
		product.setBdaEnabled(bdaEnabledPractice);
	
		Practice commadlinePractice = new Practice("CommandLineInstall");
		commadlinePractice.setStatus(domainProd.getCommandLineInstall().getStatus() != null ?  domainProd.getCommandLineInstall().getStatus().name():"");
		commadlinePractice.setErrorMessage(domainProd.getCommandLineInstall().getAlternateText() != null ?  domainProd.getCommandLineInstall().getAlternateText():"");
		commadlinePractice.setErrorLocation(domainProd.getCommandLineInstall().getUrl() != null ?  domainProd.getCommandLineInstall().getUrl().toString():"");		
		product.setCommandLineInstall(commadlinePractice);
	}

    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }	
}
