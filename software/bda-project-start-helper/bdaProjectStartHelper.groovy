#!/usr/bin/env groovy

import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.Properties
import jva.io.*

public class bdaProjectStartHelper
{
	static excludeTargetPatternList = []
	static excludePropertyPatternList = []
	static projectSearchString = 'bda-blueprints'
	static projectReplaceString = ''
	static databaseTypeList = []
	static databasePreferred = ''
	static props = new Properties()
	static fileContentsBuffer = ""
	static projectSoftwareDir =""
	static templateDir = ""

	public static void main (String[] args)
	{
		readProperties()
		println "Property list ${props.toString()}"
		buildFilterLists()
		processXmlFile('build.xml')
		processXmlFile('install.xml')
	}

	private static void readProperties ()
	{
		def propertiesFile = new File('./helper.properties')
		def fileStream = new FileInputStream(propertiesFile)
		
		props.load(fileStream)
	}
		
	private static void buildFilterLists()
	{
		def useLdap=props.get('use.ldap')
		def useJboss=props.get('use.jboss')
		def useTomcat=props.get('use.tomcat')
		def useGrid=props.get('use.grid')
		def useGuiInstaller=props.get('use.gui-installer')
		def useMaven=props.get('use.maven')

		projectReplaceString=props.get('project.prefix')
		databaseTypeList=props.get('database.type.list').split(',')
		databasePreferred=props.get('database.preferred')
		templateDir=props.get('bda.template.dir')
		projectSoftwareDir=props.get('project.software.dir')

		if (useJboss != "true") 
		{
			excludeTargetPatternList << "jboss"
			excludePropertyPatternList << "jboss"
		}	
		if (useTomcat != "true") 
		{
			excludeTargetPatternList << "tomcat"
			excludePropertyPatternList << "tomcat"
		}	
		if (useGrid != "true") 
		{
			excludeTargetPatternList << "grid"
			excludePropertyPatternList << "grid"
		}	
		if (useMaven != "true") 
		{
			excludeTargetPatternList << "maven"
			excludePropertyPatternList << "maven"
		}	
		if (useGuiInstaller != "true") 
		{
			excludeTargetPatternList << "gui-installer"
			excludePropertyPatternList << "gui-installer"
		}	
	}

	private static void createBaseFilteredXmlFile (String fileName)
	{
		String fileContents = new File(templateDir + "/" + fileName).text

		/*
		def targetMatcher =  java.util.regex.Pattern.compile(/(<target.*?<\/target>)/, Pattern.DOTALL).matcher(fileContents)
		def targetMatches = targetMatcher.matches()

		targetMatcher.find()
		def fileContentsNew = targetMatcher.replaceAll("")	
		fileContentsBuffer = fileContentsNew
		*/

		def cleanFileBuffer =""
		def breakFound = false
		fileContents.eachLine
		{
			if (! breakFound)
			{
				if (it.matches(".*<target.*"))
				{
					breakFound =true
				} else
				{
					 cleanFileBuffer += it + "\n"
				}
			}
		}
		
		fileContentsBuffer = cleanFileBuffer + "\n</project>"
	}
 
	private static void appendFilteredTargets (String fileName)
	{
		String fileContents = new File(templateDir + "/" + fileName).text

		def targetMatcher =  java.util.regex.Pattern.compile(/(<target.*?<\/target>)/, Pattern.DOTALL).matcher(fileContents)

		//build ignorelist
		def targetExcList = []
		def targetIncList = []
		while(targetMatcher.find())
		{
			def targetText = targetMatcher.group(1)
			//println targetText
			
			def targetNamePattern="<target\\s+name=\"([^\"]+)"
			def targetNameMatcher =java.util.regex.Pattern.compile(targetNamePattern, Pattern.DOTALL).matcher(targetText)
			targetNameMatcher.find()
			def targetName = targetNameMatcher.group(1)
			
			def excludeTarget = false
			excludeTargetPatternList.each
			{ excludePattern ->
				if (targetName =~ /${excludePattern}/)
				{
					excludeTarget = true
				//	println "\t${targetName} match ${excludePattern}"
				} else
				{
				//	println "\t${targetName} does not match ${excludePattern}"
				}
			}
			if (excludeTarget == true)
			{
				//println "Excluding ${targetName}"
				targetExcList << targetName
			} else
			{
			//	println "Including ${targetName}"
				targetIncList << targetName
			}
		}
		println "\nTargets Included - ${targetIncList}"
		println "\nTargets Excluded - ${targetExcList}"
		targetMatcher.reset()
		while(targetMatcher.find())
		{
			def targetText = targetMatcher.group(1)
			//println targetText
			
			def targetNamePattern="<target\\s+name=\"([^\"]+)"
			def targetNameMatcher =java.util.regex.Pattern.compile(targetNamePattern, Pattern.DOTALL).matcher(targetText)
			targetNameMatcher.find()
			def targetName = targetNameMatcher.group(1)

			if (!targetExcList.contains(targetName))
			{
				//println "before depend pattern ${targetName}"
				def targetDependPattern="depends=\"([^\"]+)\""
				def targetDependMatcher =java.util.regex.Pattern.compile(targetDependPattern, Pattern.DOTALL).matcher(targetText)
				if (targetDependMatcher.find())
				{
					def targetDependString = targetDependMatcher.group(1)
					//println "${targetName}\tdepends - ${targetDependString.trim()}"
					def newDepString="depends=\"\n"
					def depChanged = false
					targetDependString.split(',').each
					{ targetDep ->
						def dep = targetDep.trim()
						if (targetExcList.contains(dep))
						{
							depChanged=true
						} else
						{
							newDepString += "\t\t" + dep +",\n "
						}
					}
					newDepString = newDepString.replaceAll(/,\s*$/,"\n\t\t\"")
					//println newDepString
					if (depChanged)
					{
						// println "${targetName} existing depends ${targetDependString} changes to ${newDepString}"
						targetText = targetDependMatcher.replaceFirst(newDepString)
					} else
					{
						// println "${targetName} existing depends not changed"
					}
				} else
				{
					//println "${targetName} No depends"
				}
				//println targetText
				//println fileContentsBuffer
				def replaceString = Matcher.quoteReplacement("\n\t" + targetText + "\n</project>")
				fileContentsBuffer = fileContentsBuffer.replaceAll("</project>",replaceString)
				println "${fileName.toString()} ${targetName} included"
			} else
			{
				println "${fileName.toString()} ${targetName} EXCLUDED"
			}	
		}
		/*
		def cleanFileBuffer =""
		fileContentsBuffer.eachLine
		{
			if (!it.matches("^\\s+\$")) { cleanFileBuffer += it + "\n" }
		}
		*/
		
	}
	private static void filterPropertiesXmlFile ()
	{
		def newBuffer = ""
		fileContentsBuffer.eachLine
		{ line ->
			def exclude = false
			if (line.matches(".*<property.*") || 
				line.matches(".*<mkdir.*")||
				line.matches(".*<available.*")
				)
			{
				excludePropertyPatternList.each
				{ excludeProp ->
					if (line.contains(excludeProp))
					{
						exclude = true
					}
				}
				if (! exclude) { newBuffer += line + "\n"}
			} else
			{
				 newBuffer += line + "\n"
			}
		}
		fileContentsBuffer = newBuffer
	}

	private static void outputXmlFile (String fileName)
	{
		File outFile = new File(projectSoftwareDir + "/" + fileName)
		outFile.write(fileContentsBuffer)
	}
	private static void processXmlFile (String fileName)
	{
		createBaseFilteredXmlFile(fileName)
		appendFilteredTargets(fileName)
		filterPropertiesXmlFile()
		outputXmlFile(fileName)
	}
}
