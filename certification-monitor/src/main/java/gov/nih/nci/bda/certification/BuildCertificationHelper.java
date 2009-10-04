package gov.nih.nci.bda.certification;

import gov.nih.nci.bda.certification.business.BuildCertificationBean;
import gov.nih.nci.bda.certification.domain.ProjectCertificationStatus;
import gov.nih.nci.bda.certification.util.ConfigurationHelper;
import gov.nih.nci.bda.certification.util.HibernateUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author narram
 *
 */
public class BuildCertificationHelper {
	BuildCertificationBean bmb;
	private Log certLogger = LogFactory.getLog(BuildCertificationHelper.class);

	public BuildCertificationHelper()
	{
		super();
	}

	public BuildCertificationHelper(BuildCertificationBean bmb)
	{
		this.bmb = bmb;
	}

	private String formatMessage(String message) {
		StringBuffer sb = new StringBuffer();

		if (message.length() >=BuildCertificationConstants.ERROR_MESSAGE_LENGTH)
			message=message.substring(0, BuildCertificationConstants.ERROR_MESSAGE_LENGTH);

		message=message.replace("'", "");
		message=message.replace("\"", "");
		String[] result=message.split("\\n");
	    for (int x=0; x<result.length; x++)
	         sb.append(result[x]);
	    certLogger.info("Format the Error Message :: " + sb.toString());
	    return sb.toString();
	}


	private String getLink(String jobName) {
		StringBuffer sb = new StringBuffer();
		sb.append(ConfigurationHelper.getConfiguration().getString(BuildCertificationConstants.CI_SERVER_NAME));
		sb.append("/" + BuildCertificationConstants.CI_SERVER_JOB_PREFIX +"-"+jobName);
		sb.append("/" + BuildCertificationConstants.CI_SERVER_LASTBUILD_CONSOLE);
		certLogger.info("Link::"+sb.toString());
		return sb.toString();
	}

	private String getSetMethodName(String mapName) {

		return "set"+ mapName.substring(0,1).toUpperCase() + mapName.substring(1);
	}

	private String getWikiLinkTip(String displayName,String jobName,String message) {
		String wikiLinkTipStr = null;
		if (message != null)
		{
			wikiLinkTipStr = "'[" + displayName +"|"+ getLink(jobName)+"|" + formatMessage(message) + "]'";
		}
		else
		{
			wikiLinkTipStr = displayName;
		}
		certLogger.info("LinkTipString::"+wikiLinkTipStr);
		return wikiLinkTipStr;
	}

	private String getWikiProjectCertificationStatus() {
		if(bmb.isCertificationStatus())
		{
			return BuildCertificationConstants.WIKI_SUCCESSFUL;
		}
		else
		{
			return BuildCertificationConstants.WIKI_FAILED;
		}


	}

	private void invokeSetAllMethods(ProjectCertificationStatus pbs,String methodName,String certificationStatus) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{

    	Method[] methods = pbs.getClass().getMethods();
    	for (int i = 0; i < methods.length; i++)
    	{
    		certLogger.debug("Method Name :: " + methods[i].getName());
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

	private void invokeSetMethodValue(ProjectCertificationStatus pbs,String methodName,String certificationStatus) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
    		if(!bmb.isValue())
    			pbs.getClass().getMethod(methodName, new Class[] {String.class}).invoke(pbs,certificationStatus);
    		else
    			pbs.getClass().getMethod(methodName, new Class[] {String.class}).invoke(pbs,bmb.getPropertyValue());
	}

	private void setProductValue(ProjectCertificationStatus pbs,
			String projectUrl) {
		if (bmb.getProjectRepoUrl() != null)
			pbs.setProduct(projectUrl);
	}

	public void updateProjectBuildStatus() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		ProjectCertificationStatus pbs = null;
		String projectName = bmb.getProjectName();
		String mapName = bmb.getMapName();
		String methodName = getSetMethodName(mapName);
		String searchProject = "%"+projectName+"|%";
		String projectUrl = "'[" + projectName +"|"+ bmb.getProjectRepoUrl()+"]'";
/*
		if(isReachble(bmb.getProjectRepoUrl()))
		{
			searchProject = "%"+projectName+"|%";
			projectUrl = "'[" + projectName +"|"+ bmb.getProjectRepoUrl()+"]'";
		}
		else
		{
			searchProject = "%"+projectName+"{color}|%";
			projectUrl = "'[{color:red}" + projectName +"{color}|"+ bmb.getProjectRepoUrl()+"]'";
		}
*/

		certLogger.info("Update the Build Status for the feature :::::::" + mapName);
		certLogger.info("check isValue:::::::" +bmb.isValue());
		certLogger.info("check isOptional:::::::" +bmb.isOptional());
		certLogger.info("get property value if .is.value is true:::::::" +bmb.getPropertyValue());
		certLogger.info("get the project URL:::::::" +projectUrl);
		certLogger.info("FailureMessage:::::::" +bmb.getFailureMessage());

		Session session = HibernateUtil.getSession();
	    session.beginTransaction();

	    certLogger.info("Retrive the certification record from the database");
	    Query query = session.createQuery( BuildCertificationConstants.CERTIFICATION_QUERY);
	    query.setString(0, searchProject);
		pbs= (ProjectCertificationStatus) query.uniqueResult();
		certLogger.info("Clear the session");
		session.clear();

		if (!bmb.isBuildSuccessful())
		{
			certLogger.info("Certification Feature Failed");
			// build failed
			if(pbs != null )
			{
				certLogger.info("Project exists ");
				if(bmb.isOptional())
					invokeSetMethodValue(pbs,methodName,getWikiLinkTip(BuildCertificationConstants.WIKI_OPTIONAL,projectName,bmb.getFailureMessage()));
				else
					invokeSetMethodValue(pbs,methodName,getWikiLinkTip(BuildCertificationConstants.WIKI_FAILED,projectName,bmb.getFailureMessage()));
		    	//update the project URL on update
				setProductValue(pbs,projectUrl);
		    	session.update(pbs);
		    }
		    else
		    {
		    	certLogger.info("New Project ");
		    	pbs = new ProjectCertificationStatus();
		    	invokeSetAllMethods(pbs,methodName,getWikiLinkTip(BuildCertificationConstants.WIKI_FAILED,projectName,bmb.getFailureMessage()));
		    	setProductValue(pbs,projectUrl);
		    	session.save(pbs);
		    }
		}
		else
		{
			certLogger.info("Certification Feature Successful");
			// build successful
			if(pbs != null )
			{
				certLogger.info("Project exists ");
		    	invokeSetMethodValue(pbs,methodName,BuildCertificationConstants.WIKI_SUCCESSFUL);
		    	//update the project URL on update
		    	setProductValue(pbs,projectUrl);
		    	session.update(pbs);

		    }
		    else
		    {
		    	certLogger.info("New Project ");
		    	pbs = new ProjectCertificationStatus();
		    	invokeSetAllMethods(pbs,methodName,BuildCertificationConstants.WIKI_SUCCESSFUL);
		    	setProductValue(pbs,projectUrl);
		    	session.save(pbs);
		    }
		}
		certLogger.info("Update the Project Certification Status");
		invokeSetMethodValue(pbs,"setCertificationStatus",getWikiProjectCertificationStatus());
		session.update(pbs);
		certLogger.info("Commit to Database");
	    session.getTransaction().commit();
	}

	private boolean isReachble(String projectRepoUrl) {
      try
        {
	  	  URL url = new URL(projectRepoUrl);
	      System.out.println("Testing to see if URL connects::"+ projectRepoUrl);
	      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	      System.out.println("Created HttpURLConnection object");
	      conn.connect();
	      System.out.println("connecting..");
	      int code  =conn.getResponseCode();
	      if (code >= 400)
	      {
	    	  return false;
	      }
	      System.out.println("disconnecting..");
	      conn.disconnect();
	      System.out.println("disconnected");
	      return true;
	   }
      catch (Exception ex)
      {
      	ex.printStackTrace();
      	return false;
      }
	}

}
