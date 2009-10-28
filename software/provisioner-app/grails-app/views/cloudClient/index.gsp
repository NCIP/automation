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
			<h4>This BDA Provisioner application will generate working software using AWS' cloud infrastructure. </h4>
			<h4>If you do not have an AWS <u>EC2</u> account, you will need to register for one by going to the <a href="http://aws.amazon.com/">AWS</a> page and selecting the <strong>Sign Up Now</strong> button. You must also register for an <u>EC2</u> account.</h4>
			<br>
			<g:if test="${flash.message}">
				<div class="errors">
					${flash.message}
				</div>
			</g:if>
	    		<g:form action="validate">
				<form:textField label="Access Key ID"  name="accessId" title="TextField" readonly="false" value="${params.accessId}">
				  Enter your <strong>Access Key ID</strong>. To get your AWS <a href="https://aws-portal.amazon.com/gp/aws/developer/account/index.html?action=access-key">Access Identifiers</a>, go <a href="https://aws-portal.amazon.com/gp/aws/developer/account/index.html?action=access-key">here</a>
				</form:textField>
				<form:passwordField label="Secret Access Key"  name="secretId" title="TextField" readonly="false" value="${params.secretId}">
				  Enter your AWS <strong>Secret Access Key</strong>. To get your AWS <a href="https://aws-portal.amazon.com/gp/aws/developer/account/index.html?action=access-key">Access Identifiers</a>, go <a href="https://aws-portal.amazon.com/gp/aws/developer/account/index.html?action=access-key">here</a>
				</form:passwordField>
				<form:buttonBar>
				  <input type="submit" value="Login" />
				 </form:buttonBar>
	    		</g:form>
	    		<br>
	            v0.1.0
		</formset>
	</body>
</html>