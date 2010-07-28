package gov.nih.nci.bda.calculator;

public class ProjectBO {
	int averageAutomatedCodeCoveragePersentage;
	int numberOfProjectsInPortfolio;
	int averageNumberOfDeploymentsPerProjectPerYear;
	int averageSizeOfProjects;
	int numberOfTargetEnvironmentsPerProject;
	int averageTechnicalArchitectureComplexity;
	int averageEngineerHourlyRate;
	
	public int getAverageAutomatedCodeCoveragePersentage() {
		return averageAutomatedCodeCoveragePersentage;
	}
	public void setAverageAutomatedCodeCoveragePersentage(
			int averageAutomatedCodeCoveragePersentage) {
		this.averageAutomatedCodeCoveragePersentage = averageAutomatedCodeCoveragePersentage;
	}
	public int getNumberOfProjectsInPortfolio() {
		return numberOfProjectsInPortfolio;
	}
	public void setNumberOfProjectsInPortfolio(int numberOfProjectsInPortfolio) {
		this.numberOfProjectsInPortfolio = numberOfProjectsInPortfolio;
	}
	public int getAverageNumberOfDeploymentsPerProjectPerYear() {
		return averageNumberOfDeploymentsPerProjectPerYear;
	}
	public void setAverageNumberOfDeploymentsPerProjectPerYear(
			int averageNumberOfDeploymentsPerProjectPerYear) {
		this.averageNumberOfDeploymentsPerProjectPerYear = averageNumberOfDeploymentsPerProjectPerYear;
	}
	public int getAverageSizeOfProjects() {
		return averageSizeOfProjects;
	}
	public void setAverageSizeOfProjects(int averageSizeOfProjects) {
		this.averageSizeOfProjects = averageSizeOfProjects;
	}
	public int getNumberOfTargetEnvironmentsPerProject() {
		return numberOfTargetEnvironmentsPerProject;
	}
	public void setNumberOfTargetEnvironmentsPerProject(
			int numberOfTargetEnvironmentsPerProject) {
		this.numberOfTargetEnvironmentsPerProject = numberOfTargetEnvironmentsPerProject;
	}
	public int getAverageTechnicalArchitectureComplexity() {
		return averageTechnicalArchitectureComplexity;
	}
	public void setAverageTechnicalArchitectureComplexity(
			int averageTechnicalArchitectureComplexity) {
		this.averageTechnicalArchitectureComplexity = averageTechnicalArchitectureComplexity;
	}
	public int getAverageEngineerHourlyRate() {
		return averageEngineerHourlyRate;
	}
	public void setAverageEngineerHourlyRate(int averageEngineerHourlyRate) {
		this.averageEngineerHourlyRate = averageEngineerHourlyRate;
	}
}
