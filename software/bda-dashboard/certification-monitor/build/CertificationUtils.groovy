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
	public static final String WIKI_SUCCESSFUL = "(/)";
	public static final String WIKI_FAILED = "(x)";
	public static final String WIKI_MODERATE = "(!)";
	public static final int CI_BUILD_DAYS = 1;
	
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
		if(project.properties['database.type']=='mysql')
		{
			defaultProperties.put("database.system.user",project.properties["mysql.database.system.user"])
			defaultProperties.put("database.system.password",project.properties["mysql.database.system.password"])
			defaultProperties.put("database.server",project.properties["mysql.database.server"])
			defaultProperties.put("database.port",project.properties["mysql.database.port"])
			defaultProperties.put("database.name",project.properties["mysql.database.name"])
			defaultProperties.put("database.user",project.properties["mysql.database.user"])
			defaultProperties.put("database.password",project.properties["mysql.database.password"])
		}
		if(project.properties['database.type']=='postgresql')
		{
			defaultProperties.put("database.system.user",project.properties["postgresql.database.system.user"])
			defaultProperties.put("database.system.password",project.properties["postgresql.database.system.password"])
			defaultProperties.put("database.server",project.properties["postgresql.database.server"])
			defaultProperties.put("database.port",project.properties["postgresql.database.port"])
			defaultProperties.put("database.name",project.properties["postgresql.database.name"])
			defaultProperties.put("database.user",project.properties["postgresql.database.user"])
			defaultProperties.put("database.password",project.properties["postgresql.database.password"])
		}
		defaultProperties.put("mail.smtp.host","localhost")
		defaultProperties.put("jboss.server.hostname","localhost")
	}

	void checkBdaEnabled ()
	{	
		String bdaVersion = getBdaUtilsVersion();
		if( bdaVersion != null)
		{
			StringBuffer wikiStr = new StringBuffer("'[");
			wikiStr = wikiStr.append("(/)|http://gforge.nci.nih.gov/svnroot/commonlibrary/trunk/ivy-repo/ncicb/bda-utils/"+bdaVersion)
			wikiStr = wikiStr.append("|"+bdaVersion+"]'")
			project.setProperty("certification.property.value",wikiStr.toString());			
		}
		else
		{	
			ant.fail("PROJECT NOT BDA ENABLED")
		}
	}

	void checkDeploymentShakeout ()
	{

		def buildFileLocation=project.properties['master.build.location']
		def basedir=project.properties['basedir']
		println basedir

		String projectPropertiesFile=new File(basedir +"/"+buildFileLocation+"/install.properties").getAbsoluteFile();

		String installFile = new File(basedir +"/"+buildFileLocation+"/install.xml").getAbsoluteFile()
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
	       def basedir=project.properties['basedir']
	       println basedir
	       try
	       {
		       def propertiesDir = new File(basedir +"/"+privatePropertiesLocation + "/properties").getAbsoluteFile()
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
		def basedir=project.properties['basedir']
		println basedir
		
		try
		{
			String projectPropertiesFile=new File(basedir +"/"+buildFileLocation+"/install-properties.template").getAbsoluteFile();
			println projectPropertiesFile
		}
		catch(FileNotFoundException ex)
		{
			ant.fail("TEMPLATE VALIDATION FAILED: Can not find the install-properties.template file ")
		}

		String installFile = new File(basedir +"/"+buildFileLocation+"/install.xml").getAbsoluteFile()
		def installProject = new XmlParser().parse(installFile)				

		if(!installProject.switch.find{it.@value=='${properties.file.type}'}.'case'.find{it.@value=='install'}.property.@name.contains('properties.template.file'))
			ant.fail("TEMPLATE VALIDATION FAILED: properties.template.file property is not set ")
	}

	def getBdaUtilsVersion ()
	{
		def buildFileLocation=project.properties['master.build.location']
		def basedir=project.properties['basedir']
		println basedir

		String projectPropertiesFile=new File(basedir +"/"+buildFileLocation+"/project.properties").getAbsoluteFile();
		Properties props = new Properties();
		props.load(new FileInputStream(projectPropertiesFile));			
		String bdaVersion = props.getProperty("bda.version");
		println bdaVersion;
		return bdaVersion;
	}

	void checkDBIntegration ()
	{
		def buildFileLocation=project.properties['master.build.location']		
		def targetName=project.properties['database.integration.target']
		def databaseType=project.properties['database.type']
		def basedir=project.properties['basedir']
		println basedir

		String installPropertiesFile=new File(basedir +"/"+buildFileLocation+"/install.properties").getAbsoluteFile();			
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
			String installFile = new File(basedir +"/"+buildFileLocation+"/install.xml").getAbsoluteFile()
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
		def installFileLocation=project.properties['master.install.location']
		def basedir=project.properties['basedir']
		println basedir
		String installerPropertyName;
		HashMap installerProperties;
		
		def antFile = new File(basedir +"/"+buildFileLocation+"/build.xml")

		def build = new Project()
		build.init()
		build.setProperty("project.name", "petstore");
		ProjectHelper.configureProject(build, antFile)	

		def buildProperties=build.properties

		buildProperties.each() { key, value -> 
			if ("${key}".contains('install.zip.file'))
				installerPropertyName = "${key}"
			
		};

		println installerPropertyName
		
		String installerFile = build.getProperty("dist.dir") + "/" + build.getProperty(installerPropertyName)		
		println installerFile
		ant.unzip(src: installerFile,dest:'working/installer' )

		String propertiesList = getListOfobfuscatedProperties()		
		
		if(propertiesList != null)
			installerProperties = getPropertyValuesList(propertiesList.substring(1,propertiesList.length() -1))	
			
		println "installerProperties::" + installerProperties
		

		def deployFile = new File(basedir +"/"+installFileLocation+"/build.xml")

		String installPropertiesFile=new File(basedir +"/"+installFileLocation+"/install.properties").getAbsoluteFile();
		Properties props = new Properties();
		props.load(new FileInputStream(installPropertiesFile));			

		if(installerProperties != null)
		{
			Set entries = installerProperties.entrySet();
			Iterator it = entries.iterator();
			while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
				System.out.println(entry.getKey() + "-->" + entry.getValue());
				props.setProperty(entry.getKey(), entry.getValue());

			}
		}
		props.store(new FileOutputStream(installPropertiesFile),null);		
	}	
	
	
	def getListOfobfuscatedProperties ()	
	{
		def buildFileLocation=project.properties['master.build.location']
		def basedir=project.properties['basedir']
		println basedir
		
		def antFile = new File(basedir +"/"+buildFileLocation+"/build.xml")

		def installProject = new XmlParser().parse(antFile)

		if(installProject.target.find{it.'@name'=='dist:installer'}.'obfuscate-properties-file')
		{
			return installProject.target.find{it.'@name'=='dist:installer'}.'obfuscate-properties-file'.'@required.property.list'
		}
	}
	
	def getPropertyValuesList (String propertiesList)	
	{
		HashMap obfuscatedProperties = new HashMap()
		def basedir=project.properties['basedir']
		println basedir		
		propertiesList.split(',').eachWithIndex {processToken, i -> 	
		if(checkValueFound(processToken))
		{
			println("processToken::" + processToken)
			obfuscatedProperties.put(processToken,defaultProperties.get(processToken))
			println("obfuscatedProperties::" + obfuscatedProperties.get(processToken))
		}
		else
		{		
			println "propertyKey::"+processToken
			String projectPropertiesFile=new File(basedir +"/"+project.properties['master.build.location']+"/install.properties").getAbsoluteFile();
			ant.property(file: projectPropertiesFile)			
			if(project.properties[processToken])
				obfuscatedProperties.put(processToken,project.properties[processToken])
			else
				ant.fail("COULD NOT FIND THE VALUE FOR THE KEY :"+propertyKey)			}
		}
		return obfuscatedProperties
	}
	
	
	def checkValueFound (String propertyKey)	
	{
		if(defaultProperties.get(propertyKey))
			return true
		else
			return false
	}
	
	def parseAndFormatDate (String propertiesList)
	{	
		def buildFileLocation=project.properties['master.build.location']
		def basedir=project.properties['basedir']
		println basedir
		String installFile = new File(basedir +"/"+ buildFileLocation+"/ciBuildLog.xml").getAbsoluteFile()
		StringBuffer wikiStr = new StringBuffer("'[");
		def revision = new XmlParser().parse(installFile)

		String ciStatusStr = revision.body.table[1].tr.td[1].'h1'.'img'.'@alt'
		String  buildTimeStr = revision.body.table[1].tr.td[1].'h1'.text()
		
		StringBuffer sb = new StringBuffer()		
		buildTimeStr.split('\\n').each {processToken  ->
						println "${processToken}"
						sb.append("${processToken}".trim())
					}					

		String dataStr = sb.substring(sb.indexOf('(')+1,sb.indexOf(')'))
		println dataStr	
		
		if (ciStatusStr=="[Success]")
		{
			wikiStr = wikiStr.append(this.WIKI_SUCCESSFUL+"|"+"http://"+project.properties['ci-server.hostname']+":48080/hudson/job/"+project.properties['ci-server.jobname']+"/lastBuild")
		}
		else
		{
			wikiStr = wikiStr.append(getStatusOnDate(dataStr)+"|"+"http://"+project.properties['ci-server.hostname']+":48080/hudson/job/"+project.properties['ci-server.jobname']+"/lastBuild")
		}		
		
	
		
		if (sb!= null)
			wikiStr = wikiStr.append("|"+sb+"]'")
		
		println wikiStr
		
		
		project.setProperty("certification.property.value",wikiStr.toString());
		//println "revision Again::"+ revision.depthFirst().grep{ it.'h1' }.'h1'.'img'.'@alt'
	}
	
	
	def getStatusOnDate (String dataStr)
	{
		def dtConverted = new Date(dataStr);		
		def today= new Date()
		def yesterday= today - this.CI_BUILD_DAYS		
		if (dtConverted.compareTo(yesterday) > 0)
			return this.WIKI_MODERATE
		else
			return this.WIKI_FAILED

	}
}