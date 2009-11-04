<html>
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="layout" content="main" />
	<gui:resources components="['expandablePanel','toolTip','tabView']"/>
        <title>Build Details</title>
    </head>
    <body>
    <g:form action="configWizard">
    <g:if test="${flash.message}">
	<div class="errors"> 
		${flash.message}        
	</div>          
    </g:if> 

    <gui:tabView id="tabView">
    <gui:tab id='jboss1Reader' label='JBoss Prefix Reader' active="true">
	<gui:expandablePanel title="JBoss Prefix Reader" expanded="true">
	<p>The Jboss Readers read the standard BDA properties from the existing installation during an upgrade.  If you enable this plugin will try to read all standard jboss properties from the existing installation and failure to read values can cause build failures.  This was meant for use by users running the upgrade at cancer centers, it allows them less properties to enter and manage from the command line installer.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1Reader']" />
	</gui:expandablePanel>
    </gui:tab>
    <%--
    <gui:tab id='jboss1generic' label='JBoss Prefix generic'>
	<gui:expandablePanel title="JBoss Prefix generic" expanded="true">
	<p></p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1generic']" />
	</gui:expandablePanel>
    </gui:tab>
    --%>
    <gui:tab id='jboss1PreValidation' label='JBoss Prefix Pre-Validation'>
	<gui:expandablePanel title="JBoss Prefix Pre-Validation" expanded="true">
	<p>This plugin will run serveral checks including stopping the server and verifying the ports are down.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1PreValiation']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1Binaries' label='JBoss Prefix Install Binaries'>
	<gui:expandablePanel title="JBoss Prefix Install Binaries" expanded="true">
	<p>This plugin will install jboss binaries.  If the file name from project.properties ends in .jar then it will install it as a jems installer. If it is a zip it will just unzip it.  If there is a Cumulative Patch (CP) configured it will be installed. </p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1Binaries']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1Bindings' label='JBoss Prefix Configure Bindings'>
	<gui:expandablePanel title="JBoss Prefix Configure Bindings" expanded="true">
	<p>This plugin will setup JBoss bindings file for this application which controls which ports are used by JBoss.  Properties in the tier properties files can affect the behavior of this plugin.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1Bindings']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1Login' label='JBoss Prefix Configure Login'>
	<gui:expandablePanel title="JBoss Prefix Configure Login" expanded="true">
	<p>This plugin will insert a configuration from a template file into the login-config.xml.  There will be two template files one for using db for authentication and one for LDAP (located in software/common/resource/jboss).  A property in the tier properties file determines which template is used. </p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1Login']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1Secure' label='JBoss Prefix Secure Web Consoles'>
	<gui:expandablePanel title="JBoss Prefix Secure Web Consoles" expanded="true">
	<p>This plugin will secure the JBoss JMX and Web Consoles which have no authentication after a default Install</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1Secure']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1Shutdown' label='JBoss Prefix Update Shutdown'>
	<gui:expandablePanel title="JBoss Prefix Update Shutdown" expanded="true">
	<p>This plugin will update the shutdown.jar which is used to shutdown jobs with the JNDI port you have chosen for your installation.  The default for this is 1099, we change this default to your chosen port so you can call shutdown with no arguments to shutdown.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1Shutdown']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1SSL' label='JBoss Prefix Configure SSL'>
	<gui:expandablePanel title="JBoss Prefix Configure SSL" expanded="true">
	<p>This plugin will add the option of configure a SSL port on your JBoss Server.  The tier properties control whether this done on any given install.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1SSL']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1GridSSL' label='JBoss Prefix Configure Grid SSL'>
	<gui:expandablePanel title="JBoss Prefix Configure Grid SSL" expanded="true">
	<p>This plugin gives you the option to add an additional port that uses a customized version of SSL for the Grid.  You have the option of disabling the http port.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1GridSSL']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1JavaOpts' label='JBoss Prefix Configure Java OPTS'>
	<gui:expandablePanel title="JBoss Prefix Configure Java OPTS" expanded="true">
	<p>This plugin can set the java_opts for JBoss based on your jboss.java.opts property.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1JavaOpts']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1External' label='JBoss Prefix Configure External Host Names'>
	<gui:expandablePanel title="JBoss Prefix Configure External Host Names" expanded="true">
	<p>This plugin will configure External Host/Ports for your HTTP, HTTPS or GRIDSSL ports.  You use this if the URL you goto from the internet to your application is different then the host it runs on.  An example http://caarray.nci.nih.gov is your url your host is cbvapp-c1007.  You would configure caarray.nci.nih.gov as the external host name for HTTP.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1External']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1Access' label='JBoss Prefix Configure Access Logs'>
	<gui:expandablePanel title="JBoss Prefix Configure Access Logs" expanded="true">
	<p>This plugin will enable apache style access logs in JBoss so you can see each access and it's http response code in a log file within the JBoss installation.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1Access']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1WebApp' label='JBoss Prefix Install/Configure WebApp'>
	<gui:expandablePanel title="JBoss Prefix Install/Configure WebApp" expanded="true">
	<p>This plugin will call your build target to copy your artifacts to the JBoss installation.</p>
	<g:render template="customPluginTemplate" model="['varPrefix':'jboss1WebApp']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1GridApp' label='JBoss Prefix Install/Configure Grid App'>
	<gui:expandablePanel title="JBoss Prefix Install/Configure Grid App" expanded="true">
	<p>This plugin will call your build target to copy your artifacts to the JBoss installation.</p>
	<gui:toolTip text="This is the directory in the distribution where your grid files are.">
		<form:textField label="Grid Distribution Relative Dir" name="jboss1GridAppSrcDir"/>
	</gui:toolTip>
	<gui:toolTip text="The name of your grid artifact zip file">
		<form:textField label="Grid Artifact File" name="jboss1GridAppArtifact"/>
	</gui:toolTip>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1GridApp']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1ChangeLog' label='JBoss Prefix Create Change Log'>
	<gui:expandablePanel title="JBoss Prefix Create Change Log" expanded="true">
	<p>This plugin will generate a change log between the old JBoss install and the current one if you are doing and upgrade and setup properties to generate a change log.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1ChangeLog']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1Start' label='JBoss Prefix Start'>
	<gui:expandablePanel title="JBoss Prefix Start" expanded="true">
	<p>This plugin startups up JBoss.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1Start']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='jboss1Post' label='JBoss Prefix Post Install Validation'>
	<gui:expandablePanel title="JBoss Prefix Post Install Validation" expanded="true">
	<p>This plugin validates the JBoss installation and that the server has started sucessfully.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'jboss1Post']" />
	</gui:expandablePanel>
    </gui:tab>
    </gui:tabView>
		<form:buttonBar>
			<g:submitButton name="previous" value="Previous"></g:submitButton>
			<g:submitButton name="next" value="Next"></g:submitButton>
			<g:submitButton name="cancel" value="Cancel"></g:submitButton>
		</form:buttonBar>
	</g:form>
    </body>
</html>
