package gov.nih.nci.bda.certification;

import java.lang.reflect.InvocationTargetException;
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
		
	public void updateProjectBuildStatus() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{				
		ProjectCertificationStatus pbs = null;
		String projectName = bmb.getProjectName();		
		String mapName = bmb.getMapName();				
		String methodName = getSetMethodName(mapName);
		String projectUrl = "'[" + projectName +"|"+ bmb.getProjectRepoUrl()+"]'";
		String searchProject = "%"+projectName+"|%";
		
		
		System.out.println("bmb.isValue():::::::" +bmb.isValue());
		System.out.println("bmb.getPropertyValue():::::::" +bmb.getPropertyValue());
		System.out.println("bmb.getProjectRepoUrl():::::::" +bmb.getProjectRepoUrl());
		System.out.println("methodName:::::::" +methodName);
		System.out.println("mapName:::::::" +mapName);
		System.out.println("projectName:::::::" +projectName);
		
	    //Session session = HibernateUtil.getSessionFactory().getCurrentSession();		
		Session session = HibernateUtil.getSession();
	    session.beginTransaction(); 		    
	    	    

		if (!bmb.isBuildSuccessful()) 
		{	
			System.out.println("CERTIFICATION FEATURE FAILED");
			// build failed

		    Query query = session.createQuery( BuildCertificationConstants.CERTIFICATION_QUERY);
		    query.setString(0, searchProject);
			pbs= (ProjectCertificationStatus) query.uniqueResult();
			if(pbs != null )
			{	
				invokeSetMethodValue(pbs,methodName,BuildCertificationConstants.WIKI_FAILED);

		    	//update the project URL on update
		    	pbs.setProduct(projectUrl);
		    	session.update(pbs);
		    }
		    else
		    {
		    	pbs = new ProjectCertificationStatus();
		    	invokeSetAllMethods(pbs,methodName,BuildCertificationConstants.WIKI_FAILED);
		    	pbs.setProduct(projectUrl);
		    	session.save(pbs);		
		    }		    
		}
		else
		{	
			System.out.println("CERTIFICATION FEATURE PASSED");
			// build successful	 
			
		    Query query = session.createQuery(BuildCertificationConstants.CERTIFICATION_QUERY);
		    query.setString(0, searchProject);
			pbs= (ProjectCertificationStatus) query.uniqueResult();

			if(pbs != null )
			{						    		
		    	invokeSetMethodValue(pbs,methodName,BuildCertificationConstants.WIKI_SUCCESSFUL);
		    	//update the project URL on update
		    	pbs.setProduct(projectUrl);
		    	session.update(pbs);
		    }
		    else
		    {
		    	pbs = new ProjectCertificationStatus();
		    	invokeSetAllMethods(pbs,methodName,BuildCertificationConstants.WIKI_SUCCESSFUL);		    	
		    	pbs.setProduct(projectUrl);
		    	session.save(pbs);		    	
		    }
		}
	    session.getTransaction().commit();  
	}
	
	
	private String getSetMethodName(String mapName) {
		
		return "set"+ mapName.substring(0,1).toUpperCase() + mapName.substring(1); 
	}
	
	private void invokeSetMethodValue(ProjectCertificationStatus pbs,String methodName,String certificationStatus) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
    		if(!bmb.isValue())
    			pbs.getClass().getMethod(methodName, new Class[] {String.class}).invoke(pbs,certificationStatus);
    		else
    			pbs.getClass().getMethod(methodName, new Class[] {String.class}).invoke(pbs,bmb.getPropertyValue());
	}

	private void invokeSetAllMethods(ProjectCertificationStatus pbs,String methodName,String certificationStatus) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{

    	Method[] methods = pbs.getClass().getMethods();
    	for (int i = 0; i < methods.length; i++)
    	{

    		if(methods[i].getName().equals(methodName))
			{
		    		if(!bmb.isValue())
		    			methods[i].invoke(pbs,new String(certificationStatus));
		    		else
		    			methods[i].invoke(pbs,new String(bmb.getPropertyValue()));
			}
    		else if(methods[i].getName().startsWith("set"))
    		{
    			if(!(methods[i].getName().equals("setProduct") || methods[i].getName().equals("setId")))
				{
	    				methods[i].invoke(pbs,new String(BuildCertificationConstants.WIKI_NOTBUILD));
				}
    		}
    	}
	}	
	
}
