<html>
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="layout" content="main" />
	<gui:resources components="['expandablePanel','toolTip','tabView']"/>
        <title>Build Details</title>
    </head>
    <body>
    <gui:tabView id="tabView">
    <gui:tab id='subProject1' label='Sub-Project 1' active="true">
	<g:if test="${flash.message}">
		<div class="errors">
			${flash.message}
		</div>
	</g:if>
	<g:form action="configWizard">
		<gui:expandablePanel title="Build Details" expanded="true">
		<table>
		<tr>
		<td>
			<br>Builder Type<g:select name="sp1BuildBuilder" from="${['ant', 'maven']}" />
			<gui:toolTip text="This is the relative dir from the project root tot to the base directory of your build area.">
				<form:textField label="Base Relative Dir" name="sp1BuildBaseDir"/>
			</gui:toolTip>
			<gui:toolTip text="This is your build file. It should exist in the Base Relative Dir.">
				<form:textField label="Build File" name="sp1BuildFile"/>
			</gui:toolTip>
			<gui:toolTip text="This is the target within your build file to build your code">
				<form:textField label="Build Target" name="sp1BuildTarget"/>
			</gui:toolTip>
		</td>
		<td>
			<h2>Property Maps</h2>
			<table>
				<tr><td>BDA Property</td><td>Sub-Project Property</td>
				<tr>
					<td><form:textField name="sp1BuildBDAProp1"/></td>
					<td><form:textField name="sp1BuildProp1"/></td>
				</tr>
				<tr>
					<td><form:textField name="sp1BuildBDAProp2"/></td>
					<td><form:textField name="sp1BuildProp2"/></td>
				</tr>
				<tr>
					<td><form:textField name="sp1BuildBDAProp3"/></td>
					<td><form:textField name="sp1BuildProp3"/></td>
				</tr>
			</table>
		</tr>
		</table>
		</gui:expandablePanel>
		<gui:expandablePanel title="Test Details" expanded="true">
		<table>
		<tr>
		<td>
			<br>Builder Type<g:select name="sp1TestBuilder" from="${['ant', 'maven']}" />
			<gui:toolTip text="This is the relative dir from the project root tot to the base directory of your build area.">
				<form:textField label="Base Relative Dir" name="sp1TestBaseDir"/>
			</gui:toolTip>
			<gui:toolTip text="This is your build file. It should exist in the Base Relative Dir.">
				<form:textField label="Build File" name="sp1TestFile"/>
			</gui:toolTip>
			<gui:toolTip text="This is the target within your build file to test your code">
				<form:textField label="Build Target" name="sp1TestTarget"/>
			</gui:toolTip>
		</td>
		<td>
			<h2>Property Maps</h2>
			<table>
				<tr><td>BDA Property</td><td>Sub-Project Property</td>
				<tr>
					<td><form:textField name="sp1TestBDAProp1"/></td>
					<td><form:textField name="sp1TestProp1"/></td>
				</tr>
				<tr>
					<td><form:textField name="sp1TestBDAProp2"/></td>
					<td><form:textField name="sp1TestProp2"/></td>
				</tr>
				<tr>
					<td><form:textField name="sp1TestBDAProp3"/></td>
					<td><form:textField name="sp1TestProp3"/></td>
				</tr>
			</table>
		</tr>
		</table>
		</gui:expandablePanel>
		<gui:expandablePanel title="Static-Analysis Details" expanded="true">
		<table>
		<tr>
		<td>
			<br>Builder Type<g:select name="sp1SABuilder1" from="${['ant', 'maven']}" />
			<gui:toolTip text="This is the relative dir from the project root tot to the base directory of your build area.">
				<form:textField label="Base Relative Dir" name="sp1SABaseDir"/>
			</gui:toolTip>
			<gui:toolTip text="This is your build file. It should exist in the Base Relative Dir.">
				<form:textField label="Build File" name="sp1SAFile"/>
			</gui:toolTip>
			<gui:toolTip text="This is the target within your build file to run static-analysis on your code">
				<form:textField label="Build Target" name="sp1TestTarget"/>
			</gui:toolTip>
		</td>
		<td>
			<h2>Property Maps</h2>
			<table>
				<tr><td>BDA Property</td><td>Sub-Project Property</td>
				<tr>
					<td><form:textField name="sp1SABDAProp1"/></td>
					<td><form:textField name="sp1SAProp1"/></td>
				</tr>
				<tr>
					<td><form:textField name="sp1SABDAProp2"/></td>
					<td><form:textField name="sp1SAProp2"/></td>
				</tr>
				<tr>
					<td><form:textField name="sp1SABDAProp3"/></td>
					<td><form:textField name="sp1SAProp3"/></td>
				</tr>
			</table>
		</tr>
		</table>
		</gui:expandablePanel>
		<!--
		<form:buttonBar>
			<g:submitButton name="next" value="Next"></g:submitButton>
			<g:submitButton name="cancel" value="Cancel"></g:submitButton>
		</form:buttonBar>
		-->
	</g:form>
    </gui:tab>
    <gui:tab id='subProject2' label='Sub-Project 2'>
	<g:form action="configWizard">
		<gui:expandablePanel title="Build Details" expanded="true">
		<table>
		<tr>
		<td>
			<br>Builder Type<g:select name="sp2BuildBuilder" from="${['ant', 'maven']}" />
			<gui:toolTip text="This is the relative dir from the project root tot to the base directory of your build area.">
				<form:textField label="Base Relative Dir" name="sp2BuildBaseDir"/>
			</gui:toolTip>
			<gui:toolTip text="This is your build file. It should exist in the Base Relative Dir.">
				<form:textField label="Build File" name="sp2BuildFile"/>
			</gui:toolTip>
			<gui:toolTip text="This is the target within your build file to build your code">
				<form:textField label="Build Target" name="sp2BuildTarget"/>
			</gui:toolTip>
		</td>
		<td>
			<h2>Property Maps</h2>
			<table>
				<tr><td>BDA Property</td><td>Sub-Project Property</td>
				<tr>
					<td><form:textField name="sp2BuildBDAProp1"/></td>
					<td><form:textField name="sp2BuildProp1"/></td>
				</tr>
				<tr>
					<td><form:textField name="sp2BuildBDAProp2"/></td>
					<td><form:textField name="sp2BuildProp2"/></td>
				</tr>
				<tr>
					<td><form:textField name="sp2BuildBDAProp3"/></td>
					<td><form:textField name="sp2BuildProp3"/></td>
				</tr>
			</table>
		</tr>
		</table>
		</gui:expandablePanel>
		<gui:expandablePanel title="Test Details" expanded="true">
		<table>
		<tr>
		<td>
			<br>Builder Type<g:select name="sp2TestBuilder" from="${['ant', 'maven']}" />
			<gui:toolTip text="This is the relative dir from the project root tot to the base directory of your build area.">
				<form:textField label="Base Relative Dir" name="sp2TestBaseDir"/>
			</gui:toolTip>
			<gui:toolTip text="This is your build file. It should exist in the Base Relative Dir.">
				<form:textField label="Build File" name="sp2TestFile"/>
			</gui:toolTip>
			<gui:toolTip text="This is the target within your build file to test your code">
				<form:textField label="Build Target" name="sp2TestTarget"/>
			</gui:toolTip>
		</td>
		<td>
			<h2>Property Maps</h2>
			<table>
				<tr><td>BDA Property</td><td>Sub-Project Property</td>
				<tr>
					<td><form:textField name="sp2TestBDAProp1"/></td>
					<td><form:textField name="sp2TestProp1"/></td>
				</tr>
				<tr>
					<td><form:textField name="sp2TestBDAProp2"/></td>
					<td><form:textField name="sp2TestProp2"/></td>
				</tr>
				<tr>
					<td><form:textField name="sp2TestBDAProp3"/></td>
					<td><form:textField name="sp2TestProp3"/></td>
				</tr>
			</table>
		</tr>
		</table>
		</gui:expandablePanel>
		<gui:expandablePanel title="Static-Analysis Details" expanded="true">
		<table>
		<tr>
		<td>
			<br>Builder Type<g:select name="sp2SABuilder1" from="${['ant', 'maven']}" />
			<gui:toolTip text="This is the relative dir from the project root tot to the base directory of your build area.">
				<form:textField label="Base Relative Dir" name="sp2SABaseDir"/>
			</gui:toolTip>
			<gui:toolTip text="This is your build file. It should exist in the Base Relative Dir.">
				<form:textField label="Build File" name="sp2SAFile"/>
			</gui:toolTip>
			<gui:toolTip text="This is the target within your build file to run static-analysis on your code">
				<form:textField label="Build Target" name="sp2TestTarget"/>
			</gui:toolTip>
		</td>
		<td>
			<h2>Property Maps</h2>
			<table>
				<tr><td>BDA Property</td><td>Sub-Project Property</td>
				<tr>
					<td><form:textField name="sp2SABDAProp1"/></td>
					<td><form:textField name="sp2SAProp1"/></td>
				</tr>
				<tr>
					<td><form:textField name="sp2SABDAProp2"/></td>
					<td><form:textField name="sp2SAProp2"/></td>
				</tr>
				<tr>
					<td><form:textField name="sp2SABDAProp3"/></td>
					<td><form:textField name="sp2SAProp3"/></td>
				</tr>
			</table>
		</tr>
		</table>
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
