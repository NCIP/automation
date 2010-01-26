<html>
	<head>
		<title>Application Provisioner
		</title>
		<meta name="layout" content="main"/>
		
		<g:javascript library="jquery" />
		 <style>
		        .fieldWrapper { margin-bottom: 20px;}
	
			.fieldWrapper label { float: left; width: 150px; }
	
			.fieldWrapper .fieldText { margin-left: 150px; }
	
			.fieldWrapper .fieldText .error { color: red; }
	
			.fieldWrapper .error label { color: red; }
	
			.fieldWrapper .radioWrapper { margin-left: 150px; } 
		</style>
		<gui:resources components="['toolTip','tabView']"/>
	</head>
	<body>
		<formset>
		<legend><b>caArray Application Provisioner</b></legend>
			<g:form>
				<br>

						<g:hasErrors>
							<div class="errors">
								<g:renderErrors bean="${instance}" as="list" />
							</div>
						</g:hasErrors>
					<table>
						<tr>
							<td>
								<b><label for="appConfig">caArray Application Configuration</label></b>
								<br>
								<br>
								<gui:toolTip text="Enter the Email Address .">
									<form:textField label="Email Address"  name="email" title="TextField" readonly="false" value="${params.email}"/>
								</gui:toolTip>								
								<gui:toolTip text="Enter the Mail Server .">
									<form:textField label="Mail Server Hostname"  name="projectproperty_mail_smtp_host" title="TextField" readonly="false" value="${params.mailServerHostname}"/>
								</gui:toolTip>	
								<gui:toolTip text="Enter the UPT server URL .">
									<form:textField label="UPT Server URL"  name="projectproperty_upt_url" title="TextField" readonly="false" value="${params.uptServerUrl}"/>
								</gui:toolTip>
								<gui:toolTip text="Enter the LDAP URL .">
									<form:textField label="LDAP URL"  name="projectproperty_ldap_url" title="TextField" readonly="false" value="${params.ldapUrl}"/>
								</gui:toolTip>
							</td>
						</tr>
					</table>
									
					<table width="100%">
						<tr>
							<td width="30%">
								<b><label for="gridTechPOC">Grid Tech POC Research Center Information</label></b>
								<br>
								<br>
								<gui:toolTip text="Grid Tech POC Research Center Display Name .">
									<form:textField label="Display Name"  name="projectproperty_grid_poc_tech_researchCenter_displayname" title="TextField" readonly="false" value="${params.projectproperty_grid_poc_tech_researchCenter_displayname}"/>
								</gui:toolTip>	
								<gui:toolTip text="Grid Tech POC Research Center Short Name .">
									<form:textField label="Short Name"  name="projectproperty_grid_poc_tech_researchCenter_shortname" title="TextField" readonly="false" value="${params.projectproperty_grid_poc_tech_researchCenter_shortname}"/>
								</gui:toolTip>
								<gui:toolTip text="Grid Tech POC Last Name  .">
									<form:textField label="Last Name"  name="projectproperty_grid_poc_tech_name_last" title="TextField" readonly="false" value="${params.projectproperty_grid_poc_tech_name_last}"/>
								</gui:toolTip>
								<gui:toolTip text="Grid Tech POC First Name  .">
									<form:textField label="First Name"  name="projectproperty_grid_poc_tech_name_first" title="TextField" readonly="false" value="${params.projectproperty_grid_poc_tech_name_first}"/>
								</gui:toolTip>											
							</td>

							
							<td width="30%">
								<br>
								<br>									
									<form:textField label="Address1" name="projectproperty_grid_poc_tech_addr_street1" />
									<form:textField label="Address2" name="projectproperty_grid_poc_tech_addr_street2" />
									<form:textField label="City" name="projectproperty_grid_poc_tech_addr_locality" />
									<form:textField label="Province/State" name="projectproperty_grid_poc_tech_addr_stateProvince" />									
									<label>Country</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select name="projectproperty_grid_poc_tech_addr_country" >
										<option value="null">Select Country</option>
										<option value="USA">USA</option>
										<option value="Canada">Canada</option>
									</select> 
								
							</td>							
							<td width="40%">
								<br>
								<br>
									<gui:toolTip text="Grid Tech POC Phone Number .">
										<form:textField label="Phone Number"  name="projectproperty_grid_poc_tech_phone" title="TextField" readonly="false" value="${params.projectproperty_grid_poc_tech_phone}"/>
									</gui:toolTip>	
									<gui:toolTip text="Grid Tech POC Email Address .">
										<form:textField label="Email Address"  name="projectproperty_grid_poc_tech_email" title="TextField" readonly="false" value="${params.projectproperty_grid_poc_tech_email}"/>
									</gui:toolTip>								
									<form:textField label="Role" name="projectproperty_grid_poc_tech_role" value="${params.projectproperty_grid_poc_tech_role}/>
									<form:textField label="Affiliation" name="projectproperty_grid_poc_tech_affiliation" value="${params.projectproperty_grid_poc_tech_affiliation} />
									<form:textField label="Postal Code" name="projectproperty_grid_poc_tech_addr_postalCode"  />
							</td>
						</tr>
					</table>											
				<g:hiddenField name="projectName" value="caarray" />										
				<div class="buttons">
					<g:actionSubmit value="Provision Application" action="provisionSystem" />
					<g:actionSubmit value="Applications" action="validate" />
				</div>							
			</g:form>	
		</formset>
	</body>
</html>