package gov.nih.nci.confluence

import groovy.sql.Sql

class BuildStatusUpdater {
	def properties = null
	Sql connection = null
	String confluence =null
	
	static void main(String[] args) 
	{	
		BuildStatusUpdater buildStatus = new BuildStatusUpdater();
		buildStatus.loadProperties();
		buildStatus.setDBConnection();
		buildStatus.setDefaultConfluenceString();
		//buildStatus.updateBuildStatus();
		buildStatus.updateCertificationStatus();
		buildStatus.closeDBConnection();
	}
	
	public void updateBuildStatus()
	{	 
	    String deploymentStatusTemplateFile = properties.getProperty("deployment-status.template.file");//"Deployment_Status_Template"
	    String deploymentStatusTemplateSpace = properties.getProperty("deployment-status.template.space");//"test"
	    String deploymentStatusPageFile = properties.getProperty("deployment-status.page.file");//"page1"
	    String deploymentStatusPageSpace = properties.getProperty("deployment-status.page.space");//"confluence-cli-1.3.0.jar"

        // get most recent tempates
		doCmd("${confluence} -a getPageSource --space \""+deploymentStatusTemplateSpace+"\" --title \"" + deploymentStatusTemplateFile+ "\" --file _temp.txt")
		String statement   = "select PRODUCT,DEV,QA,STAGE,PROD from PROJECT_BUILD_STATUS "
	

        int count = 0
        connection.eachRow(statement) { row ->
            count++

            String productName    = row.PRODUCT;
            String devStatus = row.DEV;
            String qaStatus = row.QA;
            String stageStatus = row.STAGE;
            String prodStatus = row.PROD;

			String findReplace = "--findReplace \"Product${count}:${productName},dev-status${count}:${devStatus},qa-status${count}:${qaStatus},stage-status${count}:${stageStatus},prod-status${count}:${prodStatus}\""
	        
			println findReplace
			// update page
			doCmd("${confluence} -a storePage --space \""+deploymentStatusPageSpace+"\" --title \""+deploymentStatusPageFile+"\"   --file _temp.txt ${findReplace}")
			doCmd("${confluence} -a getPageSource --space \""+deploymentStatusPageSpace+"\" --title \""+deploymentStatusPageFile+"\"    --file _temp.txt")
		}
	}
	
	
	public void updateCertificationStatus()
	{	 
	    String certificationTemplateFile = properties.getProperty("certification.template.file");//"Deployment_Status_Template"
	    String certificationTemplateSpace = properties.getProperty("certification.template.space");//"test"
	    String certificationPageFile = properties.getProperty("certification.page.file");//"page1"
	    String certificationPageSpace = properties.getProperty("certification.page.space");//"confluence-cli-1.3.0.jar"
	    String dashboardVersion = properties.getProperty("dashboard.release.version");//"1.0.0"
	    String dashboardRevision = properties.getProperty("dashboard.revision.number");//"100"
	    
	    String dashboardRelease = "[" + dashboardVersion + "|#anchor|" + dashboardRevision + "]"

	// get most recent tempates
		doCmd("${confluence} -a getPageSource --space \""+certificationTemplateSpace+"\" --title \"" + certificationTemplateFile+ "\" --file "+certificationTemplateFile+"_temp.txt")
		String statement   = "select PRODUCT,CERTIFICATION_STATUS,SINGLE_COMMAND_BUILD,SINGLE_COMMAND_DEPLOYMENT,DATABASE_INTEGRATION,TEMPLATE_VALIDATION,PRIVATE_PROPERTIES,CI_BUILD,BDA_ENABLED,DEPLOYMENT_SHAKEOUT,COMMANDLINE_INSTALLER from PROJECT_CERTIFICATION_STATUS order by product desc"

	List projectRows = connection.rows(statement)	
	
	
	int count =projectRows.size()
	
	
	connection.eachRow(statement) { row ->
	    

	    String productName    = row.PRODUCT;
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
	    

			String findReplace = "--findReplace \"Product${count}:${productName},Certification-Status${count}:${certificationStatus},Single-Command-Build${count}:${singleCommandBuild},Single-Command-Deployment${count}:${singleCommandDeployment},Database-Integration${count}:${databaseIntegration},Template-Validation${count}:${templateValidation},Private-Properties${count}:${privateProperties},CI-Build${count}:${ciBuild},BDA-Enabled${count}:${bdaEnabled},Deployment-Shakeout${count}:${deploymentShakeout},CommandLine-Installer${count}:${commandLineInstaller}\""

			println findReplace
			// update page
			doCmd("${confluence} -a storePage --space \""+certificationPageSpace+"\" --title \""+certificationPageFile+"\"   --file "+certificationTemplateFile+"_temp.txt ${findReplace}")
			doCmd("${confluence} -a getPageSource --space \""+certificationPageSpace+"\" --title \""+certificationPageFile+"\"    --file "+certificationTemplateFile+"_temp.txt")
			count--
		}
	// Update the release version
	String findReplaceVersion = "--findReplace \"DashboardReleaseVersion:${dashboardRelease}\""
	doCmd("${confluence} -a storePage --space \""+certificationPageSpace+"\" --title \""+certificationPageFile+"\"   --file "+certificationTemplateFile+"_temp.txt ${findReplaceVersion}")	
	}
	
	
	public void loadProperties()
	{
		properties = new java.util.Properties();
		properties.load( new java.io.FileInputStream( "wiki.properties" ));
	}
	
	public void setDefaultConfluenceString()
	{
	    String wikiServer = properties.getProperty("wiki.server");//"http://localhost:8080"
	    String wikiUser = properties.getProperty("wiki.user");//"automation"
	    String wikiPassword = properties.getProperty("wiki.password");//"password"
	    String concluenceCliArtifact = properties.getProperty("concluence.cli.artifact");//"confluence-cli-1.3.0.jar"
	    
	    confluence = "java -jar "+ concluenceCliArtifact +" --server "+wikiServer+" --user "+wikiUser+" --password "+wikiPassword
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
	
	static void doCmdWithException(String cmd) {

		Process process = doCmd(cmd)

	    if (process.exitValue() != 0) {
	    	println "ERROR VALUE: ${process.exitValue()}"
		   	println "ERROR TEXT:  ${process.err.text}"
		    throw new Exception("Command failure code: ${process.exitValue()}\n ${process.err.text()}\n ${cmd}")
		}
		return
	}

	static Process doCmd(String cmd) {
		println ">>> ${cmd}"
		def cmdStr 
		def osName = System.getProperty("os.name")
		println " OS-NAME:: $osName "

		if (osName.startsWith("Win"))		
			cmdStr = "cmd /c ${cmd}"
		else
			cmdStr = ["sh","-c","${cmd}"]
	
		Process process = cmdStr.execute()
	    	println "${process.text} ${process.err.text}"
		return process

	}
}
