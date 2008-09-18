package com.izforge.izpack.util;

import java.sql.DriverManager;
import java.util.Map;

import com.izforge.izpack.installer.AutomatedInstallData;
import com.izforge.izpack.installer.InstallData;
import com.izforge.izpack.panels.ProcessingClient;
import com.izforge.izpack.panels.Validator;


public class DBConnectionValidator implements Validator {


    
	public boolean validate(ProcessingClient client)
    {
        StringBuffer dbUrl = new StringBuffer();
        String dbDriver = "com.mysql.jdbc.Driver";
        String dbUser = "";
        String dbPassword = "";
        InstallData idata = (InstallData) AutomatedInstallData.getInstance();
     
        try
        {
            dbUrl =dbUrl.append("jdbc:mysql://").append(idata.getVariable("database.server")).append(":").append(idata.getVariable("database.port")).append("/").append(idata.getVariable("database.name"));
            dbUser = idata.getVariable("database.user");
            dbPassword = client.getFieldContents(0);
            //dbDriver = idata.getVariable("db.driver");            
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            return false;
        }	
        System.out.println("dbUrl.toString()" + dbUrl.toString());
		try 
		{
			Class.forName(dbDriver).newInstance();
			DriverManager.getConnection(dbUrl.toString(),dbUser,dbPassword);
			
			return true;
		}        
		catch (Exception e)
        {
			e.printStackTrace();
            return false;
        }

	}
}
