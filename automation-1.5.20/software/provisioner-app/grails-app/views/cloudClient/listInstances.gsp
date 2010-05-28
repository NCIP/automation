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
	<legend><b>BDA Provisioner</b></legend>
		<div class="body">
			<g:form action="terminateInstances" >
				<div class="list">		
				<table>
					<thead>
						<tr>
							<g:sortableColumn property="instance_check" title="Select"/> 
							<g:sortableColumn property="instance_id" title="Instance Id" />
							<g:sortableColumn property="instance_id" title="Image Id" />
							<g:sortableColumn property="instance_status" title="Instance Status" />
							<g:sortableColumn property="instance_dns_name" title="Instance DNS Name" />
						</tr>
					</thead>
					<g:if test="${listAllInstances}">	
						<label><b><strong>${listAllInstances.size()}</strong> Instances Running</b></label><br>
						<tbody>
						<g:each var="result" in="${listAllInstances}">
							<g:if test="${result?.getInstances()}">
								<g:each var="instances" in="${result.getInstances()}" status="i">
										<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
											<td><g:checkBox name="instancesTerminating" value="${instances.getInstanceId()}" checked="false"/>
											</td>
											<td>${instances.getInstanceId()}
											</td>								
											<td>${instances.getImageId()}
											</td>
											<td>${instances.getState()}
											</td>
											<td>${instances.getDnsName()}
											</td>
										</div>
								</g:each>
							</g:if>	
						</g:each>
						</tbody>
					</g:if>
					<g:else>
						<label><b><strong>0</strong> Instances Running</b></label>
					</g:else>
				</table>
				</div>
				<div class="buttons">
					<g:actionSubmit value="Terminate" />
					<g:actionSubmit value="Request Instance" action="validate" />
					<g:actionSubmit value="Request Instance With Another ID" action="index" />
				</div>
			</g:form>
		</div>
	</body>
</html>