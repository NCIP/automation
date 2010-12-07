			<gui:toolTip text="Select yes if you want to Override this plugin. You will be prompted for info about what target to run as an alternate.">
				<br>Override<g:select name="${varPrefix}Override" from="${['yes', 'no']}" value="yes" />
                        </gui:toolTip>
			<gui:toolTip text="Select yes if you want to run a target before you execute this plugin.">
				<br>Pre-Execution Target<g:select name="${varPrefix}Pre" from="${['yes', 'no']}" value="no" />
                        </gui:toolTip>
			<gui:toolTip text="Select yes if you want to run a target after you execute this plugin.">
				<br>Post-Execution Target<g:select name="${varPrefix}Post" from="${['yes', 'no']}" value="no" />
                        </gui:toolTip>
			<gui:expandablePanel title="JBoss Reader Override" bounce="false" expanded="false">
				<table>
				<tr>
				<td>
					<br>Builder Type<g:select name="${varPrefix}ReaderOverrideBuilderType" from="${['ant', 'maven']}" />
					<gui:toolTip text="This is the relative dir from the project root tot to the base directory of your build area.">
						<form:textField label="Base Relative Dir" name="${varPrefix}ReaderOverrideBaseDir"/>
					</gui:toolTip>
					<gui:toolTip text="This is your build file. It should exist in the Base Relative Dir.">
						<form:textField label="Build File" name="${varPrefix}ReaderOverrideFile"/>
					</gui:toolTip>
					<gui:toolTip text="This is the target within your build file to build your code">
						<form:textField label="Build Target" name="${varPrefix}ReaderOverrideTarget"/>
					</gui:toolTip>
				</td>
				<td>
					<h2>Property Maps</h2>
					<table>
						<tr><td>BDA Property</td><td>Sub-Project Property</td>
						<tr>
							<td><form:textField name="${varPrefix}ReaderOverrideBDAProp1"/></td>
							<td><form:textField name="${varPrefix}ReaderOverrideProp1"/></td>
						</tr>
						<tr>
							<td><form:textField name="${varPrefix}ReaderOverrideBDAProp2"/></td>
							<td><form:textField name="${varPrefix}ReaderOverrideProp2"/></td>
						</tr>
						<tr>
							<td><form:textField name="${varPrefix}ReaderOverrideBDAProp3"/></td>
							<td><form:textField name="${varPrefix}ReaderOverrideProp3"/></td>
						</tr>
					</table>
				</tr>
				</table>
			</gui:expandablePanel>
			<gui:expandablePanel title="JBoss Reader Pre-execution Target " bounce="false" expanded="false">
				<table>
				<tr>
				<td>
					<br>Builder Type<g:select name="${varPrefix}ReaderPreBuilderType" from="${['ant', 'maven']}" />
					<gui:toolTip text="This is the relative dir from the project root tot to the base directory of your build area.">
						<form:textField label="Base Relative Dir" name="${varPrefix}ReaderPreBaseDir"/>
					</gui:toolTip>
					<gui:toolTip text="This is your build file. It should exist in the Base Relative Dir.">
						<form:textField label="Build File" name="${varPrefix}ReaderPreFile"/>
					</gui:toolTip>
					<gui:toolTip text="This is the target within your build file to build your code">
						<form:textField label="Build Target" name="${varPrefix}ReaderPreTarget"/>
					</gui:toolTip>
				</td>
				<td>
					<h2>Property Maps</h2>
					<table>
						<tr><td>BDA Property</td><td>Sub-Project Property</td>
						<tr>
							<td><form:textField name="${varPrefix}ReaderPreBDAProp1"/></td>
							<td><form:textField name="${varPrefix}ReaderPreProp1"/></td>
						</tr>
						<tr>
							<td><form:textField name="${varPrefix}ReaderPreBDAProp2"/></td>
							<td><form:textField name="${varPrefix}ReaderPreProp2"/></td>
						</tr>
						<tr>
							<td><form:textField name="${varPrefix}ReaderPreBDAProp3"/></td>
							<td><form:textField name="${varPrefix}ReaderPreProp3"/></td>
						</tr>
					</table>
				</tr>
				</table>
			</gui:expandablePanel>
			<gui:expandablePanel title="JBoss Reader Post Execution Target " bounce="false" expanded="false">
				<table>
				<tr>
				<td>
					<br>Builder Type<g:select name="${varPrefix}ReaderPostBuilderType" from="${['ant', 'maven']}" />
					<gui:toolTip text="This is the relative dir from the project root tot to the base directory of your build area.">
						<form:textField label="Base Relative Dir" name="${varPrefix}ReaderPostBaseDir"/>
					</gui:toolTip>
					<gui:toolTip text="This is your build file. It should exist in the Base Relative Dir.">
						<form:textField label="Build File" name="${varPrefix}ReaderPostFile"/>
					</gui:toolTip>
					<gui:toolTip text="This is the target within your build file to build your code">
						<form:textField label="Build Target" name="${varPrefix}ReaderPostTarget"/>
					</gui:toolTip>
				</td>
				<td>
					<h2>Property Maps</h2>
					<table>
						<tr><td>BDA Property</td><td>Sub-Project Property</td>
						<tr>
							<td><form:textField name="${varPrefix}ReaderPostBDAProp1"/></td>
							<td><form:textField name="${varPrefix}ReaderPostProp1"/></td>
						</tr>
						<tr>
							<td><form:textField name="${varPrefix}ReaderPostBDAProp2"/></td>
							<td><form:textField name="${varPrefix}ReaderPostProp2"/></td>
						</tr>
						<tr>
							<td><form:textField name="${varPrefix}ReaderPostBDAProp3"/></td>
							<td><form:textField name="${varPrefix}ReaderPostProp3"/></td>
						</tr>
					</table>
				</tr>
				</table>
			</gui:expandablePanel>
