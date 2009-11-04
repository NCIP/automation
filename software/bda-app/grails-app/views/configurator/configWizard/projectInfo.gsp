<html>
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="layout" content="main" />
	<gui:resources components="['expandablePanel']"/>
        <title>Project Info</title>
    </head>
    <body>
	<g:form action="configWizard">
	<g:if test="${flash.message}">
		<div class="errors">
			${flash.message}
		</div>
	</g:if>
		<gui:expandablePanel title="Project Details" expanded="true">
			<form:textField label="Project Name"  name="projectName" title="TextField" readonly="false" />
			<form:textarea label="Project Description"  name="projectDesc" title="TextArea" readonly="false" />
			<form:textField label="Project PM Contact"  name="projectPMContact" title="TextField" readonly="false" />
			<form:textField label="Project Tech Contact"  name="projectTechContact" title="TextField" readonly="false" />
			<form:textField label="Project Web URL"  name="projectWebUrl" title="TextField" readonly="false" />
			<form:textField label="Project SVN Url"  name="projectSVNUrl" title="TextField" readonly="false" />
		</gui:expandablePanel>
		<gui:expandablePanel title="Sub-Projects Info" expanded="true">
			<form:textField label="Sub-project Prefix 1" name="subProjectPrefix1"/>
			<form:textField label="Sub-project Prefix 2" name="subProjectPrefix2"/>
			<form:textField label="Sub-project Prefix 3" name="subProjectPrefix3"/>
			<form:textField label="Sub-project Prefix 4" name="subProjectPrefix4"/>
			<form:textField label="Sub-project Prefix 5" name="subProjectPrefix5"/>
		</gui:expandablePanel>
		<gui:expandablePanel title="Architecture Info" expanded="true">
			<h1>JBoss Info</h1>
			<table>
				<tr>
					<td><form:textField label="JBoss Prefix 1" name="jbossPrefix1"/></td>
					<td><form:textField label="JBoss Version 1" name="jbossVersion1"/></td>
				</tr>
				<tr>
					<td><form:textField label="JBoss Prefix 2" name="jbossPrefix2"/></td>
					<td><form:textField label="JBoss Version 2" name="jbossVersion2"/></td>
				</tr>
			</table>

			<h1>Tomcat Info</h1>
			<table>
				<tr>
					<td><form:textField label="Tomcat Prefix 1" name="tomcatPrefix1"/></td>
					<td><form:textField label="Tomcat Version 1" name="tomcatVersion1"/></td>
				</tr>
				<tr>
					<td><form:textField label="Tomcat Prefix 2" name="tomcatPrefix2"/></td>
					<td><form:textField label="Tomcat Version 2" name="tomcatVersion2"/></td>
				</tr>
			</table>
			<h1>Database Info</h1>
			<table>
				<tr>
					<td><form:textField label="Database Prefix 1" name="dbPrefix1"/></td>
					<td>Supported DB Types<g:select name="dbTypes1" from="${['mysql', 'postgres', 'oracle']}" multiple="true"/><td> 
				</tr>
				<tr>
					<td><form:textField label="Database Prefix 2" name="dbPrefix2"/></td>
					<td>
						MySQL<g:checkBox name="dbMysql2" value="true" checked="true"/>
						PostgreSQL<g:checkBox name="dbPostgres2" value="true" checked="false" />
						Oracle<g:checkBox name="dbOraclel2" value="true" checked="false"/>
					</td>
				</tr>
			</table>

			<h1>Custom Info</h1>
			<table>
				<tr>
					<td><form:textField label="Custom Prefix 1" name="customPrefix1"/></td>
				</tr>
				<tr>
					<td><form:textField label="Custom Prefix 2" name="customPrefix2"/></td>
				</tr>
			</table>
		</gui:expandablePanel>
		<form:buttonBar>
			<g:submitButton name="next" value="Next"></g:submitButton>
			<g:submitButton name="cancel" value="Cancel"></g:submitButton>
		</form:buttonBar>
	</g:form>
    </body>
</html>
