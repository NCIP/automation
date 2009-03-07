package gov.nih.nci.bda.certification.util;


import javax.sql.DataSource;
import org.apache.commons.configuration.DataConfiguration;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public final class ConfigurationHelper {
    private static final String TABLE_NAME = "dashboard_properties";
    private static final String DASHBOARD_KEY_COLUMN = "dashboard_key";
    private static final String DASHBOARD_VALUE_COLUMN = "dashboard_value";

    public static DataConfiguration getConfiguration() {
        DataSource ds = null;
        try 
        {            
            ds = getDataSource();            
        } catch (Exception e) 
        {
        	e.printStackTrace();
        }
        DatabaseConfiguration config = new DatabaseConfiguration(ds, TABLE_NAME, DASHBOARD_KEY_COLUMN, DASHBOARD_VALUE_COLUMN);
        config.setDelimiterParsingDisabled(true);
        return new DataConfiguration(config);
    }

    
    private static DataSource getDataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        Configuration config = HibernateUtil.getConfiguration();
        ds.setUrl(config.getProperty(Environment.URL));
        ds.setUser(config.getProperty(Environment.USER));
        ds.setPassword(config.getProperty(Environment.PASS));
        return ds;
    }

}
