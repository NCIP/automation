package gov.nih.nci.bda.provisioner.util;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class ConfigurationHelper {
	private static Log logger = LogFactory.getLog(ConfigurationHelper.class);


	public static org.apache.commons.configuration.Configuration getConfiguration(File propertyFile) {
		PropertiesConfiguration config = null;
		logger.info(" Set the FileConfiguration " );
		try {
			config = new PropertiesConfiguration(propertyFile);
		} catch (ConfigurationException e) {
			logger.error(" Could not load the  properties from the file" );
			e.printStackTrace();
		}
		return config;
	}
}
