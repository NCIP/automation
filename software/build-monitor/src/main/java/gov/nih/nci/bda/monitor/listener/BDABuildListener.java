package gov.nih.nci.bda.monitor.listener;

import java.sql.Timestamp;
import java.util.Iterator;

import gov.nih.nci.bda.monitor.BuildMonitorHelper;
import gov.nih.nci.bda.monitor.business.BuildMonitorBean;
import gov.nih.nci.bda.monitor.domain.BuildHistory;
import gov.nih.nci.bda.monitor.domain.BuildStatus;
import gov.nih.nci.bda.monitor.util.HibernateUtil;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.hibernate.Query;
import org.hibernate.Session;

public class BDABuildListener implements BuildListener {

	public void buildFinished(BuildEvent event) {
		// TODO Auto-generated method stub
		
		BuildMonitorBean bmb = populateBuildMonitorBean(event);
		BuildMonitorHelper buildHelper = new BuildMonitorHelper(bmb);
		buildHelper.updateProjectBuildStatus();
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
	    if(targetName != null && targetName.equals("clean"))
		{
			System.out.println("Target INSIDE " + targetName +  " finished!");
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
	
	private BuildMonitorBean populateBuildMonitorBean(BuildEvent event) {

		BuildMonitorBean bmb = new BuildMonitorBean();
		bmb.setPropertiesFileName(event.getProject().getProperty("properties.file"));
		if(event.getException() != null)
			bmb.setBuildSuccessful(false); 
		else
			bmb.setBuildSuccessful(true);
		bmb.setProjectName(event.getProject().getName());
	
		return bmb;
	}


}
