	<html>
	<head>
	<title>Provision Instance</title>
	<meta name="layout" content="main"/>
	</head>
	<body>
	<formset>
	<legend>System Provisioner</legend>
	    <g:form action="index">
		<label>The New EC2 AMI generated and the Hundon server is configured </label><br>
		<label>The host name of the server is '<b>${hostName}</b>' </label><br>
		<label>The host can be connected using username '<b>${hudsonUserName}</b>' and password '<b>${hudsonUserPassword}</b>'</label><br>		
		<g:submitButton name="requestAnotherAMI" value="Request Another AMI"/>
	    </g:form>
	</formset>
	</body>
</html>