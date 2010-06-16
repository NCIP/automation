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
	def log4jPattern="%d{ABSOLUTE} %-5p [%c{1}] %m%n"

	Logger log

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
			def i=0
			prop.rules.rule.each
			{ r ->
				def ruleName=r.name.text()
				def ruleDesc=r.description.text()
				def ruleFailMsg=r.'fail-message'.text()

				if(debug) println "\trule[${i}] -> " + r.name.text()
				i++
				// println "class-> " + r.conditions.getClass()
				def ruleCondition=antRecurseConditions(r.conditions.'*', "")
				if(debug) println "\t\tCondition -> " + ruleCondition
				if(debug) println prop.@name + "\t" + antProps[prop.@name] + "\t" + antProps[prop.@name].getClass()
				def result=antEvaluateRule(propertyName, ruleCondition )
				if (result)
				{
					if(debug) println "Rule ${ruleName} for property ${propertyName} passed."
				} else
				{
					if(debug) println "Property - ${propertyName}\tRule - ${ruleName} FAILED."
					if(debug) println "\t" + ruleFailMsg
					failureMessages = failureMessages + "== Property [${propertyName}]  Rule [${ruleName}] failed.\n" + ruleFailMsg
				}
			}
			o++
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
				tempCondString = tempCondString + "(" + antRecurseConditions(c.'*', " || ") + ")"
				// println "retrun or"
						
			} else if (condType == "and" )
			{
				// println "in and"
				tempCondString = tempCondString + "(" + antRecurseConditions(c.'*', " && ") + ")"
				// println "retrun and"
			} else if (condType == "condition")
			{
				// println "in condition"
				// println "\t\tcondition - > " + c.text()
				// println "${childCount}\t${cnt}"

				def condConv=antStripDot(c.text())

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
		def propertyNameConv = antStripDot(propertyName)
		binding.setVariable(propertyNameConv, antProps[propertyName]);
		
		if (shell.evaluate(ruleCondition))
		{
			return true
		} else
		{
			return false
		}
	}
	private antStripDot(String convStr)
	{
		def splitArray=convStr.split("\\s+")
		splitArray[0]=splitArray[0].replaceAll("\\.", "")
		return splitArray.join(" ");
	}
}
def rulesFileLocation=new File(args[0]).getAbsoluteFile()
def prv = new propertyRuleValidator(properties, rulesFileLocation)
//prv.testAntEvaluate()
//prv.antTestReadXML()
def failMsgs=prv.antEvaluateAntProperties()
//println "====== Property Validation Errors ======"
//println failMsgs
properties["property.rule.validator.failed"]="true"
properties["property.rule.validator.msg"]=failMsgs

