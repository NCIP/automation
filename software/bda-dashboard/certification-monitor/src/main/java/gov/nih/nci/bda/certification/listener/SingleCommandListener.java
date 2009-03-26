package gov.nih.nci.bda.certification.listener;

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
	private Log certLogger = LogFactory.getLog(SingleCommandListener.class);

	public void buildFinished(BuildEvent event) {
	}

	public void buildStarted(BuildEvent arg0) {
	}

	public void messageLogged(BuildEvent arg0) {

	}

	private void nullifyBuildCertificationBean(BuildCertificationBean bmb) {
		if (bmb != null) {
			bmb.setProjectName(null);
			bmb.setBuildSuccessful(false);
			bmb.setTargetName(null);
			bmb.setMapName(null);
			bmb.setValue(false);
			bmb.setPropertyValue(null);
			bmb.setProjectRepoUrl(null);
		}
	}

	private BuildCertificationBean populateBuildCertificationBean(
			BuildEvent event) {
		certLogger.info(" Populate the BuildCertificationBean ");
		BuildCertificationBean bmb = new BuildCertificationBean();
		String projectName = event.getProject().getProperty("project.name");
		String urlProperty = projectName + ".svn.project.url";
		String projectUrl = event.getProject().getProperty(urlProperty);

		certLogger.info(" Populating  projectName :" + projectName);
		certLogger.info(" Populating ProjectUrl " + projectUrl);
		
		certLogger.info(" Check and populate if the feature is optional ");
		if (event.getProject().getProperty("is.optional") != null
				&& event.getProject().getProperty("is.optional").equals("true")) {
			bmb.setOptional(true);
		} else {
			bmb.setOptional(false);
		}
		
		certLogger.info(" Check and populate the status of the feature and the certification ");
		if (event.getException() != null) {
			bmb.setBuildSuccessful(false);
			bmb.setFailureMessage(event.getException().getMessage());
			if (!bmb.isOptional()) {
				bmb.setCertificationStatus(false);
				CertificationManager.projectCertificationStatus = false;
			}
			//bmb.setValue(false);
		} else {
			bmb.setBuildSuccessful(true);
			certLogger.info(" Update the projectCertificationStatus to true only when the status is not false" + CertificationManager.projectCertificationStatus);
			if (CertificationManager.projectCertificationStatus) {
				bmb.setCertificationStatus(true);
				CertificationManager.projectCertificationStatus = true;
			}
			certLogger.info(" Check and Set certification.property.value only when the feature build is successful :" +event.getProject().getProperty("certification.property.value"));
			
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

	public void targetStarted(BuildEvent arg0) {

	}

	public void taskFinished(BuildEvent arg0) {

	}

	public void taskStarted(BuildEvent arg0) {

	}

}
