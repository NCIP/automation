    <gui:tabView id="${varPrefix}Tab">
    <gui:tab id='${varPrefix}WebApp' label='Custom Prefix Install/Configure WebApp' active=true">
	<gui:expandablePanel title="Custom Prefix Install/Configure WebApp" expanded="true">
	<p>This plugin will manage the installation and configuration of your custom conatiner.</p>
	<g:render template="customPluginTemplate" model="['varPrefix':'${varPrefix}WebApp']" />
	</gui:expandablePanel>
    </gui:tab>
    </gui:tabView>
