#!/usr/bin/env groovy

import java.util.regex.Matcher
import java.util.regex.Pattern

macroFile = new File(args[0])

macroXml = new XmlParser().parse(macroFile)

//println macroXml.{antlib:org.apache.tools.ant}macrodef.children()
//println macroXml[antns.macrodef].text()

//println macroXml.children()

macroXml.macrodef."@name".each
{ mName ->
	String attributeDoc=""
	String attributeCode=""

	//println mName
	//println "\t" + macroXml.macrodef.find{it.'@name'==mName}.attribute."@name"

	macroXml.macrodef.find{it.'@name'==mName}.attribute."@name".each
	{ attrName ->
		attrVal = macroXml.macrodef.find{it.'@name'==mName}.attribute.find{it.'@name'==attrName}."@default"
		attributeDoc += "${attrName} (${attrVal}) - \n"
		if (attrName.toString().length() == 0)
		{
			attributeCode +="\t\t${attrName}=\"somevalue\"\n"
		}
	}
		
	print """
h2. ${mName}

{anchor:${mName}}
*Macro Name*: *${mName}*
*Version available* \\- [0.x.0|https://gforge.nci.nih.gov/svnroot/commonlibrary/trunk/ivy-repo/ncicb/bda-utils/0.x.0/]
*File Name*: [https://gforge.nci.nih.gov/svnroot/automation/trunk/software/utils/bda-build-utils.xml]
*Description*:
*Attributes*:
${attributeDoc}
*Requirements*:
*Example usage*:
{code}
\t<${mName}
${attributeCode}
\t\t/>
{code}
"""
}


