    <gui:tabView id="${varPrefix}Tab">
    <gui:tab id='${varPrefix}Reader' label='Database Reader' active="true">
	<gui:expandablePanel title="Database Reader" expanded="true">
	<p>The Database Readers read the standard BDA properties from the existing installation during an upgrade.  If you enable this plugin will try to read all standard database properties from the existing installation and failure to read values can cause build failures.  This was meant for use by users running the upgrade at cancer centers, it allows them less properties to enter and manage from the command line installer.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}Install']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}Install' label='Database Install' active="true">
	<gui:expandablePanel title="Database Install" expanded="true">
	<p>This plugin uses the BDA Database Install process.  When an install is run any eexisting Database will be removed and recreated.</p>
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}Install']" />
	</gui:expandablePanel>
    </gui:tab>
    <gui:tab id='${varPrefix}Reader' label='Database Upgrade' active="true">
	<gui:expandablePanel title="Database Upgrade" expanded="true">
	<p>This plugin uses the BDA Database Upgrade process. This process uses a tool liquibase that uses a xml file to determine the change log and maintians metadata tables in the database about which changes have been applied. 
	<g:render template="pluginTemplate" model="['varPrefix':'${varPrefix}Upgrade']" />
	</gui:expandablePanel>
    </gui:tab>
    </gui:tabView>
