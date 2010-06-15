#!/usr/bin/env groovy
import java.util.regex.Matcher
import java.util.regex.Pattern

class propertyRuleValidator
{
	def antProps
	def antPropsConv
	def failureMessages=""
	def debug=false
	public propertyRuleValidator(antPropsIn)
	{
		antProps=antPropsIn
		println antProps.getClass()
		//System.getProperties().sort{it.key}.each{println it.key +"\t"+ it.value}
		if (antProps['groovy.debug'])
		{
			debug=true;
			println "Debug Enabled..."
		}
		/*
		antProps.each
		{ k, v -> 
			println k.getClass()
			println v.getClass()
			def tmpKey = k.replaceAll('\\.','')
			println "debug"
			println tmpKey.getClass()
			antPropsConv[tmpKey]=v
			println "debug"
		}
		antPropsConv.sort{it.key}.each{println it}
		*/
	}
			
	public void testAntEvaluate ()
	{
		//antProps.sort{it.key}.each{println it}
		//System.getProperties().each{println it.key +"\t"+ it.value}
		println antProps["jboss.server.name"]
		Binding binding = new Binding();
		GroovyShell shell = new GroovyShell(binding);
		def antPattern1='jbossServerName ==~ /default/'
		if (antProps["jboss.server.name"] ==~ /[\w\d\-\_]+/) println "pass patterns should work"
		binding.setVariable("jbossServerName", new String(antProps["jboss.server.name"]));
		if (shell.evaluate(antPattern1)) println  "antPattern1 passed"
	}

	private static void testShellEvaluate ()
	{
		Binding binding = new Binding();
		GroovyShell shell = new GroovyShell(binding);
		binding.setVariable("vabc", new String("abc"));
		binding.setVariable("vdef", new String("def"));
		def passPattern1="vabc ==~ /.*a.*/ && vdef ==~ /.*d.*/"
		def passPattern2="vabc ==~ /.*b.*/ && vdef ==~ /.*e.*/"
		def failPattern1="vabc ==~ /.*d.*/ && vdef ==~ /.*g.*/"
		def failPattern2="vabc ==~ /.*d.*/ && vdef ==~ /.*d.*/"
		
		if ("abc" ==~ /.*a.*/) println "pass patterns should work"
		if (shell.evaluate(passPattern1)) println  "passPattern1 passed"
		if (shell.evaluate(passPattern2)) println  "passPattern2 passed"
		if (!shell.evaluate(failPattern1)) println  "failPattern1 failed"
		if (!shell.evaluate(failPattern2)) println  "failPattern2 failed"
	}
	private void antEvaluateAntProperties()
	{
		def file = new File("p.xml")
		def xml = new XmlParser().parse(file)

		/*
		println xml.getClass()
		println xml.methods.each{println it}
		println xml.name()
		println xml.attributes()
		println xml.children()
		println "xml.each => "
		def i=0
		xml.each
		{ prop->
			println "property[${i}] -> " + prop.@name
			i++
		}

		println xml.property[0].name()
		println xml.property[0].attributes()
		println xml.property[0].children()
		*/
		def o=0
		xml.property.each
		{ prop->
			println "property[${o}] -> " + prop.@name
			//println prop.rules.children()
			def propertyName=prop.@name
			def i=0
			prop.rules.rule.each
			{ r ->
				def ruleName=r.name.text()
				def ruleDesc=r.description.text()
				def ruleFailMsg=r.'fail-message'.text()

				println "\trule[${i}] -> " + r.name.text()
				i++
				if(debug) println "class-> " + r.conditions.getClass()
				def ruleCondition=antRecurseConditions(r.conditions.'*', "")
				println "\t\tCondition -> " + ruleCondition
				println prop.@name + "\t" + antProps[prop.@name] + "\t" + antProps[prop.@name].getClass()
				def result=antEvaluateRule(propertyName, ruleCondition )
				if (result)
				{
					println "Rule ${ruleName} for property ${propertyName} passed."
				} else
				{
					println "Rule ${ruleName} for property ${propertyName} FAILED."
					println "\t" + ruleFailMsg
					failureMessages = failureMessages + ruleFailMsg
				}
			}
			o++
		}
	}
	private  antRecurseConditions(groovy.util.NodeList curNode, String cond)
	{
		def tempCondString=""
		if(debug) println "in antRecurseConditions"
		def childCount=curNode.size()
		if(debug) println childCount
		def cnt=0
		curNode.each
		{ c ->
			cnt++
			if(debug) println "NodeName -> '" + c.name() + "'"
			def condType = c.name()
			if (condType == "or")
			{
				if(debug) println "in or"
				tempCondString = tempCondString + "(" + antRecurseConditions(c.'*', " || ") + ")"
				if(debug) println "retrun or"
						
			} else if (condType == "and" )
			{
				if(debug) println "in and"
				tempCondString = tempCondString + "(" + antRecurseConditions(c.'*', " && ") + ")"
				if(debug) println "retrun and"
			} else if (condType == "condition")
			{
				if(debug) println "in condition"
				/*
				println c.getClass()
				println c.name()
				println c.parent().name()
				println c.children()
				*/
				if(debug) println "\t\tcondition - > " + c.text()
				if(debug) println "${childCount}\t${cnt}"
				if (cnt < childCount)
				{
					tempCondString=tempCondString + c.text().replaceAll('\\.','') + cond
				} else
				{
					tempCondString=tempCondString + c.text().replaceAll('\\.','') 
				}
				if(debug) println "\t\ttempCondString -> " + tempCondString
				if(debug) println "retrun condition"
			
			} else
			{
				if(debug) println "Invalid condition syntax."
			}
		}
		return tempCondString 
		if(debug) println "outside of each"
	}
	private  antEvaluateRule(String propertyName, String ruleCondition)
	{
		Binding binding = new Binding();
		GroovyShell shell = new GroovyShell(binding);
		def propertyNameConv = propertyName.replaceAll('\\.','')
		binding.setVariable(propertyNameConv, antProps[propertyName]);
		
		if (shell.evaluate(ruleCondition))
		{
			return true
		} else
		{
			return false
		}
	}
}
def prv = new propertyRuleValidator(properties)
//prv.testAntEvaluate()
//prv.antTestReadXML()
prv.antEvaluateAntProperties()
