<html>
	<head>
		<title>BDA Provisioner
		</title>
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

			<legend><b>Application Provisioner</b></legend>
			<h4>This Application Provisioner application will generate working software using AWS' cloud infrastructure. </h4>
			<br>
			<g:if test="${flash.message}">
				<div class="errors">
					${flash.message}
				</div>
			</g:if>
	    		<g:form action="validate">
				<form:textField label="User Name"  name="accessId" title="TextField" readonly="false" value="${params.accessId}">
				
				</form:textField>
				<form:passwordField label="Password"  name="secretId" title="TextField" readonly="false" value="${params.secretId}">
				 
				</form:passwordField>
				<form:buttonBar>
				  <input type="submit" value="Login" />
				 </form:buttonBar>
	    		</g:form>
	    		<br>
	            v0.1.0
		</formset>
	</body>
</html>