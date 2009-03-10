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
	private static Configuration config = ConfigurationHelper.getConfiguration();
	
	public static void loadProjectProperties(String projectName,Project project) {
		
        Iterator it = config.getKeys(projectName);
       
        project.setProperty("project.name", projectName);
        while(it.hasNext())
        {
        	String keyName = (String) it.next();
//        	String tempKeyName = keyName.replaceFirst(projectName,"");
        	System.out.println("KEY:: " + keyName + " VALUE:: " + config.getString(keyName));
//        	project.setProperty(tempKeyName.substring(1,tempKeyName.length()), config.getString(keyName));
        	project.setProperty(keyName, config.getString(keyName));
        }
        System.out.println("PROJECTNAME:::"+projectName );
        System.out.println("KEY:: " + config.getString(projectName + ".database.type") );
        loadDatabaseProperties(config.getString(projectName + ".database.type"),project);
	}

	private static void loadDatabaseProperties(String databaseType,Project project) {
        project.setProperty(databaseType+".database.system.user",  config.getString(databaseType+".database.system.user"));
        project.setProperty(databaseType+".database.system.password",  config.getString(databaseType+".database.system.password"));
        project.setProperty(databaseType+".database.server",  config.getString(databaseType+".database.server"));
        project.setProperty(databaseType+".database.port",  config.getString(databaseType+".database.port"));
        project.setProperty(databaseType+".database.name",  config.getString(databaseType+".database.name"));
        
        project.setProperty(databaseType+".database.user",  config.getString(databaseType+".database.user"));
        project.setProperty(databaseType+".database.password",  config.getString(databaseType+".database.password"));
        project.setProperty(databaseType+".database.url",  config.getString(databaseType+".database.url"));
        project.setProperty(databaseType+".database.system.url",  config.getString(databaseType+".database.system.url"));
        project.setProperty(databaseType+".minimum.version",  config.getString(databaseType+".minimum.version"));
	}

	public static void loadGeneralProperties(Project project) {       
        project.setProperty("ant.minimum.version",  config.getString("ant.minimum.version"));
        project.setProperty("java.major.version",  config.getString("java.major.version"));
        project.setProperty("java.minor.version",  config.getString("java.minor.version"));
        project.setProperty("bda.version",  config.getString("bda.version"));
        project.setProperty("force.reinstall",  config.getString("force.reinstall"));        
	}
	
	public static void loadProperties(String projectName) {
        Iterator it = config.getKeys(projectName);
        while(it.hasNext())
        {
        	String keyName = (String) it.next();
        	String tempKeyName = keyName.replaceFirst(projectName,"");
        	System.out.println("KEY:: " + tempKeyName.substring(1,tempKeyName.length()) + "VALUE:: " + config.getString(keyName));        	
        }
        
	}

	
	public static void main(String[] args)
	{    
		loadProperties("caarray");
	}

}
