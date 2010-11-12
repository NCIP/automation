package gov.nih.nci.bda.calculator;

public class ReportBean {
	int hoursSpentInEachTargetEnvironment;
	int numberOfTestingHoursPerDeployment;
	int totalNumberOfDeploymentHoursPerDeployment;
	int totalNumberOfDeploymentHoursPerYearPerProject;
	int averagePricePerHour;
	int costPerProject;
	int numberOfProjectsInPortfolio;
	int organizationalCost;
	
	public int getHoursSpentInEachTargetEnvironment() {
		return hoursSpentInEachTargetEnvironment;
	}
	public void setHoursSpentInEachTargetEnvironment(
			int hoursSpentInEachTargetEnvironment) {
		this.hoursSpentInEachTargetEnvironment = hoursSpentInEachTargetEnvironment;
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
}
