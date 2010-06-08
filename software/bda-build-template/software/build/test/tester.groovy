#!/usr/bin/env groovy

import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.Properties
import jva.io.*

/*
def sout = new StringBuffer()
def serr = new StringBuffer()

antTest1 = 'ant deploy:local:install'.execute()
antTest1.consumeProcessOutput(sout, serr)
antTest1.waitFor()
println "=====Standard Out======"
sout.toString().eachLine{println "\t" + it}
println "=====Standard Out======"
serr.toString().eachLine{println "\t" + it}
*/

def antFail = new AntBuilder()
try{
antFail.exec(
	outputProperty: "sout",
	errorProperty: "serr",
	resultProperty: "rc",
	failonerror: "true",
	dir: "/data/home/ssaksa/src/trunk/bda-build-template/software/build",
	executable: "ant"
	)
	{
		arg(line: "junktarget")
		env(key: "TESTENVVAR", value: "TESTENVVALUE")
	}
}
catch (e) {}
println "\n==== Standard Out ====" 
antFail.project.properties.sout.eachLine{println "\t" + it}
println "\n==== Standard Err ====" 
antFail.project.properties.serr.eachLine{println "\t" + it}
println "\n=== Return Code ====" 
antFail.project.properties.rc.eachLine{println "\t" + it}

def antPass = new AntBuilder()
antPass.exec(
	outputProperty: "sout",
	errorProperty: "serr",
	resultProperty: "rc",
	failonerror: "true",
	dir: "/data/home/ssaksa/src/trunk/bda-build-template/software/build",
	executable: "ant"
	)
	{
		arg(line: "deploy:local:install")
		env(key: "TESTENVVAR", value: "TESTENVVALUE")
	}
println "\n==== Standard Out ====" 
antPass.project.properties.sout.eachLine{println "\t" + it}
println "\n==== Standard Err ====" 
antPass.project.properties.serr.eachLine{println "\t" + it}
println "\n=== Return Code ====" 
antPass.project.properties.rc.eachLine{println "\t" + it}

