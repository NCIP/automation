package gov.nih.nci.bda.certification.listener;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import gov.nih.nci.bda.certification.BuildCertificationHelper;
import gov.nih.nci.bda.certification.CertificationManager;
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
			System.out.println(" executedTargetName " + executedTargetName);
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
			// On exception update with default values 
			BuildCertificationBean bmb = new BuildCertificationBean();
			bmb.setBuildSuccessful(false);
			bmb.setOptional(false); 
			bmb.setProjectName(event.getProject().getProperty("project.name"));
			bmb.setTargetName(event.getProject().getProperty("executed.target.name"));
			bmb.setMapName(event.getProject().getProperty("map.name"));
			bmb.setFailureMessage(ex.getMessage());
			
			BuildCertificationHelper buildHelper = new BuildCertificationHelper(bmb);
			try
			{
				buildHelper.updateProjectBuildStatus();				
			}  
			catch(Exception exp)
	        {
				System.out.println("EXCEPTION IN THE CATCH::::" + exp.getMessage());
				exp.printStackTrace();
	        }	
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
		System.out.println(" Error Message in populate " + event.getException());
		BuildCertificationBean bmb = new BuildCertificationBean();
		String projectName = event.getProject().getProperty("project.name");
		String urlProperty = projectName + ".svn.project.url";
		String projectUrl=event.getProject().getProperty(urlProperty);
		
		
		if(event.getProject().getProperty("is.optional") != null && event.getProject().getProperty("is.optional").equals("true"))
		{
			bmb.setOptional(true);
		}
		else
		{
			bmb.setOptional(false);
		}

		if(event.getException() != null)
		{
			bmb.setBuildSuccessful(false);
			bmb.setFailureMessage(event.getException().getMessage());
			if(!bmb.isOptional())
			{
				bmb.setCertificationStatus(false);
				CertificationManager.projectCertificationStatus = false;				
			}
			bmb.setValue(false);
		}
		else
		{
			bmb.setBuildSuccessful(true);
			System.out.println("CertificationManager.projectCertificationStatus:::::"+CertificationManager.projectCertificationStatus);
			if(CertificationManager.projectCertificationStatus)
			{
				bmb.setCertificationStatus(true);
				CertificationManager.projectCertificationStatus = true;
			}
			if(event.getProject().getProperty("is.value") != null && event.getProject().getProperty("is.value").equals("true"))
			{
					bmb.setValue(true);
					bmb.setPropertyValue(event.getProject().getProperty("certification.property.value"));
			}			
			else
			{
				bmb.setValue(false);
			}
		}
		bmb.setProjectName(projectName);
		bmb.setTargetName(event.getTarget().getName());
		bmb.setMapName(event.getProject().getProperty("map.name"));
		
	
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
