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
			<h4>This BDA Provisioner application will generate working software using Amazon's cloud infrastructure. </h4>
			<h4>After entering the information below and clicking the <b>Provision Instance</b> button, you will receive a follow-up email with further instructions. </h4>
			<h4>By default, the BDA Provisioner will open the following ports: 22,48080,48210.</h4>
			<h4>If you do not have an AWS <u>EC2</u> account, you will need to register for one by going to the <a href="http://aws.amazon.com/">Amazon Web Services</a> page and select the <u>Sign Up Now</u> button. You must also register for an <u>EC2</u> account.</h4>
			<br>
			<g:if test="${flash.message}">
				<div class="errors">
					${flash.message}
				</div>
			</g:if>
	    		<g:form action="provisionAMI">
				<form:textField label="Access Key ID"  name="accessId" title="TextField" readonly="false" value="${params.accessId}">
				  Enter your <strong>Access Key ID</strong>. To get your Amazon Web Services <a href="https://aws-portal.amazon.com/gp/aws/developer/account/index.html?action=access-key">Access Identifiers</a>, go <a href="https://aws-portal.amazon.com/gp/aws/developer/account/index.html?action=access-key">here</a>
				</form:textField>
				<form:textField label="Secret Access Key"  name="secretId" title="TextField" readonly="false" value="${params.secretId}">
				  Enter your Amazon <strong>Secret Access Key</strong>. To get your Amazon Web Services <a href="https://aws-portal.amazon.com/gp/aws/developer/account/index.html?action=access-key">Access Identifiers</a>, go <a href="https://aws-portal.amazon.com/gp/aws/developer/account/index.html?action=access-key">here</a>
				</form:textField>		
				<form:textField label="Port List"  name="portList" title="TextField" readonly="false" value="${params.portList}">
				  Enter the external ports (in comma-delimited, no spaces format. For example: 46210,48080) you wish to expose in the <strong>Port List</strong> field
				</form:textField>		
				<form:textField label="Email Address"  name="email" title="TextField" readonly="false" value="${params.email}">
				  Enter the email address where you wish to receive further instructions
				</form:textField>
				<form:buttonBar>
				  <input type="submit" value="Provision Instance" />
				 </form:buttonBar>
	    		</g:form>
	    		<br>
	    		<h4>Important: The BDA Provisioner will not terminate your Amazon EC2 instance. You will continue to be charged until you manually terminate this instance. Go to the <a href="https://console.aws.amazon.com/">AWS Management Console</a> to terminate your EC2 instance(s)</h4>
		</formset>
	</body>
</html>