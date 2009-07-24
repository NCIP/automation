	<html>
	<head>
	<title>Provision Instance</title>
	<meta name="layout" content="main"/>
	</head>
	<body>
	<formset>
	<legend>BDA Provisioner</legend>
	    <g:form action="generateKey">
		<label for="aId">Access Key ID</label>
		<g:textField name="accessId" value="${params.accessId}"/></br>
		<label for="sId">Secret Access Key</label>
		<g:textField name="secretId" value="${params.secretId}"/></br>
		<label for="keyId">Private Key</label></br>
		<g:textArea name="privateKey" value="${params.privateKey}" cols="40" rows="10"/></br>
		<g:submitButton name="generateKey" value="Generate Key"/>
	    </g:form>
	    <g:form action="provisionAMI">
	    	<g:hiddenField name="accessId" value="${params.accessId}" />
	    	<g:hiddenField name="secretId" value="${params.secretId}" />
	    	<g:hiddenField name="privateKey" value="${params.privateKey}" />
	    	<label for="portId">Port List</label>
	    	<g:textField name="portList" value="${params.portList}"/></br>
	    	<label for="emailId">Email Address</label>
	    	<g:textField name="email" value="${params.email}"/></br>	    	
		<g:submitButton name="provisionAMI" value="Provision AMI"/>   
	    </g:form>
	</formset>
	</body>
</html>