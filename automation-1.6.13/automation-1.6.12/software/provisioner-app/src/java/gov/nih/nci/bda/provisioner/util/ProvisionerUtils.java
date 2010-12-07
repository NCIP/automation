package gov.nih.nci.bda.provisioner.util;


import java.io.File;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;


public class ProvisionerUtils {

	public static void executeCommand(String buildFileLocation, String targetName)
	{
		Project project = new Project();
		project.init();

		DefaultLogger logger = new DefaultLogger();
		logger.setMessageOutputLevel(Project.MSG_INFO);
		logger.setErrorPrintStream(System.err);
		logger.setOutputPrintStream(System.out);
		project.addBuildListener(logger);

		File buildFile = new File(buildFileLocation);
		ProjectHelper helper = ProjectHelper.getProjectHelper();
		project.addReference("ant.projectHelper", helper);
		helper.parse(project, buildFile);
		try 
		{
			project.executeTarget(targetName);
		} catch (Exception e)
		{
			System.out.println ("Exception occured while executing the target " + targetName + " ::" + e.getMessage());
		}
	}


	
	public static void executeTarget(String buildFileLocation, String targetName)
	{
		Project project = new Project();
		project.init();

		DefaultLogger logger = new DefaultLogger();
		logger.setMessageOutputLevel(Project.MSG_INFO);
		logger.setErrorPrintStream(System.err);
		logger.setOutputPrintStream(System.out);
		project.addBuildListener(logger);

		File buildFile = new File(buildFileLocation);
		ProjectHelper helper = ProjectHelper.getProjectHelper();
		project.addReference("ant.projectHelper", helper);
		helper.parse(project, buildFile);
		try 
		{
			project.executeTarget(targetName);
		} catch (Exception e)
		{
			System.out.println ("Exception occured while executing the target " + targetName + " ::" + e.getMessage());
		}
	}	
}
