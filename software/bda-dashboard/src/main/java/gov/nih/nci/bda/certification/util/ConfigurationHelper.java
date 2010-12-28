package gov.nih.nci.bda.certification.util;

import javax.sql.DataSource;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DataConfiguration;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.FileNotFoundException;

/**
 * @author narram
 * 
 */
public final class ConfigurationHelper {
	private static Log certLogger = LogFactory.getLog(ConfigurationHelper.class);
	private static final String DASHBOARD_KEY_COLUMN = "dashboard_key";
	private static final String DASHBOARD_VALUE_COLUMN = "dashboard_value";
	private static final String TABLE_NAME = "dashboard_properties";

	public static DataConfiguration getConfiguration() {
		DataSource ds = null;
		try {
			ds = getDataSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
		DatabaseConfiguration config = new DatabaseConfiguration(ds,
				TABLE_NAME, DASHBOARD_KEY_COLUMN, DASHBOARD_VALUE_COLUMN);
		config.setDelimiterParsingDisabled(true);
		certLogger.info(" Set the DataConfiguration " );
		return new DataConfiguration(config);
	}

	private static DataSource getDataSource() throws FileNotFoundException {
		certLogger.info(" Get the DataSource " );
		MysqlDataSource ds = new MysqlDataSource();
		Configuration hibernateConfig = HibernateUtil.getConfiguration();
		ds.setUrl(hibernateConfig.getProperty(Environment.URL));
		ds.setUser(hibernateConfig.getProperty(Environment.USER));
		ds.setPassword(hibernateConfig.getProperty(Environment.PASS));
		certLogger.info(" URL:" + hibernateConfig.getProperty(Environment.URL) + " User:" + hibernateConfig.getProperty(Environment.USER) + " Pass:" +hibernateConfig.getProperty(Environment.PASS));
		return ds;
	}

	public static org.apache.commons.configuration.Configuration getFileConfiguration() {
		PropertiesConfiguration config = null;
		certLogger.info(" Set the FileConfiguration " );
		try {
			config = new PropertiesConfiguration("build/lookup.properties");
		} catch (ConfigurationException e) {
			certLogger.error(" Could not load the  properties from the file" );	
			e.printStackTrace();
		}		
		return config;
	}
}
