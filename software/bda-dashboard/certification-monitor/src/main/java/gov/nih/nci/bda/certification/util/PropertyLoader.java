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

	private static void loadDatabaseProperties(String databaseType,
			Project project) {
		project.setProperty(databaseType + ".database.system.user", config
				.getString(databaseType + ".database.system.user"));
		project.setProperty(databaseType + ".database.system.password", config
				.getString(databaseType + ".database.system.password"));
		project.setProperty(databaseType + ".database.server", config
				.getString(databaseType + ".database.server"));
		project.setProperty(databaseType + ".database.port", config
				.getString(databaseType + ".database.port"));
		project.setProperty(databaseType + ".database.name", config
				.getString(databaseType + ".database.name"));

		project.setProperty(databaseType + ".database.user", config
				.getString(databaseType + ".database.user"));
		project.setProperty(databaseType + ".database.password", config
				.getString(databaseType + ".database.password"));
		project.setProperty(databaseType + ".database.url", config
				.getString(databaseType + ".database.url"));
		project.setProperty(databaseType + ".database.system.url", config
				.getString(databaseType + ".database.system.url"));
		project.setProperty(databaseType + ".minimum.version", config
				.getString(databaseType + ".minimum.version"));
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
		loadDatabaseProperties(
				config.getString(projectName + ".database.type"), project);
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
