    <gui:tabView id="${varPrefix}Tab">
    <gui:tab id='${varPrefix}Reader' label='Tomcat Prefix Reader' active="true">
	<gui:expandablePanel title="Tomcat Prefix Reader" expanded="true">
	<p>The Tomcat Readers read the standard BDA properties from the existing installation during an upgrade.  If you enable this plugin will try to read all standard tomcat properties from the existing installation and failure to read values can cause build failures.  This was meant for use by users running the upgrade at cancer centers, it allows them less properties to enter and manage from the command line installer.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}Reader']" />
	</gui:expandablePanel>
    </gui:tab>
    <%--
    <gui:tab id='${varPrefix}generic' label='Tomcat Prefix generic'>
	<gui:expandablePanel title="Tomcat Prefix generic" expanded="true">
	<p></p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}generic']" />
	</gui:expandablePanel>
    </gui:tab>
    --%>
    <gui:tab id='${varPrefix}PreValidation' label='Tomcat Prefix Pre-Validation'>
	<gui:expandablePanel title="Tomcat Prefix Pre-Validation" expanded="true">
	<p>This plugin will run serveral checks including stopping the server and verifying the ports are down.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}PreValiation']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}Binaries' label='Tomcat Prefix Install Binaries'>
	<gui:expandablePanel title="Tomcat Prefix Install Binaries" expanded="true">
	<p>This plugin will install tomcat binaries.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}Binaries']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}Ports' label='Tomcat Prefix Configure Ports'>
	<gui:expandablePanel title="Tomcat Prefix Configure Ports" expanded="true">
	<p>This plugin will configure the ports used by Tomcat based on properties in your tier property file.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}Ports']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}DataSource' label='Tomcat Prefix Configure Data Source'>
	<gui:expandablePanel title="Tomcat Prefix Configure Data Source" expanded="true">
	<p>This plugin will configure a data source in Tomcat's server.xml</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}DataSource']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}SSL' label='Tomcat Prefix Configure SSL'>
	<gui:expandablePanel title="Tomcat Prefix Configure SSL" expanded="true">
	<p>This plugin will add the option of configure a SSL port on your Tomcat Server.  The tier properties control whether this done on any given install.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}SSL']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}GridSSL' label='Tomcat Prefix Configure Grid SSL'>
	<gui:expandablePanel title="Tomcat Prefix Configure Grid SSL" expanded="true">
	<p>This plugin gives you the option to add an additional port that uses a customized version of SSL for the Grid.  You have the option of disabling the http port.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}GridSSL']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}External' label='Tomcat Prefix Configure External Host Names'>
	<gui:expandablePanel title="Tomcat Prefix Configure External Host Names" expanded="true">
	<p>This plugin will configure External Host/Ports for your HTTP, HTTPS or GRIDSSL ports.  You use this if the URL you goto from the internet to your application is different then the host it runs on.  An example http://caarray.nci.nih.gov is your url your host is cbvapp-c1007.  You would configure caarray.nci.nih.gov as the external host name for HTTP.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}External']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}Access' label='Tomcat Prefix Configure Access Logs'>
	<gui:expandablePanel title="Tomcat Prefix Configure Access Logs" expanded="true">
	<p>This plugin will enable apache style access logs in Tomcat so you can see each access and it's http response code in a log file within the Tomcat installation.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}Access']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}WebApp' label='Tomcat Prefix Install/Configure WebApp'>
	<gui:expandablePanel title="Tomcat Prefix Install/Configure WebApp" expanded="true">
	<p>This plugin will call your build target to copy your artifacts to the Tomcat installation.</p>
	<g:render template="customPluginTemplate" model="['varPrefix':'${varPrefix}WebApp']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}GridApp' label='Tomcat Prefix Install/Configure Grid App'>
	<gui:expandablePanel title="Tomcat Prefix Install/Configure Grid App" expanded="true">
	<p>This plugin will call your build target to copy your artifacts to the Tomcat installation.</p>
	<gui:toolTip text="This is the directory in the distribution where your grid files are.">
		<form:textField label="Grid Distribution Relative Dir" name="${varPrefix}GridAppSrcDir"/>
	</gui:toolTip>
	<gui:toolTip text="The name of your grid artifact zip file">
		<form:textField label="Grid Artifact File" name="${varPrefix}GridAppArtifact"/>
	</gui:toolTip>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}GridApp']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}ChangeLog' label='Tomcat Prefix Create Change Log'>
	<gui:expandablePanel title="Tomcat Prefix Create Change Log" expanded="true">
	<p>This plugin will generate a change log between the old Tomcat install and the current one if you are doing and upgrade and setup properties to generate a change log.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}ChangeLog']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}Start' label='Tomcat Prefix Start'>
	<gui:expandablePanel title="Tomcat Prefix Start" expanded="true">
	<p>This plugin startups up Tomcat.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}Start']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}Post' label='Tomcat Prefix Post Install Validation'>
	<gui:expandablePanel title="Tomcat Prefix Post Install Validation" expanded="true">
	<p>This plugin validates the Tomcat installation and that the server has started sucessfully.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}Post']" />
	</gui:expandablePanel>
    </gui:tab>
    </gui:tabView>

