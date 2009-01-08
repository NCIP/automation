package gov.nih.nci.bda.certification;

import gov.nih.nci.bda.certification.business.BuildCertificationBean;
import gov.nih.nci.bda.certification.domain.BuildHistory;
import gov.nih.nci.bda.certification.domain.ProjectCertificationStatus;
import gov.nih.nci.bda.certification.util.HibernateUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;

public class BuildCertificationHelper {

	BuildCertificationBean bmb;
	
	public BuildCertificationHelper()
	{
		super();
	}

	public BuildCertificationHelper(BuildCertificationBean bmb)
	{
		this.bmb = bmb;
	}
		
	public void updateProjectBuildStatus()
	{				
		ProjectCertificationStatus pbs = null;
		String projectName = bmb.getProjectName();
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction(); 		    
	    String wikiFailedString = "'["+BuildMonitorConstants.WIKI_FAILED+"|"+BuildMonitorConstants.ANTHILL_ADDRESS+"]'";
	    String wikiSuccessString = "'["+BuildMonitorConstants.WIKI_SUCCESSFUL+"|"+BuildMonitorConstants.ANTHILL_ADDRESS+"]'";
	    
		if (!bmb.isBuildSuccessful()) 
		{	
			System.out.println("SINGLE COMMAND FAILED");
			// build failed
			/*
		    Query query = session.createQuery( " from ProjectBuildStatus where product= :projectName ");
		    query.setString("projectName", projectName);
			pbs= (ProjectCertificationStatus) query.uniqueResult();
			if(pbs != null )
			{	
		    	try {		    		
					pbs.getClass().getMethod(methodName, new Class[] {String.class}).invoke(pbs,wikiFailedString); 
				}
		    	catch (Exception e) {				
					e.printStackTrace();
				}
		    	session.update(pbs);
		    }
		    else
		    {
		    	pbs = new ProjectBuildStatus();
		    	Method[] methods = pbs.getClass().getMethods();
		    	for (int i = 0; i < methods.length; i++)
		    	{
		    		if(methods[i].getName().equals(methodName))
    				{
		    			try 
		    			{	System.out.println("wikiFailedString::::" + wikiFailedString);	    				
		    				methods[i].invoke(pbs,new String(wikiFailedString));
		    			}
				    	catch (Exception e) {				
							e.printStackTrace();
						}
    				}
		    		else if(methods[i].getName().startsWith("set"))
		    		{
		    			if(!(methods[i].getName().equals("setProduct") || methods[i].getName().equals("setId")))
						{
			    			try 
			    			{
			    				System.out.println("WIKI_NOTBUILD::::" );
			    				methods[i].invoke(pbs,new String(BuildMonitorConstants.WIKI_NOTBUILD));
							}
			    			catch (Exception e) 
			    			{				
			    				e.printStackTrace();
			    			}
						}
		    		}
		    	}
		    	pbs.setProduct(projectName);
		    	session.save(pbs);		
		    }

	    	BuildHistory bh = new BuildHistory();
	    	bh.setProduct(projectName);
	    	bh.setBuildTier(buildTier);
	    	bh.setBuildStatus(BuildMonitorConstants.BUILD_FAILED);
	    	bh.setWikiBuildStatus(BuildMonitorConstants.WIKI_FAILED);
	    	bh.setLastBuildTime(new Timestamp(System.currentTimeMillis()));
	    	session.save(bh);	
		    */	
		    
		}
		else
		{	
			System.out.println("SINGLE COMMAND SUCCESSFUL");
			// build successful	 
			/*
		    Query query = session.createQuery( " from ProjectBuildStatus where product= :projectName ");
		    query.setString("projectName", projectName);
			pbs= (ProjectBuildStatus) query.uniqueResult();

			if(pbs != null )
			{						    		
		    	try {
					pbs.getClass().getMethod(methodName, new Class[] {String.class}).invoke(pbs,wikiSuccessString); 
				}
		    	catch (Exception e) {				
					e.printStackTrace();
				}
		    	session.update(pbs);
		    }
		    else
		    {
		    	pbs = new ProjectBuildStatus();
		    	Method[] methods = pbs.getClass().getMethods();
		    	for (int i = 0; i < methods.length; i++)
		    	{
		    		if(methods[i].getName().equals(methodName))
    				{
		    			try 
		    			{
		    				methods[i].invoke(pbs,new String(wikiSuccessString));
		    			}
				    	catch (Exception e) {				
							e.printStackTrace();
						}
    				}
		    		else if(methods[i].getName().startsWith("set"))
		    		{
		    			if(!(methods[i].getName().equals("setProduct") || methods[i].getName().equals("setId")))
						{
			    			try 
			    			{
			    				methods[i].invoke(pbs,new String(BuildMonitorConstants.WIKI_NOTBUILD));
							}
			    			catch (Exception e) 
			    			{				
			    				e.printStackTrace();
			    			}
						}
		    		}
		    	}
		    	pbs.setProduct(projectName);
		    	session.save(pbs);		    	
		    }

	    	BuildHistory bh = new BuildHistory();		  
	    	bh.setProduct(projectName);
	    	bh.setBuildTier(buildTier);
	    	bh.setBuildStatus(BuildMonitorConstants.BUILD_SUCCESSFUL);
	    	bh.setWikiBuildStatus(BuildMonitorConstants.WIKI_SUCCESSFUL);
	    	bh.setLastBuildTime(new Timestamp(System.currentTimeMillis()));
	    	session.save(bh);
		     */
		}
	 //   session.getTransaction().commit();
	   
	}
	
	
	private String getTierName(String  propertyFile) {
		String tierName = "local";
				
		if(propertyFile.toLowerCase().contains("dev-") || propertyFile.toLowerCase().contains("dev_"))
			tierName="Dev";
		if(propertyFile.toLowerCase().contains("qa-") || propertyFile.toLowerCase().contains("qa_"))
			tierName="Qa";
		if(propertyFile.toLowerCase().contains("stage-") || propertyFile.toLowerCase().contains("stage_"))
			tierName="Stage";
		if(propertyFile.toLowerCase().contains("prod-") || propertyFile.toLowerCase().contains("prod_"))
			tierName="Prod";
		
		return tierName;
	}
}
