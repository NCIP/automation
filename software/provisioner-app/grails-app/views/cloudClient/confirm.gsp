	<html>
	<head>
	<title>Provision Instance</title>
	<meta name="layout" content="main"/>
	</head>
	<body>
	<formset>
	<legend>BDA Provisioner</legend>
	    <g:form action="index">
		<label>A new Amazon EC2 virtual instance has been created and the Hudson CI Server is being configured </label><br>
		<label>A follow up email will be sent in around 20 minutes to the email address provided </label><br>
		<g:submitButton name="requestAnotherAMI" value="Request Another AMI"/>
	    </g:form>
	</formset>
	</body>
</html>