package gov.nih.nci.confluence

import groovy.sql.Sql

class ErrorValidator {
	def properties = null
	Sql connection = null
	
	static void main(String[] args) 
	{	
		ErrorValidator errorValidator = new ErrorValidator();
		errorValidator.loadProperties();
		errorValidator.setDBConnection();
		errorValidator.validateData();
		errorValidator.closeDBConnection();
	}
	
	
	
	public void validateData()
	{	 
	    String certificationTemplateFile = properties.getProperty("certification.template.file");//"Deployment_Status_Template"
	    String certificationTemplateSpace = properties.getProperty("certification.template.space");//"test"
	    String certificationPageFile = properties.getProperty("certification.page.file");//"page1"
	    String certificationPageSpace = properties.getProperty("certification.page.space");//"confluence-cli-1.3.0.jar"
	    String dashboardVersion = properties.getProperty("dashboard.release.version");//"1.0.0"
	    String dashboardRevision = properties.getProperty("dashboard.revision.number");//"100"
	    
	    String dashboardRelease = "[" + dashboardVersion + "|#anchor|" + dashboardRevision + "]"

	    String statement   = "select PRODUCT,CERTIFICATION_STATUS,SINGLE_COMMAND_BUILD,SINGLE_COMMAND_DEPLOYMENT,DATABASE_INTEGRATION,TEMPLATE_VALIDATION,PRIVATE_PROPERTIES,CI_BUILD,BDA_ENABLED,DEPLOYMENT_SHAKEOUT,COMMANDLINE_INSTALLER from PROJECT_CERTIFICATION_STATUS order by product desc"

	    List projectRows = connection.rows(statement)	
	    	
	    connection.eachRow(statement) { row ->
	  
			String productString    = row.PRODUCT;
			String certificationStatus = row.CERTIFICATION_STATUS;	    
			String singleCommandBuild = row.SINGLE_COMMAND_BUILD;
			String singleCommandDeployment = row.SINGLE_COMMAND_DEPLOYMENT;
			String databaseIntegration = row.DATABASE_INTEGRATION;
			String templateValidation = row.TEMPLATE_VALIDATION;
			String privateProperties = row.PRIVATE_PROPERTIES;	    
			String ciBuild = row.CI_BUILD;
			String bdaEnabled = row.BDA_ENABLED;
			String deploymentShakeout = row.DEPLOYMENT_SHAKEOUT;
			String commandLineInstaller = row.COMMANDLINE_INSTALLER;
	    
			String productUrl  = productString.substring(productString.indexOf("|")+1, productString.indexOf("]"));
			String productName  = productString.substring(productString.indexOf("[")+1, productString.indexOf("|"));
 			println "productName:: " +productName
 			StringBuffer mailString = new StringBuffer("The certification of the project ").append(productName).append(" failed for the below features ")

			if(!certificationStatus.equals("(/)"))
			{
				
				println "bda enabled string:: " + bdaEnabled.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|"))
				if(bdaEnabled.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
				{				
					if (!singleCommandBuild.equals("(/)"))
					{
						mailString.append("\nSINGLE COMMAND BUILD FAILED");
					}
					if (!singleCommandDeployment.equals("(/)"))
					{
						mailString.append("\nSINGLE COMMAND DEPLOYMENT FAILED");
					}					
					if (!databaseIntegration.equals("(/)"))
					{
						mailString.append("\nDATABASE INTEGRATION FAILED");
					}
					if (!templateValidation.equals("(/)"))
					{
						mailString.append("\nTEMPLATE VALIDATION FAILED");
					}
					if (!privateProperties.equals("(/)"))
					{
						mailString.append("\nPRAVATE PROPERTIES FAILED");
					}
					println "ciBuild :: " + ciBuild
					if (ciBuild != null && !ciBuild.trim().equals("")) 
					{
						if (!ciBuild.substring(ciBuild.indexOf("[")+1, ciBuild.indexOf("|")).equals("(/)"))
						{
							mailString.append("\nCI BUILD FAILED");
						}
					}
					else
					{
							mailString.append("\nCI BUILD FAILED");
					}
					
					if (!deploymentShakeout.equals("(/)"))
					{
						mailString.append("\nDEPLOYMENT SHAKEOUT FAILED");
					}
					if (!commandLineInstaller.equals("(/)"))
					{
						mailString.append("\nCOMMANDLINE INSTALLER FAILED");
					}
					
					sendEmailToProjectTeam(productName,mailString.toString())
				}					
			}
			
		}
	
	}
	
	public void loadProperties()
	{
		properties = new java.util.Properties();
		properties.load( new java.io.FileInputStream( "wiki.properties" ));
	}
	
	
	public void setDBConnection()
	{
	    String monitorDb       = properties.getProperty("database.name");
	    String monitorUrl      = properties.getProperty("database.url");//"jdbc:mysql://localhost/wikiDB"
	    String monitorDriver   = properties.getProperty("database.driver");//"org.gjt.mm.mysql.Driver"
	    String monitorUser     = properties.getProperty("database.user");//"wikiuser"
	    String monitorPassword = properties.getProperty("database.password");//"password"

		connection = Sql.newInstance(monitorUrl, monitorUser, monitorPassword, monitorDriver)		
	}
	
	public void closeDBConnection()
	{
		connection.close();		
	}

	public void sendEmailToProjectTeam(String projectName, String message )
	{
		// Get the POCs for the projectName
		String devPocEmail,govPocEmail= null
		
		String pocStatement = "SELECT DEV_POC_EMAIL, GOV_POC_EMAIL FROM PROJECT_POC WHERE PROJECT_NAME='"+projectName + "'"
		connection.eachRow(pocStatement) { row ->			  
			devPocEmail    = row.DEV_POC_EMAIL;
			govPocEmail = row.GOV_POC_EMAIL;	 
		}	
		MailSender ms = new MailSender()
		ArrayList recipientList = new ArrayList()
		println("Sending email to Dev Poc " +devPocEmail + " And GOV Poc " + govPocEmail)
		recipientList.add(devPocEmail)
		recipientList.add(govPocEmail)
		ms.sendMessage(properties.getProperty("mail.hostname"),Integer.parseInt(properties.getProperty("mail.portnumber")),properties.getProperty("mail.send.address"),recipientList ,projectName.toUpperCase()  + " CERTIFICATION STATUS",message)
	}	
	
}
