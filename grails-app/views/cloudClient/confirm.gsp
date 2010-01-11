<html>
	<head>
		<title>BDA Provisioner</title>
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
	    <g:form action="validate">
		<label><b>A new Amazon EC2 virtual instance has been created and the Hudson CI Server is being configured </b></label><br>
		<label><b>A follow up email will be sent in around 20 minutes to the email address provided </b></label><br>	
		<label><b>Click on the 'Download Key' button below to download the Private Key file you can use to access your instance.</b></label><br>	
		<g:hiddenField name="privateKeyFileName" value="${fileName}" />
		<div class="buttons">
			<g:actionSubmit value="Download Key" action="downloadKey" />
			<g:actionSubmit value="Request Another Instance" action="validate" />
			<g:actionSubmit value="Logoff" action="index" />
		</div>
	    </g:form>
	</formset>
	</body>
</html>