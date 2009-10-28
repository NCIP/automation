class CalculatorController {
	gov.nih.nci.bda.calculator.ProjectBO projectBO = new gov.nih.nci.bda.calculator.ProjectBO()
	gov.nih.nci.bda.calculator.ReportBean reportBeanBDA = new gov.nih.nci.bda.calculator.ReportBean()
	gov.nih.nci.bda.calculator.ReportBean reportBeanNonBDA = new gov.nih.nci.bda.calculator.ReportBean()
	
	def index = { }
    
	def calculate = {  	
			populateProjectBO(params);
			try {
			  gov.nih.nci.bda.calculator.Project bdaProject = new gov.nih.nci.bda.calculator.BDAProject("cbiit");
			  gov.nih.nci.bda.calculator.Project nonBdaProject = new gov.nih.nci.bda.calculator.NonBdaProject("cbiit");
			  reportBeanBDA = bdaProject.calculateBudget(projectBO);
			  reportBeanNonBDA = nonBdaProject.calculateBudget(projectBO);
			} catch (Exception e) {
			e.printStackTrace();
			}
			render(view: 'report', model: [ reportBeanBDA: reportBeanBDA,reportBeanNonBDA: reportBeanNonBDA ])

	}

	def populateProjectBO(params) {
		projectBO.setAverageAutomatedCodeCoveragePersentage(Integer.parseInt(params.averageAutomatedCodeCoveragePersentage));
		projectBO.setAverageEngineerHourlyRate(Integer.parseInt(params.averageEngineerHourlyRate));
		projectBO.setAverageNumberOfDeploymentsPerProjectPerYear(Integer.parseInt(params.averageNumberOfDeploymentsPerProjectPerYear));
		projectBO.setAverageSizeOfProjects(Integer.parseInt(params.averageSizeOfProjects));
		projectBO.setAverageTechnicalArchitectureComplexity(Integer.parseInt(params.averageTechnicalArchitectureComplexity));
		projectBO.setNumberOfProjectsInPortfolio(Integer.parseInt(params.numberOfProjectsInPortfolio));
		projectBO.setNumberOfTargetEnvironmentsPerProject(Integer.parseInt(params.numberOfTargetEnvironmentsPerProject));
	}
}
