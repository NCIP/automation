package gov.nih.nci.bda.provisioner.util;

import java.io.File;

import org.apache.commons.configuration.Configuration;

public class PropertyHelper {
	   private static PropertyHelper propertyHelper = null;
	   private static Configuration config = null;
	   
	   protected PropertyHelper() {
		   config = ConfigurationHelper.getConfiguration(new File("resources/configuration.properties").getAbsoluteFile());
	   }
	 
	   static public PropertyHelper getPropertyHelper() {
	      if(null == propertyHelper) {
	    	  propertyHelper = new PropertyHelper();
	      }
	      return propertyHelper;
	   }
	   
	   public String getProperty(String configurationName)
	   {
		   return config.getString(configurationName);
	   }
	   
	   public void setProperty(String configurationName)
	   {
		   config.setProperty(configurationName, null);
	   }

}
