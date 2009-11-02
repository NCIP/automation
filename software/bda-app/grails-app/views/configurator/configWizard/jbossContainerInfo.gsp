<html>
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="layout" content="main" />
	<gui:resources components="['expandablePanel','toolTip','tabView']"/>
        <title>Build Details</title>
    </head>
    <body>
    <gui:tabView id="tabView">
    <gui:tab id='jboss1' label='JBoss Prefix 1' active="true">
	<g:if test="${flash.message}">
		<div class="errors">
			${flash.message}
		</div>
	</g:if>
	<g:form action="configWizard">
		<gui:expandablePanel title="JBoss Initialization" expanded="true">
			<h1>JBoss Reader Plugin</h1>
			<p>The Jboss Readers read the standard BDA properties from the existing installation during an upgrade.  If you enable this plugin will try to read all standard jboss properties from the existing installation and failure to read values can cause build failures.  This was meant for use by users running the upgrade at cancer centers, it allows them less properties to enter and manage from the command line installer.</p>
			<gui:toolTip text="Select yes if you want to disable this plugin.">
				<br>Disable<g:select name="jboss1Disable" from="${['yes', 'no']}" value="no" />
                        </gui:toolTip>
			<gui:toolTip text="Select yes if you want to Override this plugin. You will be prompted for info about what target to run as an alternate.">
				<br>Override<g:select name="jboss1Override" from="${['yes', 'no']}" value="no" />
                        </gui:toolTip>
			<gui:toolTip text="Select yes if you want to run a target before you execute this plugin.">
				<br>Pre-Execution Target<g:select name="jboss1Pre" from="${['yes', 'no']}" value="no" />
                        </gui:toolTip>
			<gui:toolTip text="Select yes if you want to run a target after you execute this plugin.">
				<br>Post-Execution Target<g:select name="jboss1Post" from="${['yes', 'no']}" value="no" />
                        </gui:toolTip>
			<gui:expandablePanel title="JBoss Reader Override" bounce="false" expanded="false">
				<table>
				<tr>
				<td>
					<br>Builder Type<g:select name="jboss1ReaderOverrideBuilderType" from="${['ant', 'maven']}" />
					<gui:toolTip text="This is the relative dir from the project root tot to the base directory of your build area.">
						<form:textField label="Base Relative Dir" name="jboss1ReaderOverrideBaseDir"/>
					</gui:toolTip>
					<gui:toolTip text="This is your build file. It should exist in the Base Relative Dir.">
						<form:textField label="Build File" name="jboss1ReaderOverrideFile"/>
					</gui:toolTip>
					<gui:toolTip text="This is the target within your build file to build your code">
						<form:textField label="Build Target" name="jboss1ReaderOverrideTarget"/>
					</gui:toolTip>
				</td>
				<td>
					<h2>Property Maps</h2>
					<table>
						<tr><td>BDA Property</td><td>Sub-Project Property</td>
						<tr>
							<td><form:textField name="jboss1ReaderOverrideBDAProp1"/></td>
							<td><form:textField name="jboss1ReaderOverrideProp1"/></td>
						</tr>
						<tr>
							<td><form:textField name="jboss1ReaderOverrideBDAProp2"/></td>
							<td><form:textField name="jboss1ReaderOverrideProp2"/></td>
						</tr>
						<tr>
							<td><form:textField name="jboss1ReaderOverrideBDAProp3"/></td>
							<td><form:textField name="jboss1ReaderOverrideProp3"/></td>
						</tr>
					</table>
				</tr>
				</table>
			</gui:expandablePanel>
			<gui:expandablePanel title="JBoss Reader Pre-execution Target " bounce="false" expanded="false">
				<table>
				<tr>
				<td>
					<br>Builder Type<g:select name="jboss1ReaderPreBuilderType" from="${['ant', 'maven']}" />
					<gui:toolTip text="This is the relative dir from the project root tot to the base directory of your build area.">
						<form:textField label="Base Relative Dir" name="jboss1ReaderPreBaseDir"/>
					</gui:toolTip>
					<gui:toolTip text="This is your build file. It should exist in the Base Relative Dir.">
						<form:textField label="Build File" name="jboss1ReaderPreFile"/>
					</gui:toolTip>
					<gui:toolTip text="This is the target within your build file to build your code">
						<form:textField label="Build Target" name="jboss1ReaderPreTarget"/>
					</gui:toolTip>
				</td>
				<td>
					<h2>Property Maps</h2>
					<table>
						<tr><td>BDA Property</td><td>Sub-Project Property</td>
						<tr>
							<td><form:textField name="jboss1ReaderPreBDAProp1"/></td>
							<td><form:textField name="jboss1ReaderPreProp1"/></td>
						</tr>
						<tr>
							<td><form:textField name="jboss1ReaderPreBDAProp2"/></td>
							<td><form:textField name="jboss1ReaderPreProp2"/></td>
						</tr>
						<tr>
							<td><form:textField name="jboss1ReaderPreBDAProp3"/></td>
							<td><form:textField name="jboss1ReaderPreProp3"/></td>
						</tr>
					</table>
				</tr>
				</table>
			</gui:expandablePanel>
			<gui:expandablePanel title="JBoss Reader Post Execution Target " bounce="false" expanded="false">
				<table>
				<tr>
				<td>
					<br>Builder Type<g:select name="jboss1ReaderPostBuilderType" from="${['ant', 'maven']}" />
					<gui:toolTip text="This is the relative dir from the project root tot to the base directory of your build area.">
						<form:textField label="Base Relative Dir" name="jboss1ReaderPostBaseDir"/>
					</gui:toolTip>
					<gui:toolTip text="This is your build file. It should exist in the Base Relative Dir.">
						<form:textField label="Build File" name="jboss1ReaderPostFile"/>
					</gui:toolTip>
					<gui:toolTip text="This is the target within your build file to build your code">
						<form:textField label="Build Target" name="jboss1ReaderPostTarget"/>
					</gui:toolTip>
				</td>
				<td>
					<h2>Property Maps</h2>
					<table>
						<tr><td>BDA Property</td><td>Sub-Project Property</td>
						<tr>
							<td><form:textField name="jboss1ReaderPostBDAProp1"/></td>
							<td><form:textField name="jboss1ReaderPostProp1"/></td>
						</tr>
						<tr>
							<td><form:textField name="jboss1ReaderPostBDAProp2"/></td>
							<td><form:textField name="jboss1ReaderPostProp2"/></td>
						</tr>
						<tr>
							<td><form:textField name="jboss1ReaderPostBDAProp3"/></td>
							<td><form:textField name="jboss1ReaderPostProp3"/></td>
						</tr>
					</table>
				</tr>
				</table>
			</gui:expandablePanel>
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
