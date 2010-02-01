<html>
	<head>
		<title>Application Provisioner</title>
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
	<legend><b>Application Provisioner</b></legend>
		<div class='right'>
			<p>
				<g:isLoggedIn>
					<g:link controller="logout" action="index">sign out</g:link>
				</g:isLoggedIn>
			<p>
		</div>	
		<br>
		<div class="body">
			<g:form action="terminateInstances" >
				<div class="list">		
				<table>
					<thead>
						<tr>
							<g:sortableColumn property="instance_check" title="Select"/> 					
							<g:sortableColumn property="project_name" title="Project Name" />
							<g:sortableColumn property="instance_dns_name" title="Instance DNS Name" />
							<g:sortableColumn property="instance_status" title="Instance Status" />
						</tr>
					</thead>
					<g:if test="${listAllInstances}">	
						<label><b><strong>${listAllInstances.size()}</strong> Instances Running</b></label><br>
						<tbody>
						<g:each var="result" in="${listAllInstances}">
							<g:if test="${result}" >
								<tr >
									<td><g:checkBox name="instancesTerminating" value="${result.getInstanceId()}" checked="false"/>
									</td>							
									<td>${result.getProjectName()}
									</td>
									<td>${result.getInstanceName()}
									</td>
									<td>${result.getInstanceStatus()}
									</td>
								</div>

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
					<g:actionSubmit value="Terminate" action="terminate" />
					<g:actionSubmit value="Request Instance" action="validate" />
				</div>
			</g:form>
		</div>
	</body>
</html>