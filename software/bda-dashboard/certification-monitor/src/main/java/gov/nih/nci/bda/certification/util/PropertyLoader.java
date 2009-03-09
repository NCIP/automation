package gov.nih.nci.bda.certification.util;

import java.util.Iterator;

import gov.nih.nci.bda.certification.BuildCertificationConstants;
import gov.nih.nci.bda.certification.domain.ProjectCertificationStatus;
import gov.nih.nci.bda.certification.domain.ProjectProperties;

import org.apache.commons.configuration.Configuration;
import org.hibernate.Query;
import org.hibernate.Session;

import org.apache.tools.ant.Project;

public class PropertyLoader {

	private static ProjectProperties pp = null;
	
	public static void loadProperties(String projectName,Project project) {
		Configuration config = ConfigurationHelper.getConfiguration();
        Iterator it = config.getKeys(projectName);
       
        project.setProperty("project.name", projectName);
        while(it.hasNext())
        {
        	String keyName = (String) it.next();
        	String tempKeyName = keyName.replaceFirst(projectName,"");
        	System.out.println("KEY:: " + tempKeyName.substring(1,tempKeyName.length()) + "VALUE:: " + config.getString(keyName));
        	project.setProperty(tempKeyName.substring(1,tempKeyName.length()), config.getString(keyName));        	        	
        }
        
	}

	public static void loadProperties(String projectName) {
		Configuration config = ConfigurationHelper.getConfiguration();
        Iterator it = config.getKeys(projectName);
        while(it.hasNext())
        {
        	String keyName = (String) it.next();
        	String tempKeyName = keyName.replaceFirst(projectName,"");
        	System.out.println("KEY:: " + tempKeyName.substring(1,tempKeyName.length()) + "VALUE:: " + config.getString(keyName));
      //  	project.setProperty(tempKeyName.substring(1,tempKeyName.length(), config.getString(keyName));        	
        	
        }
        
	}

	
	public static void main(String[] args)
	{    
		loadProperties("caarray");
	}

}
