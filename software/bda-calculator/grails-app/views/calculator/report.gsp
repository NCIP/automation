<html>
	<head>
		<title>BDA Calculator</title>
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
	<g:javascript>
		function refreshPage() 
		{
			alert("111");
		}
	</g:javascript>
	<legend><b>BDA Provisioner</b></legend>
		<div class="body">
			<g:form >
				<div class="list">		
				<table>
					<thead>
						<tr class="odd">
							<td>
							<label><b>Feature</b></label>
							</td>
							<td>
							<label><b>BDA Project</b></label>
							</td>
							<td>
							<label><b>Non-BDA Project</b></label>
							</td>
						</tr>
					</thead>
					<tbody>
						<g:if test="${reportBeanBDA}">
							<tr class="even">
								<td>
								<label><b>Number of testing hours per deployment</b></label>
								</td>
								<td>${reportBeanBDA.getNumberOfTestingHoursPerDeployment()}
								</td>								
								<td>${reportBeanNonBDA.getNumberOfTestingHoursPerDeployment()}
								</td>
							</tr>	
							<tr class="odd">
								<td>
								<label><b>Hours spent in each target environment</b></label>
								</td>
								<td>${reportBeanBDA.getHoursSpentInEachTargetEnvironment()}
								</td>								
								<td>${reportBeanNonBDA.getHoursSpentInEachTargetEnvironment()}
								</td>
							</tr>
							<tr class="even">
								<td>
								<label><b>Total Number of deployment hours per deployment</b></label>
								</td>
								<td>${reportBeanBDA.getTotalNumberOfDeploymentHoursPerDeployment()}
								</td>								
								<td>${reportBeanNonBDA.getTotalNumberOfDeploymentHoursPerDeployment()}
								</td>
							</tr>	
							<tr class="odd">
								<td>
								<label><b>Total number of deployment hours per year per project</b></label>
								</td>
								<td>${reportBeanBDA.getTotalNumberOfDeploymentHoursPerYearPerProject()}
								</td>								
								<td>${reportBeanNonBDA.getTotalNumberOfDeploymentHoursPerYearPerProject()}
								</td>
							</tr>							
							<tr class="even">
								<td>
								<label><b>Average price per hour(In Dollars)</b></label>
								</td>
								<td>$${reportBeanBDA.getAveragePricePerHour()}
								</td>								
								<td>$${reportBeanNonBDA.getAveragePricePerHour()}
								</td>
							</tr>	
							<tr class="odd">
								<td>
								<label><b>Cost per project(In Dollars)</b></label>
								</td>
								<td>$${reportBeanBDA.getCostPerProject()}
								</td>								
								<td>$${reportBeanNonBDA.getCostPerProject()}
								</td>
							</tr>
							<tr class="even">
								<td>
								<label><b>Number of Projects in Portfolio</b></label>
								</td>
								<td>${reportBeanBDA.getNumberOfProjectsInPortfolio()}
								</td>								
								<td>${reportBeanNonBDA.getNumberOfProjectsInPortfolio()}
								</td>
							</tr>	
							<tr class="odd">
								<td>
								<label><b>Organizational Cost(In Dollars)</b></label>
								</td>
								<td>$${reportBeanBDA.getOrganizationalCost()}
								</td>								
								<td>$${reportBeanNonBDA.getOrganizationalCost()}
								</td>
							</tr>								
						</g:if>	
					</tbody>
				</table>
				</div>
				<div class="buttons">
					<g:actionSubmit value="Calculate Again" action="index" />
				</div>				
			</g:form>
		</div>
	</body>
</html>