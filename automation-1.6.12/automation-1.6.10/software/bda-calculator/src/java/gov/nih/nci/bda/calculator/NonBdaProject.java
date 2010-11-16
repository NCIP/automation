package gov.nih.nci.bda.calculator;

import gov.nih.nci.bda.calculator.util.Calculator;

/**
 *
 * @author Mahidhar Narra
 */

public class NonBdaProject extends BaseProject{
	public NonBdaProject(String projectName) throws Exception {
		super(projectName);
	}

	public void populateProjectConfiguration(String projectName) {
		System.out.println("IN NON-BDA PROJECT POPULATE");
		setDbDeploymentPerTargetEnv(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/dbDeploymentPerTargetEnv/@non-bda")));
		setDeploymentPerTargetEnv(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/deploymentPerTargetEnv/@non-bda")));
		setAppContainerConfigurationPerTargetEnv(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/appContainerConfigurationPerTargetEnv/@non-bda")));
		setTierProvisioning(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/tierProvisioning/@non-bda")));
		setBuildPromotion(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/buildPromotion/@non-bda")));
		setOneTimeProcessesPerDeployment(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/oneTimeProcessesPerDeployment/@non-bda")));
		setContinuousIntegration(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/continuousIntegration/@non-bda")));
		setBdaImplementationOneTimeCost(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/bdaImplementationOneTimeCost/@non-bda")));
		setContinuousIntegrationWithBuildThresholds(Integer.parseInt((String) config.getProperty("/organizations/organization[@name='"+projectName+"']/continuousIntegration/@bda")));
	}

	public ReportBean calculateBudget(ProjectBO projectBO) throws Exception
	{
			System.out.println("Calculate NON-BDA Budget");
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
