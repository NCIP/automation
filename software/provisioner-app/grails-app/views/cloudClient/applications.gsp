<html>
	<head>
		<title>BDA Provisioner
		</title>
		<meta name="layout" content="main"/>
		
		 <style>
		        .fieldWrapper { margin-bottom: 20px;}
	
			.fieldWrapper label { float: left; width: 150px; }
	
			.fieldWrapper .fieldText { margin-left: 150px; }
	
			.fieldWrapper .fieldText .error { color: red; }
	
			.fieldWrapper .error label { color: red; }
	
			.fieldWrapper .radioWrapper { margin-left: 150px; } 
		</style>
		<gui:resources components="['toolTip','tabView']"/>	</head>
	<body>
		<formset>
		<legend><b>Application Provisioner</b></legend>
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
						<g:link action="isApplicationAuthorised" params="[projectName:'nbia']">
							<img src="${createLinkTo(dir:'/images', file:'Logo-NCIA-2.gif')}" alt="NBIA Application"/>
						</g:link>
					</td>

				</tr>	
			</table>
			
			</g:form>	
		</formset>
	</body>
</html>