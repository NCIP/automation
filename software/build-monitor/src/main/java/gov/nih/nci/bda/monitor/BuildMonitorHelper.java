package gov.nih.nci.bda.monitor;

import gov.nih.nci.bda.monitor.business.BuildMonitorBean;
import gov.nih.nci.bda.monitor.domain.BuildHistory;
import gov.nih.nci.bda.monitor.domain.BuildStatus;
import gov.nih.nci.bda.monitor.domain.ProjectBuildStatus;
import gov.nih.nci.bda.monitor.util.HibernateUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;

public class BuildMonitorHelper {

	BuildMonitorBean bmb;
	
	public BuildMonitorHelper()
	{
		super();
	}

	public BuildMonitorHelper(BuildMonitorBean bmb)
	{
		this.bmb = bmb;
	}
	
	public void updateBuildStatus()
	{		
		BuildStatus bs = null;
		String buildTier = getTierName(bmb.getPropertiesFileName());
		if (!bmb.isBuildSuccessful()) 
		{									
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    session.beginTransaction(); 		    
		    
		    Query query = session.createQuery( "from BuildStatus as status where buildTier= :buildTier");
		    query.setString("buildTier", buildTier);
			Iterator it=query.iterate();			
			if(it != null && it.hasNext())
		    {		    	
		    	bs = (BuildStatus) it.next();		    	
		    	bs.setBuildStatus(BuildMonitorConstants.BUILD_FAILED);
		    	bs.setWikiBuildStatus(BuildMonitorConstants.WIKI_FAILED);
		    	bs.setLastBuildTime(new Timestamp(System.currentTimeMillis()));
		    	session.update(bs);
		    }
		    else
		    {
		    	bs = new BuildStatus();
		    	bs.setBuildTier(buildTier);
		    	bs.setBuildStatus(BuildMonitorConstants.BUILD_FAILED);
		    	bs.setWikiBuildStatus(BuildMonitorConstants.WIKI_FAILED);
		    	bs.setLastBuildTime(new Timestamp(System.currentTimeMillis()));
		    	session.save(bs);		    	
		    }

	    	BuildHistory bh = new BuildHistory();			  
	    	bh.setBuildTier(buildTier);
	    	bh.setBuildStatus(BuildMonitorConstants.BUILD_FAILED);
	    	bh.setWikiBuildStatus(BuildMonitorConstants.WIKI_FAILED);
	    	bh.setLastBuildTime(new Timestamp(System.currentTimeMillis()));
	    	session.save(bh);	
		    	
		    session.getTransaction().commit();		    
		}
		else
		{						
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    session.beginTransaction(); 		    
		    Query query = session.createQuery( "from BuildStatus where buildTier= '"+buildTier+"'");
			bs= (BuildStatus) query.uniqueResult();

			if(bs != null )
			{				
		    	bs.setBuildStatus(BuildMonitorConstants.BUILD_SUCCESSFUL);
		    	bs.setWikiBuildStatus(BuildMonitorConstants.WIKI_SUCCESSFUL);
		    	bs.setLastBuildTime(new Timestamp(System.currentTimeMillis()));
		    	session.update(bs);
		    }
		    else
		    {
		    	bs = new BuildStatus();
		    	bs.setBuildTier(buildTier);
		    	bs.setBuildStatus(BuildMonitorConstants.BUILD_SUCCESSFUL);
		    	bs.setWikiBuildStatus(BuildMonitorConstants.WIKI_SUCCESSFUL);
		    	bs.setLastBuildTime(new Timestamp(System.currentTimeMillis()));
		    	session.save(bs);		    	
		    }

	    	BuildHistory bh = new BuildHistory();			  
	    	bh.setBuildTier(buildTier);
	    	bh.setBuildStatus(BuildMonitorConstants.BUILD_SUCCESSFUL);
	    	bh.setWikiBuildStatus(BuildMonitorConstants.WIKI_SUCCESSFUL);
	    	bh.setLastBuildTime(new Timestamp(System.currentTimeMillis()));
	    	session.save(bh);
		    	
		    session.getTransaction().commit();
		}

	}

	
	
	public void updateProjectBuildStatus()
	{				
		ProjectBuildStatus pbs = null;
		String buildTier = getTierName(bmb.getPropertiesFileName());
		String projectName = bmb.getProjectName();
		String methodName = "set"+buildTier;
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction(); 		    
	    String wikiFailedString = "'["+BuildMonitorConstants.WIKI_FAILED+"|"+BuildMonitorConstants.ANTHILL_ADDRESS+"]'";
	    String wikiSuccessString = "'["+BuildMonitorConstants.WIKI_SUCCESSFUL+"|"+BuildMonitorConstants.ANTHILL_ADDRESS+"]'";
	    
		if (!bmb.isBuildSuccessful()) 
		{	
			// build failed
		    Query query = session.createQuery( " from ProjectBuildStatus where product= :projectName ");
		    query.setString("projectName", projectName);
			pbs= (ProjectBuildStatus) query.uniqueResult();
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
		    	
		    
		}
		else
		{						
			// build successful	    
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
		    
		}
	    session.getTransaction().commit();
	}
	
	
	private String getTierName(String  propertyFile) {
		String tierName = "local";
				
		if(propertyFile.contains("dev-"))
			tierName="Dev";
		if(propertyFile.contains("qa-"))
			tierName="Qa";
		if(propertyFile.contains("stage-"))
			tierName="Stage";
		if(propertyFile.contains("prod-"))
			tierName="Prod";
		
		return tierName;
	}
}
