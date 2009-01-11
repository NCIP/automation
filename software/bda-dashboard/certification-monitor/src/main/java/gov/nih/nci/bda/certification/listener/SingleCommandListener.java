package gov.nih.nci.bda.certification.listener;

import java.sql.Timestamp;
import java.util.Iterator;

import gov.nih.nci.bda.certification.BuildCertificationHelper;
import gov.nih.nci.bda.certification.business.BuildCertificationBean;
import gov.nih.nci.bda.certification.domain.BuildHistory;
import gov.nih.nci.bda.certification.util.HibernateUtil;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.hibernate.Query;
import org.hibernate.Session;

public class SingleCommandListener implements BuildListener {

	public void buildFinished(BuildEvent event) {
		// TODO Auto-generated method stub
	/*	
		BuildCertificationBean bmb = populateBuildCertificationBean(event);
		BuildCertificationHelper buildHelper = new BuildCertificationHelper(bmb);
		buildHelper.updateProjectBuildStatus();
	*/
	}

	public void buildStarted(BuildEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Build " + arg0.getProject().getName() +  " Started!");
	}

	public void messageLogged(BuildEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void targetFinished(BuildEvent event) {
		String targetName=event.getTarget().getName();
		System.out.println("Target " + targetName +  " finished!");
	    if(targetName != null && targetName.equals("validation:JADS"))
		{
			BuildCertificationBean bmb = populateBuildCertificationBean(event);
			BuildCertificationHelper buildHelper = new BuildCertificationHelper(bmb);
			buildHelper.updateProjectBuildStatus();			
		}
	}


	public void targetStarted(BuildEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void taskFinished(BuildEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void taskStarted(BuildEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	private BuildCertificationBean populateBuildCertificationBean(BuildEvent event) {

		BuildCertificationBean bmb = new BuildCertificationBean();
		
			
		if(event.getException() != null)
			bmb.setBuildSuccessful(false); 
		else
			bmb.setBuildSuccessful(true);
		bmb.setProjectName(event.getProject().getProperty("project.name"));
		bmb.setTargetName(event.getTarget().getName());
		bmb.setMapName(event.getProject().getProperty("map.name"));

		System.out.println("bmb.ProjectName" + bmb.getProjectName());
		System.out.println("bmb.buildsuccesful" + bmb.isBuildSuccessful());
		System.out.println("bmb.TagegetName" + bmb.getTargetName());
		System.out.println("bmb.MAPName" + bmb.getMapName());
		return bmb;
	}


}
