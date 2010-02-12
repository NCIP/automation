package gov.nih.nci.bda.provisioner.util;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertyHelper {
	   private PropertiesConfiguration config = null;
	   
	   public PropertyHelper(String fileName) {
		   config =  ConfigurationHelper.getPropertyConfiguration(new File(fileName).getAbsoluteFile());
	   }
	   
	   public String getProperty(String propertyName)
	   {
		   return config.getString(propertyName);
	   }
	   
	   public void setProperty(String propertyName,String propertyValue)
	   {
		   config.setProperty(propertyName, propertyValue);
	   }
	
	   public void addProperty(String propertyName,String propertyValue)
	   {
		   Iterator keys = config.getKeys();
		   boolean keyExists = false;
		   while (keys.hasNext())
		   {
			   if(propertyName.equalsIgnoreCase((String) keys.next()))
				   keyExists= true;
		   }
		   if(keyExists)
			   config.setProperty(propertyName, propertyValue);
		   else
			   config.addProperty(propertyName, propertyValue);
			   
	   }	   

	   public void removeProperty(String propertyName)
	   {
		   Iterator keys = config.getKeys();
		   boolean keyExists = false;
		   while (keys.hasNext())
		   {
			   if(propertyName.equalsIgnoreCase((String) keys.next()))
				   keyExists= true;
		   }
		   if(keyExists)
			   config.clearProperty(propertyName);			   
	   }	
	   
	   public void saveConfiguration() throws ConfigurationException
	   {
		   config.save();
	   }	   
	   public static void main(String args[]) throws ConfigurationException
	   {
		   PropertyHelper ph = new PropertyHelper("resources/local.properties");
		   System.out.println(ph.getProperty("force.reinstall"));
		   ph.setProperty("force.reinstall","false");
		   ph.addProperty("myproperty1","value1");
		   ph.saveConfiguration();
		   System.out.println(ph.getProperty("force.reinstall"));
		   System.out.println(ph.getProperty("myproperty1"));
		   
	   }

}
