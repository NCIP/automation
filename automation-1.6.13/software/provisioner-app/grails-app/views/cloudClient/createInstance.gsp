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
		<legend><b>Application Provisioner</b></legend>
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
								<b><label for="appConfig">Application Configuration</label></b>
								<br>
								<br>
								<gui:toolTip text="Enter the Mail Server .">
									<form:textField label="Mail Server Hostname"  name="mailServerHostname" title="TextField" readonly="false" value="${params.mailServerHostname}"/>
								</gui:toolTip>	
								<gui:toolTip text="Enter the UPT server URL .">
									<form:textField label="UPT Server URL"  name="uptServerUrl" title="TextField" readonly="false" value="${params.uptServerUrl}"/>
								</gui:toolTip>
								<gui:toolTip text="Enter the LDAP URL .">
									<form:textField label="LDAP URL"  name="ldapUrl" title="TextField" readonly="false" value="${params.ldapUrl}"/>
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
									<form:textField label="Display Name"  name="gridTechPOCDisplayName" title="TextField" readonly="false" value="${params.gridTechPOCDisplayName}"/>
								</gui:toolTip>	
								<gui:toolTip text="Grid Tech POC Research Center Short Name .">
									<form:textField label="Short Name"  name="gridTechPOCShortName" title="TextField" readonly="false" value="${params.gridTechPOCShortName}"/>
								</gui:toolTip>
								<gui:toolTip text="Grid Tech POC Last Name  .">
									<form:textField label="Last Name"  name="gridTechPOCLastName" title="TextField" readonly="false" value="${params.gridTechPOCFirstName}"/>
								</gui:toolTip>
								<gui:toolTip text="Grid Tech POC Phone Number .">
									<form:textField label="Phone Number"  name="gridTechPOCPhoneNumber" title="TextField" readonly="false" value="${params.gridTechPOCPhoneNumber}"/>
								</gui:toolTip>	
								<gui:toolTip text="Grid Tech POC Email Address .">
									<form:textField label="Email Address"  name="gridTechPOCEmail" title="TextField" readonly="false" value="${params.gridTechPOCEmail}"/>
								</gui:toolTip>									
		
							</td>
							<td width="30%">
								<br>
								<br>
									<form:textField label="Address1" name="address1" />
									<form:textField label="City" name="city" />
									<form:textField label="Province/State" name="province" />
									<form:textField label="Postal Code" name="postalCode" />
									<label>Country</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select name="averageSizeOfProjects" >
										<option value="null">Select Country</option>
										<option value="USA">USA</option>
										<option value="Canada">Canada </option>
									</select> 
								
							</td>
							
							<td width="40%">

								
							</td>
						</tr>
					</table>					
				<div class="buttons">
					<g:actionSubmit value="Provision Application" action="provisionSystem" />
					<g:actionSubmit value="Applications" action="validate" />
				</div>							
			</g:form>	
		</formset>
	</body>
</html>