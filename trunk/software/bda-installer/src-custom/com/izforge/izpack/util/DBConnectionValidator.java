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
        StringBuffer dbUrl = new StringBuffer();
        StringBuffer dbSystemUrl = new StringBuffer();
        String dbDriver = "com.mysql.jdbc.Driver";
        String dbUser = "";
        String dbSystemUser = "";
        String dbPassword = "";
        InstallData idata = (InstallData) AutomatedInstallData.getInstance();        
        String sysUser = "";
        try
        {
            if (client.hasParams())
            {
                Map<String, String> paramMap = client.getValidatorParams();
                sysUser = paramMap.get("systemUser");             
            }
            if (sysUser.equals("true"))
            {              
	    		dbPassword = client.getFieldContents(0);
	    		dbSystemUrl =dbSystemUrl.append("jdbc:mysql://").append(idata.getVariable("database.server")).append(":").append(idata.getVariable("database.port"));
	    		System.out.println("dbSystemUrl::" + dbSystemUrl);
	        	dbSystemUser = idata.getVariable("database.system.user");    	        	
	            Class.forName(dbDriver).newInstance();
	          	DriverManager.getConnection(dbSystemUrl.toString(),dbSystemUser,dbPassword);    	        	
	        }
            else
            {            	
	        	dbPassword = client.getFieldContents(0);
	            dbUrl =dbUrl.append("jdbc:mysql://").append(idata.getVariable("database.server")).append(":").append(idata.getVariable("database.port")).append("/").append(idata.getVariable("database.name"));
	            dbUser = idata.getVariable("database.user");
	            Class.forName(dbDriver).newInstance();
	        	DriverManager.getConnection(dbUrl.toString(),dbUser,dbPassword);
            }  
        	return true;
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            return false;
        }	
	}
}
