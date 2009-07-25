	<html>
	<head>
	<title>Provision Instance</title>
	<meta name="layout" content="main"/>
	</head>
	<body>
	<formset>
	<legend>BDA Provisioner</legend>
	    <g:form action="provisionAMI">
		<label for="aId">Access Key ID</label>
		<g:textField name="accessId" value="${params.accessId}"/></br>
		<label for="sId">Secret Access Key</label>
		<g:textField name="secretId" value="${params.secretId}"/></br>
	    	<label for="portId">Port List</label>
	    	<g:textField name="portList" value="${params.portList}"/></br>
	    	<label for="emailId">Email Address</label>
	    	<g:textField name="email" value="${params.email}"/></br>	    	
		<g:submitButton name="provisionAMI" value="Provision AMI"/>   
	    </g:form>
	</formset>
	</body>
</html>