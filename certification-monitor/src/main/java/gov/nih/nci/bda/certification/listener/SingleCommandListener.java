package gov.nih.nci.bda.certification.listener;

import java.util.ArrayList;

import gov.nih.nci.bda.certification.BuildCertificationHelper;
import gov.nih.nci.bda.certification.CertificationManager;
import gov.nih.nci.bda.certification.business.BuildCertificationBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;

/**
 * @author narram
 *
 */
public class SingleCommandListener implements BuildListener {

    public static final String WIKI_FAILED = "(x)";

	private Log certLogger = LogFactory.getLog(SingleCommandListener.class);

	ArrayList taskList = new ArrayList();

	public void buildFinished(BuildEvent event) {
	}

	public void buildStarted(BuildEvent arg0) {
	}

	public void messageLogged(BuildEvent arg0) {

	}

	private BuildCertificationBean populateBuildCertificationBean(
			BuildEvent event) {
		certLogger.info(" Populate the BuildCertificationBean ");
		BuildCertificationBean bmb = new BuildCertificationBean();
		String projectName = event.getProject().getProperty("project.name");
		String projectUrl = event.getProject().getProperty("svn.project.url");
		String macroList = event.getProject().getProperty( event.getTarget().getName() + ".macro.list");

        certLogger.info("map.name=" + event.getProject().getProperty("map.name"));

		certLogger.info(" Populating  projectName :" + projectName);
		certLogger.info(" Populating ProjectUrl " + projectUrl);
		certLogger.info(" macroList " + macroList);
		certLogger.info(" property name " + event.getProject().getProperty( event.getTarget().getName() + ".macro.list"));

		certLogger.info(" Check and populate if the feature is optional ");
		if (event.getProject().getProperty("is.optional") != null
				&& event.getProject().getProperty("is.optional").equals("true")) {
			bmb.setOptional(true);
		} else {
			bmb.setOptional(false);
		}

		certLogger.info(" Check to see for systems waiver ");
		if (event.getProject().getProperty("is.systems.waiver") != null
				&& event.getProject().getProperty("is.systems.waiver").equals("true")) {
			bmb.setSystemsWaiver(true);
		} else {
			bmb.setSystemsWaiver(false);
		}

		certLogger.info(" Check and populate the status of the feature and the certification ");
		if (event.getException() != null || !checkMacroList(macroList,event)) {
			certLogger.info(" macro list value False " + checkMacroList(macroList,event));
			bmb.setBuildSuccessful(false);
			bmb.setFailureMessage(event.getException().getMessage());
			if (bmb.isOptional() || bmb.isSystemsWaiver())
			{
				certLogger.info(" IS OPTIONAL OR HAS SYSTEMS WAIVER  " );
				if (CertificationManager.projectCertificationStatus)
				{
					bmb.setCertificationStatus(true);
					CertificationManager.projectCertificationStatus = true;
				}else
				{
					bmb.setCertificationStatus(false);
					CertificationManager.projectCertificationStatus = false;
				}
			}else
			{
				bmb.setCertificationStatus(false);
				CertificationManager.projectCertificationStatus = false;
			}
			//bmb.setValue(false);
		} else {
			bmb.setBuildSuccessful(true);
			certLogger.info(" macro list value TURE " + checkMacroList(macroList,event));
			certLogger.info(" Check and populate the status of the feature and the certification ");
			certLogger.info(" Update the projectCertificationStatus to true only when the status is not false" + CertificationManager.projectCertificationStatus);
            String certificationPropertyValue = event.getProject().getProperty("certification.property.value");

            certLogger.info(" certificationPropertyValue=" + certificationPropertyValue );

            if (certificationPropertyValue.contains( WIKI_FAILED ))
            {
                certLogger.info(" contains (X)... failing...=" );
                bmb.setCertificationStatus(false);
                CertificationManager.projectCertificationStatus = false;
            }

			if (CertificationManager.projectCertificationStatus) {
				bmb.setCertificationStatus(true);
				CertificationManager.projectCertificationStatus = true;
			}
			certLogger.info(" Check and Set certification.property.value only when the feature build is successful :" + event.getProject().getProperty("certification.property.value"));

		}
		if (event.getProject().getProperty("is.value") != null
				&& event.getProject().getProperty("is.value")
						.equals("true")) {
			bmb.setValue(true);
			certLogger.info(" certification.property.value: " +event.getProject().getProperty("certification.property.value"));
			bmb.setPropertyValue(event.getProject().getProperty(
					"certification.property.value"));
		} else {
			bmb.setValue(false);
		}

		certLogger.info(" Set ProjectName:" + projectName +" TargetName:"+event.getTarget().getName()+ " MapName:" +event.getProject().getProperty("map.name"));
		bmb.setProjectName(projectName);
		bmb.setTargetName(event.getTarget().getName());
		bmb.setMapName(event.getProject().getProperty("map.name"));

		if (projectUrl != null) {
			bmb.setProjectRepoUrl(projectUrl);
		}

		return bmb;
	}

	private boolean checkMacroList(String macroList,BuildEvent event) {
		/*
		if (macroList != null)
		{
			String macroArray[] = macroList.split(",");
			for ( int i =0 ; i < macroArray.length ; i++){
				if (!taskList.contains(macroArray[i])){
					certLogger.info("Macro " + macroArray[i] + " was not called during the execution path ");
					event.setException(new Exception("Macro " + macroArray[i] + " was not called during the execution path"));
					return false;
				}
			}
		}
		*/
		String execTaskList = event.getProject().getProperty("task.list");
		certLogger.info("execTaskList:: " + execTaskList);
		if(execTaskList != null && !execTaskList.equals(""))
		{
			certLogger.info("macroList:: " + macroList);
			if(macroList != null && !macroList.equals(""))
			{
				String macroArray[] = macroList.split(",");

				for ( int i =0 ; i < macroArray.length ; i++)
				{
					certLogger.info("Checking for the Macro " + macroArray[i] + " called during the execution path " + execTaskList.contains(macroArray[i]));
					if (!execTaskList.contains(macroArray[i]))
					{
						certLogger.info("Macro " + macroArray[i] + " was not called during the execution path ");
						event.setException(new Exception("Macro " + macroArray[i] + " was not called during the execution path"));
						return false;
					}
					else
					{
						certLogger.info("Macro " + macroArray[i] + " was called during the execution path  " + execTaskList.contains(macroArray[i]));
					}
				}
			}
		}
		return true;
	}

	public void targetFinished(BuildEvent event) {
		try {
			String targetName = event.getTarget().getName();
			String executedTargetName = event.getProject().getProperty(
					"executed.target.name");
			certLogger.info("Target " + targetName + " finished!");
			certLogger.info(" ExecutedTargetName " + executedTargetName);
			if (targetName != null && targetName.equals(executedTargetName)) {
				BuildCertificationBean bmb = populateBuildCertificationBean(event);
				BuildCertificationHelper buildHelper = new BuildCertificationHelper(
						bmb);
				certLogger.info(" Update the Project Status ");
				buildHelper.updateProjectBuildStatus();
				// nullifyBuildCertificationBean(bmb);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			certLogger.info(" Exception occured and updating with default values ");
			BuildCertificationBean bmb = new BuildCertificationBean();
			bmb.setBuildSuccessful(false);
			bmb.setOptional(false);
			bmb.setProjectName(event.getProject().getProperty("project.name"));
			bmb.setTargetName(event.getProject().getProperty(
					"executed.target.name"));
			bmb.setMapName(event.getProject().getProperty("map.name"));
			bmb.setFailureMessage(ex.getMessage());

			BuildCertificationHelper buildHelper = new BuildCertificationHelper(
					bmb);
			try {
				buildHelper.updateProjectBuildStatus();
			} catch (Exception exp) {
				certLogger.info("EXCEPTION IN THE CATCH::::"
						+ exp.getMessage());
				exp.printStackTrace();
			}
		}
	}

	public void targetStarted(BuildEvent event) {
		System.out.println("TARGET "+event.getTarget().getName()+" STARTED....");
	}

	public void taskFinished(BuildEvent event) {
        certLogger.info("taskFinished:" + event.getTask().getTaskName());
	}

	private void addTask(String taskName) {
		taskList.add(taskName);
	}

	public void taskStarted(BuildEvent event) {
	}

}
