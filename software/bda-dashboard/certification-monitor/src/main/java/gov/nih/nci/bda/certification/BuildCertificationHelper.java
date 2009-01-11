package gov.nih.nci.bda.certification;

import java.lang.reflect.Method;

import gov.nih.nci.bda.certification.business.BuildCertificationBean;
import gov.nih.nci.bda.certification.domain.ProjectCertificationStatus;
import gov.nih.nci.bda.certification.util.HibernateUtil;

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
		System.out.println("projectName:::::::" +projectName);
		String mapName = bmb.getMapName();		
		System.out.println("mapName:::::::" +mapName);
		String methodName = getSetMethodName(mapName);
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction(); 		    
	    	    
	    
		if (!bmb.isBuildSuccessful()) 
		{	
			System.out.println("SINGLE COMMAND FAILED");
			// build failed

		    Query query = session.createQuery( " select "+mapName+" from ProjectCertificationStatus where product= :projectName");
		    query.setString("projectName", projectName);
			pbs= (ProjectCertificationStatus) query.uniqueResult();
			System.out.println("SINGLE COMMAND FAILED :::11");
			if(pbs != null )
			{	
		    	try {		    		
					pbs.getClass().getMethod(methodName, new Class[] {String.class}).invoke(pbs,BuildCertificationConstants.WIKI_FAILED); 
				}
		    	catch (Exception e) {				
					e.printStackTrace();
				}
		    	session.update(pbs);
		    }
		    else
		    {
		    	pbs = new ProjectCertificationStatus();
		    	Method[] methods = pbs.getClass().getMethods();
		    	for (int i = 0; i < methods.length; i++)
		    	{
		    		if(methods[i].getName().equals(methodName))
    				{
		    			try 
		    			{	System.out.println("BuildCertificationConstants.WIKI_FAILED::::" + BuildCertificationConstants.WIKI_FAILED);	    				
		    				methods[i].invoke(pbs,new String(BuildCertificationConstants.WIKI_FAILED));
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
			    				methods[i].invoke(pbs,new String(BuildCertificationConstants.WIKI_NOTBUILD));
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
/*
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

		    Query query = session.createQuery( " select "+mapName+" from ProjectCertificationStatus where product= :projectName ");
		    query.setString("projectName", projectName);
			pbs= (ProjectCertificationStatus) query.uniqueResult();

			if(pbs != null )
			{						    		
		    	try {
					pbs.getClass().getMethod(methodName, new Class[] {String.class}).invoke(pbs,BuildCertificationConstants.WIKI_SUCCESSFUL); 
				}
		    	catch (Exception e) {				
					e.printStackTrace();
				}
		    	session.update(pbs);
		    }
		    else
		    {
		    	pbs = new ProjectCertificationStatus();
		    	Method[] methods = pbs.getClass().getMethods();
		    	for (int i = 0; i < methods.length; i++)
		    	{
		    		if(methods[i].getName().equals(methodName))
    				{
		    			try 
		    			{
		    				methods[i].invoke(pbs,new String(BuildCertificationConstants.WIKI_SUCCESSFUL));
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
			    				methods[i].invoke(pbs,new String(BuildCertificationConstants.WIKI_NOTBUILD));
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
/*
	    	BuildHistory bh = new BuildHistory();		  
	    	bh.setProduct(projectName);
	    	bh.setBuildTier(buildTier);
	    	bh.setBuildStatus(BuildMonitorConstants.BUILD_SUCCESSFUL);
	    	bh.setWikiBuildStatus(BuildMonitorConstants.WIKI_SUCCESSFUL);
	    	bh.setLastBuildTime(new Timestamp(System.currentTimeMillis()));
	    	session.save(bh);
		     */
		}
	    session.getTransaction().commit();
	   
	}
	
	
	private String getSetMethodName(String mapName) {
		
		return "set"+"SingleCommandBuild";
	}

}
