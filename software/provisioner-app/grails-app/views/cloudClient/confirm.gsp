	<html>
	<head>
	<title>Provision Instance</title>
	<meta name="layout" content="main"/>
	</head>
	<body>
	<formset>
	<legend>System Provisioner</legend>
	    <g:form action="index">
		<label>The New EC2 AMI generated and the Hudson CI Server is configured </label><br>
		<label>A follow up email will be sent in around 30 minutes to the email address provided </label><br>
		<g:submitButton name="requestAnotherAMI" value="Request Another AMI"/>
	    </g:form>
	</formset>
	</body>
</html>