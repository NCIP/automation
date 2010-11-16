    <gui:resources components="['expandablePanel','toolTip','tabView']"/>

    <gui:tabView id="${jbossPrefix}Tab">
    <gui:tab id='${jbossPrefix}Reader' label='JBoss ${prefixNum} Reader' active="true">
	<gui:expandablePanel title="JBoss ${prefixNum} Reader" expanded="true">
	<p>The Jboss Readers read the standard BDA properties from the existing installation during an upgrade.  If you enable this plugin will try to read all standard jboss properties from the existing installation and failure to read values can cause build failures.  This was meant for use by users running the upgrade at cancer centers, it allows them less properties to enter and manage from the command line installer.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}Reader']" />
	</gui:expandablePanel>
    </gui:tab>
    <%--
    <gui:tab id='${jbossPrefix}generic' label='JBoss ${prefixNum} generic'>
	<gui:expandablePanel title="JBoss ${prefixNum} generic" expanded="true">
	<p></p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}generic']" />
	</gui:expandablePanel>
    </gui:tab>
    --%>
    <gui:tab id='${jbossPrefix}PreValidation' label='JBoss ${prefixNum} Pre-Validation'>
	<gui:expandablePanel title="JBoss ${prefixNum} Pre-Validation" expanded="true">
	<p>This plugin will run serveral checks including stopping the server and verifying the ports are down.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}PreValiation']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}Binaries' label='JBoss ${prefixNum} Install Binaries'>
	<gui:expandablePanel title="JBoss ${prefixNum} Install Binaries" expanded="true">
	<p>This plugin will install jboss binaries.  If the file name from project.properties ends in .jar then it will install it as a jems installer. If it is a zip it will just unzip it.  If there is a Cumulative Patch (CP) configured it will be installed. </p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}Binaries']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}Bindings' label='JBoss ${prefixNum} Configure Bindings'>
	<gui:expandablePanel title="JBoss ${prefixNum} Configure Bindings" expanded="true">
	<p>This plugin will setup JBoss bindings file for this application which controls which ports are used by JBoss.  Properties in the tier properties files can affect the behavior of this plugin.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}Bindings']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}Login' label='JBoss ${prefixNum} Configure Login'>
	<gui:expandablePanel title="JBoss ${prefixNum} Configure Login" expanded="true">
	<p>This plugin will insert a configuration from a template file into the login-config.xml.  There will be two template files one for using db for authentication and one for LDAP (located in software/common/resource/jboss).  A property in the tier properties file determines which template is used. </p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}Login']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}Secure' label='JBoss ${prefixNum} Secure Web Consoles'>
	<gui:expandablePanel title="JBoss ${prefixNum} Secure Web Consoles" expanded="true">
	<p>This plugin will secure the JBoss JMX and Web Consoles which have no authentication after a default Install</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}Secure']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}Shutdown' label='JBoss ${prefixNum} Update Shutdown'>
	<gui:expandablePanel title="JBoss ${prefixNum} Update Shutdown" expanded="true">
	<p>This plugin will update the shutdown.jar which is used to shutdown jobs with the JNDI port you have chosen for your installation.  The default for this is 1099, we change this default to your chosen port so you can call shutdown with no arguments to shutdown.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}Shutdown']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}SSL' label='JBoss ${prefixNum} Configure SSL'>
	<gui:expandablePanel title="JBoss ${prefixNum} Configure SSL" expanded="true">
	<p>This plugin will add the option of configure a SSL port on your JBoss Server.  The tier properties control whether this done on any given install.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}SSL']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}GridSSL' label='JBoss ${prefixNum} Configure Grid SSL'>
	<gui:expandablePanel title="JBoss ${prefixNum} Configure Grid SSL" expanded="true">
	<p>This plugin gives you the option to add an additional port that uses a customized version of SSL for the Grid.  You have the option of disabling the http port.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}GridSSL']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}JavaOpts' label='JBoss ${prefixNum} Configure Java OPTS'>
	<gui:expandablePanel title="JBoss ${prefixNum} Configure Java OPTS" expanded="true">
	<p>This plugin can set the java_opts for JBoss based on your jboss.java.opts property.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}JavaOpts']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}External' label='JBoss ${prefixNum} Configure External Host Names'>
	<gui:expandablePanel title="JBoss ${prefixNum} Configure External Host Names" expanded="true">
	<p>This plugin will configure External Host/Ports for your HTTP, HTTPS or GRIDSSL ports.  You use this if the URL you goto from the internet to your application is different then the host it runs on.  An example http://caarray.nci.nih.gov is your url your host is cbvapp-c1007.  You would configure caarray.nci.nih.gov as the external host name for HTTP.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}External']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}Access' label='JBoss ${prefixNum} Configure Access Logs'>
	<gui:expandablePanel title="JBoss ${prefixNum} Configure Access Logs" expanded="true">
	<p>This plugin will enable apache style access logs in JBoss so you can see each access and it's http response code in a log file within the JBoss installation.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}Access']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}WebApp' label='JBoss ${prefixNum} Install/Configure WebApp'>
	<gui:expandablePanel title="JBoss ${prefixNum} Install/Configure WebApp" expanded="true">
	<p>This plugin will call your build target to copy your artifacts to the JBoss installation.</p>
	<g:render template="customPluginTemplate" model="['varPrefix':'${jbossPrefix}WebApp']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}GridApp' label='JBoss ${prefixNum} Install/Configure Grid App'>
	<gui:expandablePanel title="JBoss ${prefixNum} Install/Configure Grid App" expanded="true">
	<p>This plugin will call your build target to copy your artifacts to the JBoss installation.</p>
	<gui:toolTip text="This is the directory in the distribution where your grid files are.">
		<form:textField label="Grid Distribution Relative Dir" name="${jbossPrefix}GridAppSrcDir"/>
	</gui:toolTip>
	<gui:toolTip text="The name of your grid artifact zip file">
		<form:textField label="Grid Artifact File" name="${jbossPrefix}GridAppArtifact"/>
	</gui:toolTip>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}GridApp']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}ChangeLog' label='JBoss ${prefixNum} Create Change Log'>
	<gui:expandablePanel title="JBoss ${prefixNum} Create Change Log" expanded="true">
	<p>This plugin will generate a change log between the old JBoss install and the current one if you are doing and upgrade and setup properties to generate a change log.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}ChangeLog']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}Start' label='JBoss ${prefixNum} Start'>
	<gui:expandablePanel title="JBoss ${prefixNum} Start" expanded="true">
	<p>This plugin startups up JBoss.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}Start']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${jbossPrefix}Post' label='JBoss ${prefixNum} Post Install Validation'>
	<gui:expandablePanel title="JBoss ${prefixNum} Post Install Validation" expanded="true">
	<p>This plugin validates the JBoss installation and that the server has started sucessfully.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${jbossPrefix}Post']" />
	</gui:expandablePanel>
    </gui:tab>
    </gui:tabView>

