<html>
	<head>
		<title>BDA Provisioner
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
	</head>
	<body>
		<formset>
			<legend><b>BDA Provisioner</b></legend>
			<h4>After entering the information below and clicking the <b>Provision Instance</b> button, you will receive a follow-up email with further instructions. </h4>
			<h4>By default, the BDA Provisioner will open the following ports: 22,48080,48210.</h4>
			<br>
			<g:if test="${flash.message}">
				<div class="errors">
					${flash.message}
				</div>
			</g:if>
	    		<g:form action="provisionInstance">		
				<form:textField label="Port List"  name="portList" title="TextField" readonly="false" value="${params.portList}">
				  Enter the external ports (in comma-delimited, no spaces format. For example: 46210,48080) you wish to expose in the <strong>Port List</strong> field
				</form:textField>		
				<form:textField label="Email Address"  name="email" title="TextField" readonly="false" value="${params.email}">
				  Enter the email address where you wish to receive further instructions
				</form:textField>
				<div class="buttons">
					<g:actionSubmit value="Provision Instance" action="provisionInstance" />
					<g:actionSubmit value="List Instances" action="listInstances" />
					<g:actionSubmit value="Logoff" action="index" />
				</div>
	    		</g:form>
	    		<br>
	    		<h4>Important: To terminate your EC2 instances, select the <u>List Instances</u> button and select the instance(s) to terminate. You will continue to be charged until you terminate your instances.</h4>
		</formset>
	</body>
</html>