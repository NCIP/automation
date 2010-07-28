package gov.nih.nci.bda.calculator;
/**
 *
 * @author Mahidhar Narra
 */

public interface Project {
	public ReportBean calculateBudget(ProjectBO projectBO) throws Exception;
	public void populateProjectConfiguration(String projectName) throws Exception;
}
