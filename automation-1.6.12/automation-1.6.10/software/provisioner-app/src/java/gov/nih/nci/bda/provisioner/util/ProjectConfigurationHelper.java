package gov.nih.nci.bda.provisioner.util;

import java.util.Iterator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DataConfiguration;

/**
 * @author narram
 *
 */
public class ProjectConfigurationHelper {

	private  DataConfiguration config = null; 

	public ProjectConfigurationHelper()
	{
		config =  gov.nih.nci.bda.provisioner.util.ConfigurationHelper.getConfiguration();
	}
	
	public void loadProjectConfiguration(String projectName,
			PropertyHelper project) {
		Iterator<Object> it = config.getKeys(projectName);
		try {
			while (it.hasNext()) {
				String keyName = (String) it.next();
				project.setProperty(keyName.substring(projectName.length()+1), config.getString(keyName));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String getProperty(String propertyName) {
		return (String) config.getProperty(propertyName);
	}
	
	public static void main(String args[]) throws ConfigurationException
	   {
		   PropertyHelper ph = new PropertyHelper("resources/local.properties");
		   ProjectConfigurationHelper pch = new ProjectConfigurationHelper();
		   pch.loadProjectConfiguration("caarray",ph);
		   System.out.println("SAVE::");
		   ph.saveConfiguration();
		   System.out.println("END");

		   
	   }	
}
