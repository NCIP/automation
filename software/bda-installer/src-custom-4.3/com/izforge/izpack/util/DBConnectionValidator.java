package com.izforge.izpack.util;

import java.sql.DriverManager;
import java.util.Map;

import com.izforge.izpack.installer.AutomatedInstallData;
import com.izforge.izpack.installer.InstallData;
import com.izforge.izpack.panels.PasswordGroup;
import com.izforge.izpack.panels.ProcessingClient;
import com.izforge.izpack.panels.Validator;


public class DBConnectionValidator implements Validator {


	public boolean validate(ProcessingClient client)
    {
        InstallData idata = (InstallData) AutomatedInstallData.getInstance();
        String sysUser = null;
        String databaseType =null;
        DatabaseConnection connection = null;
        try
        {
        	databaseType = idata.getVariable("database.type");
        	if(databaseType == null )
        	{
        		databaseType = "mysql";
        	}
        	System.out.println("databaseType::"+databaseType);
            if (client.hasParams())
            {
                Map<String, String> paramMap = client.getValidatorParams();
                sysUser = paramMap.get("systemUser");
            }
            System.out.println("sysUser::"+sysUser);
            if(databaseType != null && databaseType.equalsIgnoreCase("mysql"))
            {
                if (sysUser != null && sysUser.equals("true"))
                {
                	connection = new MySQLConnection(idata.getVariable("database.server"),idata.getVariable("database.port"),idata.getVariable("database.system.user"),client.getFieldContents(0));
                }else
                {
                	connection = new MySQLConnection(idata.getVariable("database.server"),idata.getVariable("database.port"),idata.getVariable("database.name"),idata.getVariable("database.user"),client.getFieldContents(0));
                }
            }
            if(databaseType != null && databaseType.equalsIgnoreCase("oracle"))
            {
            	String dbPassword = client.getFieldContents(0);
            	connection = new OracleConnection(idata.getVariable("database.server"),idata.getVariable("database.port"),idata.getVariable("database.name"),idata.getVariable("database.user"),dbPassword);
            }
            if(databaseType != null && databaseType.equalsIgnoreCase("postgresql"))
            {
            	String dbPassword = client.getFieldContents(0);
            	connection = new PostgresConnection(idata.getVariable("database.server"),idata.getVariable("database.port"),idata.getVariable("database.name"),idata.getVariable("database.user"),dbPassword);
            }
            return connection.isValidConnection();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            return false;
        }
	}
}
