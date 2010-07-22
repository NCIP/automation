#!/usr/bin/env groovy
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.apache.log4j.*

class propertyRuleValidator
{
	def antProps
	def failureMessages=""
	def debug=false
	def rulesFileLocation
	def ruleRefererncedPropList =[]
	def processingProperty
	//def log4jPattern="%d{ABSOLUTE} %-5p [%c{1}] %m%n"

	//Logger log

	public propertyRuleValidator(antPropsIn, File rulesFileLocationIn)
	{
		/*
		def pattern = new PatternLayout(log4jPattern)
		BasicConfigurator.configure(new ConsoleAppender(pattern))
		LogManager.rootLogger.level=Level.INFO
		log = Logger.getInstance(propertyRuleValidator.class)

		log.info "Test log4j"
		*/

		antProps=antPropsIn
		rulesFileLocation=rulesFileLocationIn
		//System.getProperties().sort{it.key}.each{println it.key +"\t"+ it.value}
		if (antProps['groovy.debug'])
		{
			debug=true;
			println "Debug Enabled..."
		}
	}
			
	private antEvaluateAntProperties()
	{
		//log.info "Entering antEvaluateAntProperties"
		def xml = new XmlParser().parse(rulesFileLocation)
		def o=0
		xml.property.each
		{ prop->
			if(debug) println "property[${o}] -> " + prop.@name
			def propertyName=prop.@name
			this.processingProperty=propertyName
			def i=0
			def skipProp=prop.@'skip-property'
			if (debug) println "skip-prop - (${skipProp}) ant - (${antProps[skipProp]})"
			if (skipProp.length() > 0 && antProps[skipProp] == "true")
			{
				println "Skipping property [${propertyName}] skip-prop (${skipProp}) set"
			}
			else
			{
			prop.rules.rule.each
			{ r ->
				def ruleName=r.name.text()
				def ruleDesc=r.description.text()
				def ruleFailMsg=r.'fail-message'.text()
				if(debug) println "\trule[${i}] -> " + r.name.text()
				i++
				// println "class-> " + r.conditions.getClass()
				def ruleCondition=antRecurseConditions(r.conditions.'*', "")
				// Cleanup trailing && and !! wow is this a mess
				def tmpArray=ruleCondition.split("\\)\\s+[\\&\\|]+\\s+\\)")
				def tmpStr=tmpArray[0];
				def leftMatcher = tmpStr =~ /\(/
				def rightMatcher = tmpStr =~ /\)/
				def leftCnt=0
				def rightCnt=0
				leftMatcher.each{leftCnt++}
				rightMatcher.each{rightCnt++}
				def missingRightCnt=leftCnt - rightCnt
				def ruleCondMod=tmpStr;
				if (missingRightCnt > 0){(1..missingRightCnt).each{ruleCondMod=ruleCondMod +")"}}

				if(debug) println "\t\tCondition -> " + ruleCondMod
				if(debug) println prop.@name + "\t" + antProps[prop.@name] + "\t" + antProps[prop.@name].getClass()
				println "Processing property [${propertyName}] rule [${ruleName}]."
				def result=antEvaluateRule(propertyName, ruleCondMod )
				if (result)
				{
					if(debug) println "Rule ${ruleName} for property ${propertyName} passed."
				} else
				{
					if(debug) println "Property - ${propertyName}\tRule - ${ruleName} FAILED."
					if(debug) println "\t" + ruleFailMsg
					failureMessages = failureMessages + "== Property [${propertyName}]  Rule [${ruleName}] Value [${antProps[propertyName]}] failed.\n" + ruleFailMsg + "\n"
				}
			}
			o++
			} // else
		}
		return failureMessages
	}
	private  antRecurseConditions(groovy.util.NodeList curNode, String cond)
	{
		def tempCondString=""
		// println "in antRecurseConditions"
		def childCount=curNode.size()
		// println childCount
		def cnt=0
		curNode.each
		{ c ->
			cnt++
			// println "NodeName -> '" + c.name() + "'"
			def condType = c.name()
			if (condType == "or")
			{
				// println "in or"
				tempCondString = tempCondString + "(" + antRecurseConditions(c.'*', " || ") + ")" + cond
				// println "retrun or"
						
			} else if (condType == "and" )
			{
				// println "in and"
				tempCondString = tempCondString + "(" + antRecurseConditions(c.'*', " && ") + ")" + cond
				// println "retrun and"
			} else if (condType == "condition")
			{
				// println "in condition"
				// println "\t\tcondition - > " + c.text()
				// println "${childCount}\t${cnt}"

				def condConv=antStripDot(c.text())
				if (c.@negate =="true")
				{
					condConv = "! ("+condConv+")"
				}

				if (cnt < childCount)
				{
					tempCondString=tempCondString + condConv + cond
				} else
				{
					tempCondString=tempCondString + condConv 
				}
				// println "\t\ttempCondString -> " + tempCondString
				// println "retrun condition"
			
			} else
			{
				// println "Invalid condition syntax."
			}
		}
		return tempCondString 
		// println "outside of each"
	}
	private  antEvaluateRule(String propertyName, String ruleCondition)
	{
		Binding binding = new Binding();
		GroovyShell shell = new GroovyShell(binding);
		antProps.each
		{ key, value ->
			//if(debug) println "binding - " + key
			def keyNameConv = antStripDot(key)
			binding.setVariable(keyNameConv, antProps[key]);
		}
		ruleRefererncedPropList.each
		{ key ->
			//if(debug) println "binding ref prop - " + key
			def keyNameConv = antStripDot(key)
			binding.setVariable(keyNameConv, "");
		}
			

		def propNameConv = antStripDot(propertyName)
		binding.setVariable(propNameConv, antProps[propertyName]);
		
		try
		{
			if (shell.evaluate(ruleCondition))
			{
				return true
			} else
			{
				return false
			}
		}
		catch (MissingPropertyException)
		{
			println "rule - (${ruleCondition}) failed with Missing Property Exception, one of the properties does not exist. You may want to contact the BDA team for assistance with resolving this issue."
			return false
		}
	}
	private antStripDot(String convStr)
	{
		def splitArray=convStr.split("\\s+")
		// This conditional captures all the properties that are referenced but not defined in antProps, must be done before dots and such are stripped from splitArray[0]
		if (splitArray[0] != processingProperty && ! ruleRefererncedPropList.contains(splitArray[0]) && ! antProps[splitArray[0]])
		{
			ruleRefererncedPropList.add(splitArray[0]);
		}

		// Strip dots and dashs from left hand side of equation
		splitArray[0]=splitArray[0].replaceAll("\\.", "")
		splitArray[0]=splitArray[0].replaceAll("-", "")
		if (splitArray.size() == 3 && splitArray[2] ==~ /\d+/)
		{
			splitArray[0]=splitArray[0] + ".toInteger()"
			if (debug) println "Compare 2 is  numeric - " + splitArray.join(" ")	
		}

		// If there is a property on the right hand side of the equation strip dots and dashes
		if (splitArray.size() == 3 && antProps[splitArray[2]] != null)
		{
			if (debug) println "Referenced prop on right (before)-> " + splitArray[2] + " " + antProps[splitArray[2]]
			splitArray[2]=splitArray[2].replaceAll("\\.", "")
			splitArray[2]=splitArray[2].replaceAll("-", "")
			if (debug) println "Referenced prop on right (after)-> " + splitArray[2]  + " " + antProps[splitArray[2]]
		}
		return splitArray.join(" ")
	}
}
def rulesFileLocation=new File(args[0]).getAbsoluteFile()
def prv = new propertyRuleValidator(properties, rulesFileLocation)

def failMsgs=prv.antEvaluateAntProperties()
if (properties["evaluate.property.name"] != null)
{
	def failProp=properties["evaluate.property.name"]
	println "evaluate.property.name - " + properties["evaluate.property.name"] + " (" + properties[failProp] + ") so only setting failure flag based on pass/fail results of this property."
	//println "failMsgs -\n" + failMsgs
	//println "failProp - " + failProp
	if( failMsgs.contains("[" + failProp + "]"))
	{
		println "Property ${failProp} failed validation."
		properties["property.rule.validator.failed"]="true"
		properties["property.rule.validator.msg"]=failMsgs
	} else
	{
		println "Property ${failProp} passed all validation rules."
	}
} else
{
	println "evaluate.property.name not set so evaluating all properties."
	
	if (failMsgs.length() > 0)
	{
		println "Some properties had property validation failures."
		properties["property.rule.validator.failed"]="true"
		properties["property.rule.validator.msg"]=failMsgs
	}
}