<html>
	<head>
		<title><g:loggedInUserInfo field="username">Guest</g:loggedInUserInfo>@Application Provisioner
		</title>
		<meta name="layout" content="main"/>
		
		 <style>
			.right
			{
				position:absolute;
				right:0px;
				width:300px;
			}

		</style>
		<gui:resources components="['toolTip','tabView']"/>	</head>
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
		<legend><b>Available applications</b></legend>
		<br>
			<g:form>

			<table>
				<tr>
					<td>
						<g:link action="isApplicationAuthorised" params="[projectName:'caarray']">
							<img src="${createLinkTo(dir:'/images', file:'logo_caarray.gif')}" alt="caArray Application"/>
						</g:link>
					</td>
					<td>
						
						<img src="${createLinkTo(dir:'/images', file:'Logo-NCIA-2.gif')}" alt="NBIA Application"/>
						
					</td>

				</tr>	
			</table>
				<div class="buttons">
					<g:actionSubmit value="List Instances" action="listInstances" />
				</div>									
			</g:form>	
		</formset>
	</body>
</html>