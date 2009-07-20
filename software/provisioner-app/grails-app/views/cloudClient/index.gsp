	<html>
	<head>
	<title>Provision Instance</title>
	<meta name="layout" content="main"/>
	</head>
	<body>
	<formset>
	<legend>System Provisioner</legend>
	    <g:form action="generateKey">
		<label for="aId">Access Id</label>
		<g:textField name="accessId" value="${params.accessId}"/></br>
		<label for="sId">Secret Id</label>
		<g:textField name="secretId" value="${params.secretId}"/></br>
		<label for="sId">Private Key</label></br>
		<g:textArea name="privateKey" value="${params.privateKey}" cols="40" rows="10"/></br>
		<g:submitButton name="generateKey" value="Generate Key"/>
	    </g:form>
	    <g:form action="provisionAMI">
	    	<g:hiddenField name="accessId" value="${params.accessId}" />
	    	<g:hiddenField name="secretId" value="${params.secretId}" />
	    	<g:hiddenField name="privateKey" value="${params.privateKey}" />
	    	<label for="sId">Port List</label>
	    	<g:textField name="portList" value="${params.portList}"/></br>
		<g:submitButton name="provisionAMI" value="Provision AMI"/>   
	    </g:form>
	</formset>
	</body>
</html>