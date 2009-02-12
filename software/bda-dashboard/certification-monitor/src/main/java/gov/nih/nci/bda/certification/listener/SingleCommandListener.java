package gov.nih.nci.bda.certification.listener;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import gov.nih.nci.bda.certification.BuildCertificationHelper;
import gov.nih.nci.bda.certification.business.BuildCertificationBean;
import gov.nih.nci.bda.certification.domain.BuildHistory;
import gov.nih.nci.bda.certification.util.HibernateUtil;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.*;
import org.hibernate.criterion.*;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


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
		try
		{
			String targetName=event.getTarget().getName();
			String executedTargetName=event.getProject().getProperty("executed.target.name");
			System.out.println("Target " + targetName +  " finished!");
		    if(targetName != null && targetName.equals(executedTargetName))
			{
		    	BuildCertificationBean bmb  = populateBuildCertificationBean(event);
				BuildCertificationHelper buildHelper = new BuildCertificationHelper(bmb);
				buildHelper.updateProjectBuildStatus();
				//nullifyBuildCertificationBean(bmb);	
			}	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
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
		String projectName = event.getProject().getProperty("project.name");
		String urlProperty = projectName + ".svn.project.url";
		String projectUrl=event.getProject().getProperty(urlProperty);
		

		if(event.getException() != null)
			bmb.setBuildSuccessful(false); 
		else
			bmb.setBuildSuccessful(true);
		
		bmb.setProjectName(projectName);
		bmb.setTargetName(event.getTarget().getName());
		bmb.setMapName(event.getProject().getProperty("map.name"));

		if(event.getProject().getProperty("is.value") != null && event.getProject().getProperty("is.value").equals("true"))
		{
				bmb.setValue(true);
				bmb.setPropertyValue(event.getProject().getProperty("certification.property.value"));
		}			
		else
		{
			bmb.setValue(false);
		}
		
	
		if(projectUrl != null)
		{
			bmb.setProjectRepoUrl(projectUrl);
		}
		return bmb;
	}

	private void nullifyBuildCertificationBean(BuildCertificationBean bmb)	
	{
		if (bmb != null)
		{
			bmb.setProjectName(null);
			bmb.setBuildSuccessful(false);
			bmb.setTargetName(null);
			bmb.setMapName(null);
			bmb.setValue(false);
			bmb.setPropertyValue(null);
			bmb.setProjectRepoUrl(null);
		}
	}
	

}
