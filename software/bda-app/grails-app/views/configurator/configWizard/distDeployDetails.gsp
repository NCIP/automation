<html>
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="layout" content="main" />
	<gui:resources components="['expandablePanel','toolTip','tabView']"/>
        <title>Build Details</title>
    </head>
    <body>
    <gui:tabView id="tabView">
    <gui:tab id='jbossContainers' label='JBoss Container Dist/Deploy Info' active="true">
	<g:if test="${flash.message}">
		<div class="errors">
			${flash.message}
		</div>
	</g:if>
	<g:form action="configWizard">
		<gui:expandablePanel title="JBoss Prefix 1" expanded="true">
			<gui:toolTip text="This is the Sub-Project that will be deploying to athis JBoss container.">
				<br>Sub-Project Prefix &nbsp;<g:select name="jboss1SubProject" from="${['spPrefix1', 'spPrefix2']}" />
			</gui:toolTip>
			<gui:toolTip text="Is a grid service going to be deployed in this container.">
				<br>Grid Service Installed &nbsp;<g:select name="jboss1Grid" from="${['yes', 'no']}" />
			</gui:toolTip>
			<gui:toolTip text="This is the relative dir from the distribution root where the build artifacts can be found.">
				<form:textField label="Artifact Relative Dir" name="jboss1ArtifactDir"/>
			</gui:toolTip>
			<gui:toolTip text="The pattern (in ant style) to find artifact files under the above dir.">
				<form:textField label="Artifact Pattern" name="jboss1ArtifactPattern" value="**/*" />
			</gui:toolTip>
		</gui:expandablePanel>
		<gui:expandablePanel title="JBoss Prefix 2" expanded="true">
			<gui:toolTip text="This is the Sub-Project that will be deploying to athis JBoss container.">
				<br>Sub-Project Prefix &nbsp;<g:select name="jboss2SubProject" from="${['spPrefix1', 'spPrefix2']}" />
			</gui:toolTip>
			<gui:toolTip text="Is a grid service going to be deployed in this container.">
				<br>Grid Service Installed &nbsp;<g:select name="jboss2Grid" from="${['yes', 'no']}" />
			</gui:toolTip>
			<gui:toolTip text="This is the relative dir from the distribution root where the build artifacts can be found.">
				<form:textField label="Artifact Relative Dir" name="jboss2ArtifactDir"/>
			</gui:toolTip>
			<gui:toolTip text="The pattern (in ant style) to find artifact files under the above dir.">
				<form:textField label="Artifact Pattern" name="jboss2ArtifactPattern" value="**/*" />
			</gui:toolTip>
		</gui:expandablePanel>
		<!--
		<form:buttonBar>
			<g:submitButton name="previous" value="Previous"></g:submitButton>
			<g:submitButton name="next" value="Next"></g:submitButton>
			<g:submitButton name="cancel" value="Cancel"></g:submitButton>
		</form:buttonBar>
		-->
	</g:form>
    </gui:tab>
    <gui:tab id='TomcatContainers' label='Tomcat Container Dist/Deploy Info'>
	<g:if test="${flash.message}">
		<div class="errors">
			${flash.message}
		</div>
	</g:if>
	<g:form action="configWizard">
		<gui:expandablePanel title="Tomcat Prefix 1" expanded="true">
			<gui:toolTip text="This is the Sub-Project that will be deploying to athis Tomcat container.">
				<br>Sub-Project Prefix &nbsp;<g:select name="tomcat1SubProject" from="${['spPrefix1', 'spPrefix2']}" />
			</gui:toolTip>
			<gui:toolTip text="Is a grid service going to be deployed in this container.">
				<br>Grid Service Installed &nbsp;<g:select name="tomcat1Grid" from="${['yes', 'no']}" />
			</gui:toolTip>
			<gui:toolTip text="This is the relative dir from the distribution root where the build artifacts can be found.">
				<form:textField label="Artifact Relative Dir" name="tomcat1ArtifactDir"/>
			</gui:toolTip>
			<gui:toolTip text="The pattern (in ant style) to find artifact files under the above dir.">
				<form:textField label="Artifact Pattern" name="tomcat1ArtifactPattern" value="**/*" />
			</gui:toolTip>
		</gui:expandablePanel>
		<gui:expandablePanel title="Tomcat Prefix 2" expanded="true">
			<gui:toolTip text="This is the Sub-Project that will be deploying to athis Tomcat container.">
				<br>Sub-Project Prefix &nbsp;<g:select name="tomcat2SubProject" from="${['spPrefix1', 'spPrefix2']}" />
			</gui:toolTip>
			<gui:toolTip text="Is a grid service going to be deployed in this container.">
				<br>Grid Service Installed &nbsp;<g:select name="tomcat2Grid" from="${['yes', 'no']}" />
			</gui:toolTip>
			<gui:toolTip text="This is the relative dir from the distribution root where the build artifacts can be found.">
				<form:textField label="Artifact Relative Dir" name="tomcat2ArtifactDir"/>
			</gui:toolTip>
			<gui:toolTip text="The pattern (in ant style) to find artifact files under the above dir.">
				<form:textField label="Artifact Pattern" name="tomcat2ArtifactPattern" value="**/*" />
			</gui:toolTip>
		</gui:expandablePanel>
		<!--
		<form:buttonBar>
			<g:submitButton name="previous" value="Previous"></g:submitButton>
			<g:submitButton name="next" value="Next"></g:submitButton>
			<g:submitButton name="cancel" value="Cancel"></g:submitButton>
		</form:buttonBar>
		-->
	</g:form>
    </gui:tab>
    <gui:tab id='customContainers' label='Custom Container Dist/Deploy Info' >
	<g:if test="${flash.message}">
		<div class="errors">
			${flash.message}
		</div>
	</g:if>
	<g:form action="configWizard">
		<gui:expandablePanel title="Custom Prefix 1" expanded="true">
			<gui:toolTip text="This is the Sub-Project that will be deploying to athis Custom container.">
				<br>Sub-Project Prefix &nbsp;<g:select name="custom1SubProject" from="${['spPrefix1', 'spPrefix2']}" />
			</gui:toolTip>
			<gui:toolTip text="Is a grid service going to be deployed in this container.">
				<br>Grid Service Installed &nbsp;<g:select name="custom1Grid" from="${['yes', 'no']}" />
			</gui:toolTip>
			<gui:toolTip text="This is the relative dir from the distribution root where the build artifacts can be found.">
				<form:textField label="Artifact Relative Dir" name="custom1ArtifactDir"/>
			</gui:toolTip>
			<gui:toolTip text="The pattern (in ant style) to find artifact files under the above dir.">
				<form:textField label="Artifact Pattern" name="custom1ArtifactPattern" value="**/*" />
			</gui:toolTip>
		</gui:expandablePanel>
		<gui:expandablePanel title="Custom Prefix 2" expanded="true">
			<gui:toolTip text="This is the Sub-Project that will be deploying to athis Custom container.">
				<br>Sub-Project Prefix &nbsp;<g:select name="custom2SubProject" from="${['spPrefix1', 'spPrefix2']}" />
			</gui:toolTip>
			<gui:toolTip text="Is a grid service going to be deployed in this container.">
				<br>Grid Service Installed &nbsp;<g:select name="custom2Grid" from="${['yes', 'no']}" />
			</gui:toolTip>
			<gui:toolTip text="This is the relative dir from the distribution root where the build artifacts can be found.">
				<form:textField label="Artifact Relative Dir" name="custom2ArtifactDir"/>
			</gui:toolTip>
			<gui:toolTip text="The pattern (in ant style) to find artifact files under the above dir.">
				<form:textField label="Artifact Pattern" name="custom2ArtifactPattern" value="**/*" />
			</gui:toolTip>
		</gui:expandablePanel>
		<!--
		<form:buttonBar>
			<g:submitButton name="previous" value="Previous"></g:submitButton>
			<g:submitButton name="next" value="Next"></g:submitButton>
			<g:submitButton name="cancel" value="Cancel"></g:submitButton>
		</form:buttonBar>
		-->
	</g:form>
    </gui:tab>
    <gui:tab id='dbContainer' label='Database Container Dist/Deploy Info'>
	<g:if test="${flash.message}">
		<div class="errors">
			${flash.message}
		</div>
	</g:if>
	<g:form action="configWizard">
		<gui:expandablePanel title="Database Prefix 1" expanded="true">
			<gui:toolTip text="This is the Sub-Project that will be deploying to athis Database container.">
				<br>Sub-Project Prefix &nbsp;<g:select name="db1SubProject" from="${['spPrefix1', 'spPrefix2']}" />
			</gui:toolTip>
			<gui:toolTip text="The directory within your project where the database install files exist. Under this directory should be a folder for each DB Type you support with all files for that type directly under it.">
				<form:textField label="Database Install Source Dir" name="db1InstallDir"/>
			</gui:toolTip>
			<gui:toolTip text="The directory within your project where the database upgrade files exist. Under this directory should be a folder for each DB Type you support with all files for that type directly under it.">
				<form:textField label="Database Upgrade Source Dir" name="db1UpgradeDir"/>
			</gui:toolTip>
		</gui:expandablePanel>
		<gui:expandablePanel title="Database Prefix 2" expanded="true">
			<gui:toolTip text="This is the Sub-Project that will be deploying to athis Database container.">
				<br>Sub-Project Prefix &nbsp;<g:select name="db2SubProject" from="${['spPrefix1', 'spPrefix2']}" />
			</gui:toolTip>
			<gui:toolTip text="The directory within your project where the database install files exist. Under this directory should be a folder for each DB Type you support with all files for that type directly under it.">
				<form:textField label="Database Install Source Dir" name="db2InstallDir"/>
			</gui:toolTip>
			<gui:toolTip text="The directory within your project where the database upgrade files exist. Under this directory should be a folder for each DB Type you support with all files for that type directly under it.">
				<form:textField label="Database Upgrade Source Dir" name="db2UpgradeDir"/>
			</gui:toolTip>
		</gui:expandablePanel>
		<!--
		<form:buttonBar>
			<g:submitButton name="previous" value="Previous"></g:submitButton>
			<g:submitButton name="next" value="Next"></g:submitButton>
			<g:submitButton name="cancel" value="Cancel"></g:submitButton>
		</form:buttonBar>
		-->
	</g:form>
    </gui:tab>
    <gui:tab id='generalContainer' label='General Container Dist/Deploy Info'>
	<g:if test="${flash.message}">
		<div class="errors">
			${flash.message}
		</div>
	</g:if>
	<g:form action="configWizard">
		<gui:expandablePanel title="Distribution/Deploy Type" expanded="true">
			<table>
				<tr><td><em>Type</em></td><td><em>Description</em></td></tr>
				<tr>
					<td>Container<td>
					<td>This will create multiple distributions, one for each container including all the files that should be included in the distrubtion.  The deploy will then copy this distribution it's server and install it there.</td>
				</tr>
				</tr>
					<td>Complete<td>
					<td>This will create a single distribution, with all files from all containers and then copy it to one machine to execute the install.</td>
				</tr>
			</table>

			<gui:toolTip text="See Table above.">
				<br>Distribution/Deploy Type &nbsp;<g:select name="db1SubProject" from="${['container', 'complete']}" />
			</gui:toolTip>
		</gui:expandablePanel>
		<gui:expandablePanel title="Distribution/Deploy Variables" expanded="true">
			<gui:toolTip text="Insert the list of properties you would like to be replaced with &quot;REPLACE_ME&quot; in the distribution for use by cancer centers. This will cause the build to fail if they are not edited. Use commas to separate variables.">
				<form:textField label="Install Required Property List" name="installRequiredPropertyList"/>
			</gui:toolTip>
			<gui:toolTip text="Insert the list of properties you would like to be replaced with &quot;replace_me&quot; in the distribution for use by cancer centers. This will cause warnings if these are set. Use commas to separate variables.">
				<form:textField label="Install Optional Property List" name="installOptionalPropertyList"/>
			</gui:toolTip>
		</gui:expandablePanel>
		<form:buttonBar>
			<g:submitButton name="previous" value="Previous"></g:submitButton>
			<g:submitButton name="next" value="Next"></g:submitButton>
			<g:submitButton name="cancel" value="Cancel"></g:submitButton>
		</form:buttonBar>
	</g:form>
    </gui:tab>
    </gui:tabView>
    </body>
</html>
