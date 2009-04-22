package gov.nih.nci.bda.certification.util;

import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.Project;

/**
 * @author narram
 *
 */
public class PropertyLoader {
	private static Log certLogger = LogFactory.getLog(PropertyLoader.class);
	private static Configuration config = ConfigurationHelper
			.getConfiguration();
	private static Configuration fileConfig = ConfigurationHelper
	.getFileConfiguration();
	
	private static void loadDatabaseProperties(String databaseType,
			Project project) {
		certLogger.info("Loading database properties ");
		Iterator<Object> it = config.getKeys(databaseType);
		try {
			while (it.hasNext()) {
				String keyName = (String) it.next();
				certLogger.info("Key: " + keyName + " Value: "
						+ config.getString(keyName));
				project.setProperty(keyName, config.getString(keyName));
			}
		} catch (Exception ex) {
			// do nothing
			certLogger
					.info("Exception Occured while loading database properties "
							+ ex.getMessage());
		}
	}

	private static void loadDatabasePropertiesFromFile(String projectName,
			Project project) {
		certLogger.info("Loading database properties ");
		Iterator<Object> it = fileConfig.getKeys(projectName);
		try {
			while (it.hasNext()) {
				String keyName = (String) it.next();
				certLogger.info("Key: " + keyName + " Value: "
						+ config.getString(keyName));
				project.setProperty(keyName.substring(keyName.indexOf(projectName)+1), config.getString(keyName));
			}
		} catch (Exception ex) {
			// do nothing
			certLogger
					.info("Exception Occured while loading database properties from file"
							+ ex.getMessage());
		}
	}
	
	public static void loadGeneralProperties(Project project) {
		project.setProperty("ant.minimum.version", config
				.getString("ant.minimum.version"));
		project.setProperty("java.major.version", config
				.getString("java.major.version"));
		project.setProperty("java.minor.version", config
				.getString("java.minor.version"));
		project.setProperty("bda.version", config.getString("bda.version"));
		project.setProperty("force.reinstall", config
				.getString("force.reinstall"));
	}

	public static void loadProjectProperties(String projectName, Project project) {
		Iterator<Object> it = config.getKeys(projectName);

		project.setProperty("project.name", projectName);
		try {
			while (it.hasNext()) {
				String keyName = (String) it.next();
				certLogger.info("Key: " + keyName + " Value: "
						+ config.getString(keyName));
				project.setProperty(keyName, config.getString(keyName));
			}
		} catch (Exception ex) {
			// do nothing
			certLogger
					.info("Exception Occured while loading properties from the database "
							+ ex.getMessage());
		}

		certLogger
				.info("Exception Occured while loading properties from the database ");
		System.out.println("Database Type:: "
				+ config.getString(projectName + ".database.type"));
		if(config.getString(projectName + ".use.genericDB") != null && config.getString(projectName + ".use.genericDB").equalsIgnoreCase("true"))
			loadDatabaseProperties(config.getString(projectName + ".database.type"), project);
		else
			loadDatabasePropertiesFromFile(projectName,project);
	}

	public static void loadProperties(String projectName) {
		Configuration config = ConfigurationHelper.getConfiguration();
		Iterator<Object> it = config.getKeys(projectName);

		try {
			while (it.hasNext()) {
				String keyName = (String) it.next();
				System.out.println("KEY:: " + keyName);
			}
		} catch (Exception ex) {
			// do nothing
		}

	}

	public static void main(String[] args) {
		loadProperties("caarray");
	}

}
