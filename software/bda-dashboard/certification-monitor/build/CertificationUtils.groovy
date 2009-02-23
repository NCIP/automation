import org.apache.tools.ant.Project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.HashMap

import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper
import org.codehaus.groovy.runtime.InvokerHelper

class CertificationUtils
{
	def ant = new AntBuilder();
	def project = new Project();
	HashMap defaultProperties = new HashMap();

	CertificationUtils(AntBuilder ant,Project project )
	{
		this.ant = ant
		this.project = project
		populateDefaultProperties()
	}

	void populateDefaultProperties ()
	{
		defaultProperties.put("application.base.path.linux","\${user.home}/apps/petstore-webapp");
		defaultProperties.put("application.base.path.windows","C:/apps/petstore-webapp");
		defaultProperties.put("database.system.user","mysql")
		defaultProperties.put("database.system.password","mysql")
		defaultProperties.put("database.server","localhost")
		defaultProperties.put("database.port","3306")
		defaultProperties.put("database.name","genericdb")
		defaultProperties.put("database.user","genericuser")
		defaultProperties.put("database.password","password")
		defaultProperties.put("mail.smtp.host","localhost")
		defaultProperties.put("jboss.server.hostname","localhost")
	}

	void checkBdaEnabled ()
	{
		getBdaUtilsVersion()
		if(project.properties['certification.property.value'] == '')
		{
			ant.fail("PROJECT NOT BDA ENABLED")
		}
	}

	void checkDeploymentShakeout ()
	{

		def buildFileLocation=project.properties['master.build.location']

		String projectPropertiesFile=new File(buildFileLocation+"/install.properties").getAbsoluteFile();

		String installFile = new File(buildFileLocation+"/install.xml").getAbsoluteFile()
		def installProject = new XmlParser().parse(installFile)

		ant.property(file: projectPropertiesFile)
		println project.properties['application.url']	
		def applicationUrl = project.properties['application.url']
		if(applicationUrl)
		{					
			ant.runseliniumtest(hostname:applicationUrl)
		}					
		if(installProject.target.find{it.'@name'=='install'}.'@depends'.contains('install:grid'))
		{
			println "Run the selenium test for the jboss server"
			def jbossServerPort = project.properties['jboss.server.port']
			def jbossServerHostName = project.properties['jboss.server.hostname']
			println jbossServerPort;
			ant.runseliniumtest(hostname:'http://'+jbossServerHostName+':'+jbossServerPort)
		}
		if(installProject.target.find{it.'@name'=='install'}.'@depends'.contains('install:tomcat'))
		{
			println "Run the selenium test for the tomcat server"
			String tomcatServerPort = project.properties['tomcat.port.http']
			String tomcatServerHostName = project.properties['tomcat.hostname']
			println tomcatServerPort;
			ant.runseliniumtest(hostname:'http://'+tomcatServerHostName+':'+tomcatServerPort)				
		}
	}


	void formatAndSetDate ()
	{

		def latestCIBuild=project.properties['latest.build.dir']

		def ant = new AntBuilder()
		def format = new SimpleDateFormat("yyyy-MM-dd");
		def formatter = new SimpleDateFormat("EEE, dd MMM yyyy");

		latestCIBuild.split('_').eachWithIndex {processToken, i -> 
		if(i == 0)
			{
				Date dtConverted = format.parse(processToken);
				println("Time Stamp Format" + dtConverted)
				def parsedDate = formatter.format(dtConverted);
				println("Time Stamp Format" + parsedDate);
				project.setProperty("certification.property.value",parsedDate);
			}
		}
	}



	void checkPrivateRepositoryProperties ()
	{
	       def privatePropertiesLocation=project.properties['svn.private.local.checkout']
	       try
	       {
		       def propertiesDir = new File(privatePropertiesLocation + "/properties").getAbsoluteFile()
		       java.util.regex.Pattern upgradePattern = java.util.regex.Pattern.compile(/.*dev.*upgrade.properties/)
		       java.util.regex.Pattern installPattern = java.util.regex.Pattern.compile(/.*dev.*install.properties/)

		       if(propertiesDir.exists())
		       {
			       def flag = false
			       propertiesDir.eachFileRecurse
			       { file ->
				       if (file.isFile())
				       {
					       if(upgradePattern.matcher(file.getName()).matches() || installPattern.matcher(file.getName()).matches())
						       flag = true
				       }
			       }
			       if(!flag)
				       ant.fail("PRIVATE REPOSITORY FAILED: Can not find the install or upgrade property files ")
		       }
		       else
		       {
			       println "directory does not exist"
			       ant.fail("PRIVATE REPOSITORY FAILED: Can not find the properties folder ")
		       }
	       }
	       catch(FileNotFoundException ex)
	       {
		       ant.fail("PRIVATE REPOSITORY FAILED: Can not find the properties folder ")
	       }
	}


	void checkTemplateFiles ()
	{
		def buildFileLocation=project.properties['master.build.location']

		try
		{
			String projectPropertiesFile=new File(buildFileLocation+"/install-properties.template").getAbsoluteFile();
			println projectPropertiesFile
		}
		catch(FileNotFoundException ex)
		{
			ant.fail("TEMPLATE VALIDATION FAILED: Can not find the install-properties.template file ")
		}

		String installFile = new File(buildFileLocation+"/install.xml").getAbsoluteFile()
		def installProject = new XmlParser().parse(installFile)				

		if(!installProject.switch.find{it.@value=='${properties.file.type}'}.'case'.find{it.@value=='install'}.property.@name.contains('properties.template.file'))
			ant.fail("TEMPLATE VALIDATION FAILED: properties.template.file property is not set ")
	}

	void getBdaUtilsVersion ()
	{
		def buildFileLocation=project.properties['master.build.location']

		String projectPropertiesFile=new File(buildFileLocation+"/project.properties").getAbsoluteFile();
		Properties props = new Properties();
		props.load(new FileInputStream(projectPropertiesFile));			
		String bdaVersion = props.getProperty("bda.version");
		println bdaVersion;
		project.setProperty("certification.property.value",bdaVersion);
	}

	void checkDBIntegration ()
	{
		def buildFileLocation=project.properties['master.build.location']
		def targetName=project.properties['database.integration.target']
		def databaseType=project.properties['database.type']

		String installPropertiesFile=new File(buildFileLocation+"/install.properties").getAbsoluteFile();			
		Properties props = new Properties();
		props.load(new FileInputStream(installPropertiesFile));			
		String dbFlag = props.getProperty("exclude.database");
		println dbFlag;
		println databaseType;
		if(dbFlag != null)
		{
			println "Flag is set skip DB integration Check"
		}
		else
		{
			println "DB integration Check"
			String installFile = new File(buildFileLocation+"/install.xml").getAbsoluteFile()
			def installProject = new XmlParser().parse(installFile)				
			println installProject.target.'@name'.contains(targetName)
			println targetName
			if(installProject.target.'@name'.contains(targetName))
			{
				if(installProject.target.find{it.'@name'=='install'}.'@depends'.contains(targetName))
				{
					def targetUpgrade = installProject.target.find{it.@name==targetName}.'database-upgrade'
					if(!targetUpgrade)
						ant.fail("DATABASE INTEGRATION FAILED: The database-upgrade target is missing")

					def targetInstall = installProject.'target'.find{it.@name==targetName}.switch.'case'.find{it.'@value'==databaseType}.'database-install'
					if(!targetInstall)
						ant.fail("DATABASE INTEGRATION FAILED: The database-install target is missing")

					println "CHECK FOR MACROS COMPLETE"
				}
				else
				{				
					ant.fail("DATABASE INTEGRATION FAILED: The install:database target is not called from the install target")
				}
			}
			else
			{				
				ant.fail("DATABASE INTEGRATION FAILED: The install:database target is not found in the install.xml")
			}

		}
	}


	void killProcessOnCertificationDir ()
	{
		def directoryName=project.properties['certification.directory.name']
		def proc1 = 'ps -ef'.execute()
		def proc2 = "grep ${directoryName}".execute()
		def proc3 = 'grep java'.execute()

		proc1 | proc2 | proc3
		proc3.waitFor()

		proc3.in.eachLine { 
			line -> line.split('\\s+').eachWithIndex {processToken, i -> 
				if(i == 1)
					{
						println "${processToken}"
						def proc4 ="kill -9 ${processToken}".execute()
						proc4.waitFor()
						proc4.in.eachLine { 
							line2 -> println line2
						}
					}
			}
		 }
	}
	
	void checkCommandLineInstaller ()
	{
		def buildFileLocation=project.properties['master.build.location']	
		def antFile = new File(buildFileLocation+"/build.xml")

		def build = new Project()
		build.init()
		build.setProperty("project.name", "petstore");
		ProjectHelper.configureProject(build, antFile)	


		String installerFile = build.getProperty("dist.dir") + "/" + build.getProperty("petstore-webapp.install.zip.file")		
		println installerFile
		println installerworking
		ant.unzip(src: installerFile,dest:'working/installer' )

		String propertiesList = getListOfobfuscatedProperties()		
		
		HashMap installerProperties = getPropertyValuesList(propertiesList.substring(1,propertiesList.length() -1))	
		println "installerProperties::" + installerProperties
		

		def deployFile = new File("build/working/installer/build.xml")	
		def deployProject = new Project()
		deployProject.init()

		Set entries = installerProperties.entrySet();
		Iterator it = entries.iterator();
		while (it.hasNext()) {
		Map.Entry entry = (Map.Entry) it.next();
			System.out.println(entry.getKey() + "-->" + entry.getValue());
			deployProject.setProperty(entry.getKey(), entry.getValue());
		}

		ProjectHelper.configureProject(deployProject, deployFile)	
		
		
							
		deployProject.executeTarget("install");
		
	}	
	
	
	def getListOfobfuscatedProperties ()	
	{
		def buildFileLocation=project.properties['master.build.location']
		def antFile = new File(buildFileLocation+"/build.xml")

		def installProject = new XmlParser().parse(antFile)

		if(installProject.target.find{it.'@name'=='dist:installer'}.'obfuscate-properties-file')
		{
			return installProject.target.find{it.'@name'=='dist:installer'}.'obfuscate-properties-file'.'@required.property.list'
		}
	}
	
	def getPropertyValuesList (String propertiesList)	
	{
	HashMap obfuscatedProperties = new HashMap()
	propertiesList.split(',').eachWithIndex {processToken, i -> 	
			if(checkValueFound(processToken))
			{
			println("processToken::" + processToken)
			obfuscatedProperties.put(processToken,defaultProperties.get(processToken))
			println("obfuscatedProperties::" + obfuscatedProperties.get(processToken))
			}
		}
	return obfuscatedProperties
	}
	
	
	def checkValueFound (String propertiesList)	
	{
		return true
	}
	

}
