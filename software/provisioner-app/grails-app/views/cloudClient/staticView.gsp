	<html>
	<head>
	<title>Provision Instance</title>
	<meta name="layout" content="main"/>
	</head>
	<body>
	<formset>
	<legend>System Provisioner</legend>
	    <g:form action="generateKey">
		<label for="aId">The New EC2 AMI generated and the Hundon server is configured </label><br>
		<label for="aId">The host name of the server is ${params.hostName} </label><br>
		<label for="aId">The host can be connected using username ${params.hudsonUserName} and password ${params.hudsonUserPassword}</label><br>		
	    </g:form>
	</formset>
	</body>
</html>