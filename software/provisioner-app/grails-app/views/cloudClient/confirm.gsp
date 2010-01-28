<html>
	<head>
		<title><g:loggedInUserInfo field="username">Guest</g:loggedInUserInfo>@Apllication Provisioner</title>
		<meta name="layout" content="main"/>
		<g:javascript library="jquery" />
		 <style>
			.right
			{
				position:absolute;
				right:0px;
				width:300px;
			}
		</style>
	</head>
	<body>
	<formset>
		<div class='right'>
			<p>
				<g:isLoggedIn>
					<g:link controller="logout" action="index">sign out</g:link>
				</g:isLoggedIn>
			<p>
		</div>
		<br>
		<legend><b>caArray Application Provisioner</b></legend>	
	    <g:form action="validate">
		<label><b>The caArray Application will be installed and configured for you cancer center. In about 30 minutes, you will receive an email with the link to the newly installed application. </b></label><br>
		<label><b>Click on the 'Download Key' button below to download the Private Key file you can use to access your instance.</b></label><br>	
		<g:hiddenField name="privateKeyFileName" value="${fileName}" />
		<div class="buttons">
			<g:actionSubmit value="Download Key" action="downloadKey" />
			<g:actionSubmit value="Request Another Instance" action="validate" />
		</div>
	    </g:form>
	</formset>
	</body>
</html>