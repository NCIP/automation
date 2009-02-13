import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher
import java.util.regex.Pattern

String methodName = args[0];
println methodName;

if (methodName	 != null)
{
	this.invokeMethod(methodName, null);
}

void checkBdaEnabled ()
{
	getBdaUtilsVersion()
	if(properties['certification.property.value'] == '')
	{
		ant.fail("PROJECT NOT BDA ENABLED")
	}
}

void checkDeploymentShakeout ()
{
	
	def buildFileLocation=properties['master.build.location']

	String projectPropertiesFile=new File(buildFileLocation+"/install.properties").getAbsoluteFile();

	installFile = new File(buildFileLocation+"/install.xml").getAbsoluteFile()
	installProject = new XmlParser().parse(installFile)

	ant.property(file: projectPropertiesFile)
	println properties['application.url']	
	def applicationUrl = properties['application.url']
	if(applicationUrl)
	{					
		ant.runseliniumtest(hostname:applicationUrl)
	}					
	if(installProject.target.find{it.'@name'=='install'}.'@depends'.contains('install:grid'))
	{
		println "Run the selenium test for the jboss server"
		def jbossServerPort = properties['jboss.server.port']
		def jbossServerHostName = properties['jboss.server.hostname']
		println jbossServerPort;
		ant.runseliniumtest(hostname:'http://'+jbossServerHostName+':'+jbossServerPort)
	}
	if(installProject.target.find{it.'@name'=='install'}.'@depends'.contains('install:tomcat'))
	{
		println "Run the selenium test for the tomcat server"
		String tomcatServerPort = properties['tomcat.port.http']
		String tomcatServerHostName = properties['tomcat.hostname']
		println tomcatServerPort;
		ant.runseliniumtest(hostname:'http://'+tomcatServerHostName+':'+tomcatServerPort)				
	}
}


void formatAndSetDate ()
{

	def latestCIBuild=properties['latest.build.dir']

	def ant = new AntBuilder()
	def format = new SimpleDateFormat("yyyy-MM-dd");
	def formatter = new SimpleDateFormat("EEE, dd MMM yyyy");

	latestCIBuild.split('_').eachWithIndex {processToken, i -> 
	if(i == 0)
		{
			Date dtConverted = format.parse(processToken);
			println("Time Stamp Format" + dtConverted)
			parsedDate = formatter.format(dtConverted);
			println("Time Stamp Format" + parsedDate);
			project.setProperty("certification.property.value",parsedDate);
		}
	}
}



void checkPrivateRepositoryProperties ()
{
       def privatePropertiesLocation=properties['svn.private.local.checkout']
       try
       {
	       propertiesDir = new File(privatePropertiesLocation + "/properties").getAbsoluteFile()
	       java.util.regex.Pattern upgradePattern = java.util.regex.Pattern.compile(/.*dev.*upgrade.properties/)
	       java.util.regex.Pattern installPattern = java.util.regex.Pattern.compile(/.*dev.*install.properties/)

	       if(propertiesDir.exists())
	       {
		       flag = false
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
	def buildFileLocation=properties['master.build.location']

	try
	{
		String projectPropertiesFile=new File(buildFileLocation+"/install-properties.template").getAbsoluteFile();
		println projectPropertiesFile
	}
	catch(FileNotFoundException ex)
	{
		ant.fail("TEMPLATE VALIDATION FAILED: Can not find the install-properties.template file ")
	}

	installFile = new File(buildFileLocation+"/install.xml").getAbsoluteFile()
	installProject = new XmlParser().parse(installFile)				

	if(!installProject.switch.find{it.@value=='${properties.file.type}'}.'case'.find{it.@value=='install'}.property.@name.contains('properties.template.file'))
		ant.fail("TEMPLATE VALIDATION FAILED: properties.template.file property is not set ")
}

void getBdaUtilsVersion ()
{
	def buildFileLocation=properties['master.build.location']

	String projectPropertiesFile=new File(buildFileLocation+"/project.properties").getAbsoluteFile();
	Properties props = new Properties();
	props.load(new FileInputStream(projectPropertiesFile));			
	String bdaVersion = props.getProperty("bda.version");
	println bdaVersion;
	project.setProperty("certification.property.value",bdaVersion);
}

void checkDBIntegration ()
{
	def buildFileLocation=properties['master.build.location']
	def targetName=properties['database.integration.target']
	def databaseType=properties['database.type']
					
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
		installFile = new File(buildFileLocation+"/install.xml").getAbsoluteFile()
		installProject = new XmlParser().parse(installFile)				
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
	def directoryName=properties['certification.directory.name']
	proc1 = 'ps -ef'.execute()
	proc2 = "grep ${directoryName}".execute()
	proc3 = 'grep java'.execute()

	proc1 | proc2 | proc3
	proc3.waitFor()

	proc3.in.eachLine { 
		line -> line.split('\\s+').eachWithIndex {processToken, i -> 
			if(i == 1)
				{
					println "${processToken}"
					proc4 ="kill -9 ${processToken}".execute()
					proc4.waitFor()
					proc4.in.eachLine { 
						line2 -> println line2
					}
				}
		}
	 }
}