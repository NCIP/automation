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
	    String statement   = "select PRODUCT,CERTIFICATION_STATUS,SINGLE_COMMAND_BUILD,SINGLE_COMMAND_DEPLOYMENT,DATABASE_INTEGRATION,REMOTE_UPGRADE,TEMPLATE_VALIDATION,PRIVATE_PROPERTIES,CI_BUILD,BDA_ENABLED,DEPLOYMENT_SHAKEOUT,COMMANDLINE_INSTALLER from PROJECT_CERTIFICATION_STATUS order by product desc"

	    List projectRows = connection.rows(statement)	
	    	
	    connection.eachRow(statement) { row ->
	  
			String productString    = row.PRODUCT;
			String certificationStatus = row.CERTIFICATION_STATUS;	    
			String singleCommandBuild = row.SINGLE_COMMAND_BUILD;
			String singleCommandDeployment = row.SINGLE_COMMAND_DEPLOYMENT;
			String databaseIntegration = row.DATABASE_INTEGRATION;
			String remoteUpgrade = row.REMOTE_UPGRADE;
			String templateValidation = row.TEMPLATE_VALIDATION;
			String privateProperties = row.PRIVATE_PROPERTIES;	    
			String ciBuild = row.CI_BUILD;
			String bdaEnabled = row.BDA_ENABLED;
			String deploymentShakeout = row.DEPLOYMENT_SHAKEOUT;
			String commandLineInstaller = row.COMMANDLINE_INSTALLER;
	    
			String productUrl  = productString.substring(productString.indexOf("|")+1, productString.indexOf("]"));
			String productName  = productString.substring(productString.indexOf("[")+1, productString.indexOf("|"));
 			println "productName:: " +productName
 			
 		
 			String mailString = properties.getProperty("mail.pre.template")

			if(!certificationStatus.equals("(/)"))
			{
				
				println "bda enabled string:: " + bdaEnabled.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|"))

				if(bdaEnabled != null && bdaEnabled.length() != 0 && bdaEnabled.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
				{				
					if (singleCommandBuild != null && !singleCommandBuild.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
					{
						mailString = mailString + properties.getProperty("mail.single.command.build")
						mailString = mailString.replaceAll("ERROR_MESSAGE",singleCommandBuild.substring(singleCommandBuild.lastIndexOf("|")+1, singleCommandBuild.indexOf("]")))
					}
					if (singleCommandDeployment != null &&  !singleCommandDeployment.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
					{
						mailString = mailString + properties.getProperty("mail.single.command.deployment")
						mailString = mailString.replaceAll("ERROR_MESSAGE",singleCommandDeployment.substring(singleCommandDeployment.lastIndexOf("|")+1, singleCommandDeployment.indexOf("]")))
					}					
					if (databaseIntegration != null &&  !databaseIntegration.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
					{
						mailString = mailString + properties.getProperty("mail.database.integration")
						mailString = mailString.replaceAll("ERROR_MESSAGE",databaseIntegration.substring(databaseIntegration.lastIndexOf("|")+1, databaseIntegration.indexOf("]")))
					}
					if (remoteUpgrade != null &&  !remoteUpgrade.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
					{
						mailString = mailString + properties.getProperty("mail.remote.upgrade")
						mailString = mailString.replaceAll("ERROR_MESSAGE",remoteUpgrade.substring(remoteUpgrade.lastIndexOf("|")+1, remoteUpgrade.indexOf("]")))
					}
					if (templateValidation != null && !templateValidation.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
					{
						mailString = mailString + properties.getProperty("mail.template.validation")
						mailString = mailString.replaceAll("ERROR_MESSAGE",templateValidation.substring(templateValidation.lastIndexOf("|")+1, templateValidation.indexOf("]")))
					}
					if (privateProperties != null && !privateProperties.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
					{
						mailString = mailString + properties.getProperty("mail.private.properties")
						mailString = mailString.replaceAll("ERROR_MESSAGE",privateProperties.substring(privateProperties.lastIndexOf("|")+1, privateProperties.indexOf("]")))
					}
					println "ciBuild :: " + ciBuild
					if (ciBuild != null && !ciBuild.trim().equals("")) 
					{
						if (!ciBuild.substring(ciBuild.indexOf("[")+1, ciBuild.indexOf("|")).equals("(/)"))
						{
							mailString = mailString + properties.getProperty("mail.ci.build")
							mailString = mailString.replaceAll("ERROR_MESSAGE",ciBuild.substring(ciBuild.lastIndexOf("|")+1, ciBuild.indexOf("]")))
						}
					}
					else
					{
							mailString = mailString + properties.getProperty("mail.ci.build")
							mailString = mailString.replaceAll("ERROR_MESSAGE","")
					}
					
					if (deploymentShakeout != null && !deploymentShakeout.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
					{
						mailString = mailString + properties.getProperty("mail.deployment.shakeout")
						mailString = mailString.replaceAll("ERROR_MESSAGE",deploymentShakeout.substring(deploymentShakeout.lastIndexOf("|")+1, deploymentShakeout.indexOf("]")))
					}
					if (commandLineInstaller != null && !commandLineInstaller.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
					{
						mailString = mailString + properties.getProperty("mail.commandLine.installer")
						mailString = mailString.replaceAll("ERROR_MESSAGE",commandLineInstaller.substring(commandLineInstaller.lastIndexOf("|")+1, commandLineInstaller.indexOf("]")))
					}					
					mailString = mailString + properties.getProperty("mail.post.template")
					sendEmailToProjectTeam(productName,mailString.replaceAll("INSERT_SVN_URL",productUrl))
				}
//				else
//				{
//				     println (" Beta Message :: " +bdaEnabled.substring(bdaEnabled.lastIndexOf("|")+1, bdaEnabled.indexOf("]")))
//
//					if (bdaEnabled != null && bdaEnabled.length() != 0 && bdaEnabled.substring(bdaEnabled.lastIndexOf("|")+1, bdaEnabled.indexOf("]")).contains("-beta"))
//					{
//						mailString = mailString + properties.getProperty("mail.beta.version")
//						if (singleCommandBuild != null && !singleCommandBuild.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
//						{
//							mailString = mailString + properties.getProperty("mail.single.command.build")
//							mailString = mailString.replaceAll("ERROR_MESSAGE",singleCommandBuild.substring(singleCommandBuild.lastIndexOf("|")+1, singleCommandBuild.indexOf("]")))
//						}
//						if (singleCommandDeployment != null && !singleCommandDeployment.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
//						{
//							mailString = mailString + properties.getProperty("mail.single.command.deployment")
//							mailString = mailString.replaceAll("ERROR_MESSAGE",singleCommandDeployment.substring(singleCommandDeployment.lastIndexOf("|")+1, singleCommandDeployment.indexOf("]")))
//						}
//						if (databaseIntegration != null && !databaseIntegration.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
//						{
//							mailString = mailString + properties.getProperty("mail.database.integration")
//							mailString = mailString.replaceAll("ERROR_MESSAGE",databaseIntegration.substring(databaseIntegration.lastIndexOf("|")+1, databaseIntegration.indexOf("]")))
//						}
//						if (remoteUpgrade != null && !remoteUpgrade.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
//						{
//							mailString = mailString + properties.getProperty("mail.remote.upgrade")
//							mailString = mailString.replaceAll("ERROR_MESSAGE",remoteUpgrade.substring(remoteUpgrade.lastIndexOf("|")+1, remoteUpgrade.indexOf("]")))
//						}
//						if (templateValidation != null && !templateValidation.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
//						{
//							mailString = mailString + properties.getProperty("mail.template.validation")
//							mailString = mailString.replaceAll("ERROR_MESSAGE",templateValidation.substring(templateValidation.lastIndexOf("|")+1, templateValidation.indexOf("]")))
//						}
//						if (privateProperties != null && !privateProperties.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
//						{
//							mailString = mailString + properties.getProperty("mail.private.properties")
//							mailString = mailString.replaceAll("ERROR_MESSAGE",privateProperties.substring(privateProperties.lastIndexOf("|")+1, privateProperties.indexOf("]")))
//						}
//						println "ciBuild :: " + ciBuild
//						if (ciBuild != null && !ciBuild.trim().equals(""))
//						{
//							if (!ciBuild.substring(ciBuild.indexOf("[")+1, ciBuild.indexOf("|")).equals("(/)"))
//							{
//								mailString = mailString + properties.getProperty("mail.ci.build")
//								mailString = mailString.replaceAll("ERROR_MESSAGE",ciBuild.substring(ciBuild.lastIndexOf("|")+1, ciBuild.indexOf("]")))
//							}
//						}
//						else
//						{
//								mailString = mailString + properties.getProperty("mail.ci.build")
//								mailString = mailString.replaceAll("ERROR_MESSAGE","")
//						}
//
//						if (deploymentShakeout != null && !deploymentShakeout.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
//						{
//							mailString = mailString + properties.getProperty("mail.deployment.shakeout")
//							mailString = mailString.replaceAll("ERROR_MESSAGE",deploymentShakeout.substring(deploymentShakeout.lastIndexOf("|")+1, deploymentShakeout.indexOf("]")))
//						}
//						if (commandLineInstaller != null && !commandLineInstaller.substring(bdaEnabled.indexOf("[")+1, bdaEnabled.indexOf("|")).equals("(/)"))
//						{
//							mailString = mailString + properties.getProperty("mail.commandLine.installer")
//							mailString = mailString.replaceAll("ERROR_MESSAGE",commandLineInstaller.substring(commandLineInstaller.lastIndexOf("|")+1, commandLineInstaller.indexOf("]")))
//						}
//						mailString = mailString + properties.getProperty("mail.post.template")
//						sendEmailToProjectTeam(productName,mailString.replaceAll("INSERT_SVN_URL",productUrl))
//					}
//				}
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
		String devPocEmail,govPocEmail,devPoc= null
        String cotrEmail = null;
        println "Sending Email To:" + projectName ;
		try
		{		
			message = message.replaceAll("INSERT_PROJECT_NAME",projectName)


			String pocStatement = "SELECT DEV_POC, DEV_POC_EMAIL, GOV_POC_EMAIL, COTR_EMAIL FROM PROJECT_POC WHERE PROJECT_NAME='"+projectName + "'"
			connection.eachRow(pocStatement) { row ->
				devPoc    = row.DEV_POC;
				devPocEmail    = row.DEV_POC_EMAIL;
				govPocEmail = row.GOV_POC_EMAIL;
                cotrEmail = row.COTR_EMAIL;
			}
			println ("Dev POC: " + devPoc)
			message = message.replaceAll("DEV_POC_NAME",devPoc)
			gov.nih.nci.confluence.MailSender ms = new gov.nih.nci.confluence.MailSender()
			ArrayList recipientList = new ArrayList()
			println("Sending email to Dev Poc " +devPocEmail + " And GOV Poc " + govPocEmail)
			recipientList.add(devPocEmail)
			recipientList.add(govPocEmail)
            recipientList.add(cotrEmail);
			properties.getProperty("mail.additional.recipients").split(',').eachWithIndex {processToken, i -> 
				recipientList.add(processToken)
			}
			ms.sendMessage(properties.getProperty("mail.hostname"),Integer.parseInt(properties.getProperty("mail.portnumber")),properties.getProperty("mail.send.address"),recipientList , "BDA Certification status for " + projectName,message)
		}
		catch(Exception ex){
          println "Exception sending email for project:" + projectName;  
          println ex.toString();

			ex.printStackTrace()
		}
	}
}
