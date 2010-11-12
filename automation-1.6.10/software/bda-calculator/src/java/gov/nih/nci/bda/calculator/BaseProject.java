package gov.nih.nci.bda.calculator;

import org.apache.commons.configuration.Configuration;

import gov.nih.nci.bda.calculator.util.Calculator;
import gov.nih.nci.bda.calculator.util.ConfigurationHelper;

/**
 *
 * @author Mahidhar Narra
 */

public abstract class BaseProject implements Project{
	int dbDeploymentPerTargetEnv;
	int DeploymentPerTargetEnv;
	int appContainerConfigurationPerTargetEnv;
	int tierProvisioning;
	int buildPromotion;
	int oneTimeProcessesPerDeployment;
	int continuousIntegration;
	int bdaImplementationOneTimeCost;
	int continuousIntegrationWithBuildThresholds;


	/*
	int codeReviewHours;
	int automatedCodeCoveragePercentage;
	int sizeOfApplication;
	int numberOfTargetEnvironments;
	int numberOfDeploymentsPerYear;
	int publishing;
	int continuousIntegrationWithBuildThresholds; //(calculated)
	int technicalArchitectureComplexity;
	int buildPromotion; //(calculated)
	int hoursPerTargetEnvironment;
	int numberOfTestingHoursPerDeployment;
	int totalNumberOfDeploymentHoursPerDeployment;
	int totalNumberOfDeploymentHoursPerYearPerProject;
	int averagePricePerHour;
	int costPerProject;
	int numberOfProjectsInPortfolio;
	int organizationalCost;
	*/
	protected static Configuration config;
	
	public int getDbDeploymentPerTargetEnv() {
		return dbDeploymentPerTargetEnv;
	}
	public void setDbDeploymentPerTargetEnv(int dbDeploymentPerTargetEnv) {
		this.dbDeploymentPerTargetEnv = dbDeploymentPerTargetEnv;
	}
	public int getDeploymentPerTargetEnv() {
		return DeploymentPerTargetEnv;
	}
	public void setDeploymentPerTargetEnv(int deploymentPerTargetEnv) {
		DeploymentPerTargetEnv = deploymentPerTargetEnv;
	}
	public int getAppContainerConfigurationPerTargetEnv() {
		return appContainerConfigurationPerTargetEnv;
	}
	public void setAppContainerConfigurationPerTargetEnv(
			int appContainerConfigurationPerTargetEnv) {
		this.appContainerConfigurationPerTargetEnv = appContainerConfigurationPerTargetEnv;
	}
	public int getTierProvisioning() {
		return tierProvisioning;
	}
	public void setTierProvisioning(int tierProvisioning) {
		this.tierProvisioning = tierProvisioning;
	}
	public int getBuildPromotion() {
		return buildPromotion;
	}
	public void setBuildPromotion(int buildPromotion) {
		this.buildPromotion = buildPromotion;
	}
	public int getOneTimeProcessesPerDeployment() {
		return oneTimeProcessesPerDeployment;
	}
	public void setOneTimeProcessesPerDeployment(int oneTimeProcessesPerDeployment) {
		this.oneTimeProcessesPerDeployment = oneTimeProcessesPerDeployment;
	}
	public int getContinuousIntegration() {
		return continuousIntegration;
	}
	public void setContinuousIntegration(int continuousIntegration) {
		this.continuousIntegration = continuousIntegration;
	}
	public int getBdaImplementationOneTimeCost() {
		return bdaImplementationOneTimeCost;
	}
	public void setBdaImplementationOneTimeCost(int bdaImplementationOneTimeCost) {
		this.bdaImplementationOneTimeCost = bdaImplementationOneTimeCost;
	}
	
	public int getContinuousIntegrationWithBuildThresholds() {
		return continuousIntegrationWithBuildThresholds;
	}
	public void setContinuousIntegrationWithBuildThresholds(
			int continuousIntegrationWithBuildThresholds) {
		this.continuousIntegrationWithBuildThresholds = continuousIntegrationWithBuildThresholds;
	}	
	/*
	public int getCodeReviewHours() {
		return codeReviewHours;
	}
	public void setCodeReviewHours(int codeReviewHours) {
		this.codeReviewHours = codeReviewHours;
	}
	public int getAutomatedCodeCoveragePercentage() {
		return automatedCodeCoveragePercentage;
	}
	public void setAutomatedCodeCoveragePercentage(
			int automatedCodeCoveragePercentage) {
		this.automatedCodeCoveragePercentage = automatedCodeCoveragePercentage;
	}
	public int getSizeOfApplication() {
		return sizeOfApplication;
	}
	public void setSizeOfApplication(int sizeOfApplication) {
		this.sizeOfApplication = sizeOfApplication;
	}
	public int getNumberOfTargetEnvironments() {
		return numberOfTargetEnvironments;
	}
	public void setNumberOfTargetEnvironments(int numberOfTargetEnvironments) {
		this.numberOfTargetEnvironments = numberOfTargetEnvironments;
	}
	public int getNumberOfDeploymentsPerYear() {
		return numberOfDeploymentsPerYear;
	}
	public void setNumberOfDeploymentsPerYear(int numberOfDeploymentsPerYear) {
		this.numberOfDeploymentsPerYear = numberOfDeploymentsPerYear;
	}
	public int getPublishing() {
		return publishing;
	}
	public void setPublishing(int publishing) {
		this.publishing = publishing;
	}
	public int getContinuousIntegrationWithBuildThresholds() {
		return continuousIntegrationWithBuildThresholds;
	}
	public void setContinuousIntegrationWithBuildThresholds(
			int continuousIntegrationWithBuildThresholds) {
		this.continuousIntegrationWithBuildThresholds = continuousIntegrationWithBuildThresholds;
	}
	public int getTechnicalArchitectureComplexity() {
		return technicalArchitectureComplexity;
	}
	public void setTechnicalArchitectureComplexity(
			int technicalArchitectureComplexity) {
		this.technicalArchitectureComplexity = technicalArchitectureComplexity;
	}

	public int getHoursPerTargetEnvironment() {
		return hoursPerTargetEnvironment;
	}
	public void setHoursPerTargetEnvironment(int hoursPerTargetEnvironment) {
		this.hoursPerTargetEnvironment = hoursPerTargetEnvironment;
	}
	public int getNumberOfTestingHoursPerDeployment() {
		return numberOfTestingHoursPerDeployment;
	}
	public void setNumberOfTestingHoursPerDeployment(
			int numberOfTestingHoursPerDeployment) {
		this.numberOfTestingHoursPerDeployment = numberOfTestingHoursPerDeployment;
	}
	public int getTotalNumberOfDeploymentHoursPerDeployment() {
		return totalNumberOfDeploymentHoursPerDeployment;
	}
	public void setTotalNumberOfDeploymentHoursPerDeployment(
			int totalNumberOfDeploymentHoursPerDeployment) {
		this.totalNumberOfDeploymentHoursPerDeployment = totalNumberOfDeploymentHoursPerDeployment;
	}
	public int getTotalNumberOfDeploymentHoursPerYearPerProject() {
		return totalNumberOfDeploymentHoursPerYearPerProject;
	}
	public void setTotalNumberOfDeploymentHoursPerYearPerProject(
			int totalNumberOfDeploymentHoursPerYearPerProject) {
		this.totalNumberOfDeploymentHoursPerYearPerProject = totalNumberOfDeploymentHoursPerYearPerProject;
	}
	public int getAveragePricePerHour() {
		return averagePricePerHour;
	}
	public void setAveragePricePerHour(int averagePricePerHour) {
		this.averagePricePerHour = averagePricePerHour;
	}
	public int getCostPerProject() {
		return costPerProject;
	}
	public void setCostPerProject(int costPerProject) {
		this.costPerProject = costPerProject;
	}
	public int getNumberOfProjectsInPortfolio() {
		return numberOfProjectsInPortfolio;
	}
	public void setNumberOfProjectsInPortfolio(int numberOfProjectsInPortfolio) {
		this.numberOfProjectsInPortfolio = numberOfProjectsInPortfolio;
	}
	public int getOrganizationalCost() {
		return organizationalCost;
	}
	public void setOrganizationalCost(int organizationalCost) {
		this.organizationalCost = organizationalCost;
	}
	*/
	public BaseProject(String projectName) throws Exception {		
		initProject(projectName);
	}
	protected void initProject(String projectName) throws Exception {
		config = ConfigurationHelper.getConfiguration("project_config.xml");
		populateProjectConfiguration(projectName);
	}
	protected int calculateNumberOfTestingHours(ProjectBO projectBO) {
		Calculator calculator = new Calculator();
		float result=0;
		result = calculator.calculate("div", projectBO.getAverageAutomatedCodeCoveragePersentage(), 100);
		result = calculator.calculate("mult", projectBO.getAverageSizeOfProjects(), result);
		result = calculator.calculate("sub", projectBO.getAverageSizeOfProjects(), result);
		result = calculator.calculate("mult", projectBO.getAverageTechnicalArchitectureComplexity(), result);
		return (int) result;
	}	
	protected int calculateHoursSpentInEachTargetEnv(ProjectBO projectBO) {
		Calculator calculator = new Calculator();
		float result;
		result = calculator.calculate("mult", projectBO.getNumberOfTargetEnvironmentsPerProject(), getBuildPromotion());
		result = calculator.calculate("add", getDbDeploymentPerTargetEnv(), result);
		result = calculator.calculate("add", getDeploymentPerTargetEnv(), result);
		result = calculator.calculate("add", getAppContainerConfigurationPerTargetEnv(), result);
		result = calculator.calculate("add", getTierProvisioning(), result);
		result = calculator.calculate("mult", getContinuousIntegration(), result);
		return (int) result;		
	}
	protected int calculateTotalNumberOfDeploymentHoursPerDeployment(ProjectBO projectBO) {
		Calculator calculator = new Calculator();
		float result;
		result = calculateHoursSpentInEachTargetEnv(projectBO);
		result = calculator.calculate("mult", projectBO.getNumberOfTargetEnvironmentsPerProject(), result);
		result = calculator.calculate("add", calculator.calculate("div", projectBO.getAverageSizeOfProjects(), getContinuousIntegrationWithBuildThresholds()), result);
		return (int) result;		
	}
	protected int calculateTotalNumberOfDeploymentHoursPerYearPerProject(ProjectBO projectBO) {
		Calculator calculator = new Calculator();
		float result;
		result = calculateHoursSpentInEachTargetEnv(projectBO);
		result = calculator.calculate("sub", result, getTierProvisioning());
		result = calculator.calculate("add", result, calculateNumberOfTestingHours(projectBO));
		result = calculator.calculate("mult", result, projectBO.getNumberOfTargetEnvironmentsPerProject());
		result = calculator.calculate("mult", result, projectBO.getAverageNumberOfDeploymentsPerProjectPerYear());
		return (int) result;		
	}
	protected int calculateCostPerProject(ProjectBO projectBO) {
		Calculator calculator = new Calculator();
		float result;
		result = calculateTotalNumberOfDeploymentHoursPerYearPerProject(projectBO);
		result = calculator.calculate("mult", result, projectBO.getAverageEngineerHourlyRate());
		return (int) result;		
	}	
	protected int calculateOrganizationalCost(ProjectBO projectBO) {
		Calculator calculator = new Calculator();
		float result;
		result = calculateCostPerProject(projectBO);
		result = calculator.calculate("mult", result, projectBO.getNumberOfProjectsInPortfolio());
		return (int) result;		
	}		
}
