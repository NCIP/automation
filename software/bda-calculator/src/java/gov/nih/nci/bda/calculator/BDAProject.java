package gov.nih.nci.bda.calculator;

import gov.nih.nci.bda.calculator.util.Calculator;

/**
 * 
 * @author Mahidhar Narra
 */

public class BDAProject extends BaseProject {


	public BDAProject(String projectName) throws Exception {
		super(projectName);
	}


	public void populateProjectConfiguration(String projectName) {
		System.out.println("IN BDA PROJECT POPULATE");
		setDbDeploymentPerTargetEnv(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/dbDeploymentPerTargetEnv/@bda")));
		setDeploymentPerTargetEnv(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/deploymentPerTargetEnv/@bda")));
		setAppContainerConfigurationPerTargetEnv(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/appContainerConfigurationPerTargetEnv/@bda")));
		setTierProvisioning(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/tierProvisioning/@bda")));
		setBuildPromotion(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/buildPromotion/@bda")));
		setOneTimeProcessesPerDeployment(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/oneTimeProcessesPerDeployment/@bda")));
		setContinuousIntegration(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/continuousIntegration/@bda")));
		setBdaImplementationOneTimeCost(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/bdaImplementationOneTimeCost/@bda")));
		setContinuousIntegrationWithBuildThresholds(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/continuousIntegration/@non-bda")));
		
/*		
		setNumberOfDeploymentsPerYear(Integer.parseInt((String) config.getProperty("/projects/project[@name='"+projectName+"']/numberOfDeploymentsPerYear")));
		setPublishing(Integer.parseInt((String) config.getProperty("/projects/project[@name='"+projectName+"']/publishing")));
		setContinuousIntegrationWithBuildThresholds(Integer.parseInt((String) config.getProperty("/projects/project[@name='"+projectName+"']/continuousIntegrationWithBuildThresholds")));
		setTechnicalArchitectureComplexity(Integer.parseInt((String) config.getProperty("/projects/project[@name='"+projectName+"']/technicalArchitectureComplexity")));
		
		setHoursPerTargetEnvironment(Integer.parseInt((String) config.getProperty("/projects/project[@name='"+projectName+"']/hoursPerTargetEnvironment")));
		setNumberOfTestingHoursPerDeployment(Integer.parseInt((String) config.getProperty("/projects/project[@name='"+projectName+"']/numberOfTestingHoursPerDeployment")));
		setTotalNumberOfDeploymentHoursPerDeployment(Integer.parseInt((String) config.getProperty("/projects/project[@name='"+projectName+"']/totalNumberOfDeploymentHoursPerDeployment")));
		setTotalNumberOfDeploymentHoursPerYearPerProject(Integer.parseInt((String) config.getProperty("/projects/project[@name='"+projectName+"']/totalNumberOfDeploymentHoursPerYearPerProject")));
		setAveragePricePerHour(Integer.parseInt((String) config.getProperty("/projects/project[@name='"+projectName+"']/averagePricePerHour")));
		setCostPerProject(Integer.parseInt((String) config.getProperty("/projects/project[@name='"+projectName+"']/costPerProject")));
		setNumberOfProjectsInPortfolio(Integer.parseInt((String) config.getProperty("/projects/project[@name='"+projectName+"']/numberOfProjectsInPortfolio")));
		setOrganizationalCost(Integer.parseInt((String) config.getProperty("/projects/project[@name='"+projectName+"']/organizationalCost")));
*/
	}

	
	public ReportBean calculateBudget(ProjectBO projectBO) throws Exception {
		System.out.println("Calculate BDA Budget");		
		Calculator calculator = new Calculator();
		ReportBean reportBean = new ReportBean();
		reportBean.setHoursSpentInEachTargetEnvironment(calculateHoursSpentInEachTargetEnv(projectBO));
		reportBean.setNumberOfTestingHoursPerDeployment(calculateNumberOfTestingHours(projectBO));
		reportBean.setTotalNumberOfDeploymentHoursPerDeployment(calculateTotalNumberOfDeploymentHoursPerDeployment(projectBO));
		reportBean.setTotalNumberOfDeploymentHoursPerYearPerProject(calculateTotalNumberOfDeploymentHoursPerYearPerProject(projectBO));
		reportBean.setAveragePricePerHour(projectBO.getAverageEngineerHourlyRate());
		reportBean.setCostPerProject(calculateCostPerProject(projectBO));
		reportBean.setNumberOfProjectsInPortfolio(projectBO.getNumberOfProjectsInPortfolio());
		reportBean.setOrganizationalCost(calculateOrganizationalCost(projectBO));
		return reportBean;
	}

}
