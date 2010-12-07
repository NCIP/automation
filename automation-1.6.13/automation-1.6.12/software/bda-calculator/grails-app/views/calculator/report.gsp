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
	<legend><b>BDA Calculator</b></legend>
	<%! import java.text.NumberFormat %>
	<%
		NumberFormat nf = NumberFormat.getCurrencyInstance();    		
		int totalSaving = reportBeanNonBDA.getOrganizationalCost() - reportBeanBDA.getOrganizationalCost()
		String totalSavingDollars = nf.format(totalSaving);
		String nonBDAOrgCosts = nf.format(reportBeanNonBDA.getOrganizationalCost());
		String bdaOrgCosts = nf.format(reportBeanBDA.getOrganizationalCost());
		String nonBDACostPerProject = nf.format(reportBeanNonBDA.getCostPerProject());
		String bdaCostPerProject = nf.format(reportBeanBDA.getCostPerProject());
		String nonBDAAvgPricePerHour = nf.format(reportBeanNonBDA.getAveragePricePerHour());
		String bdaAvgPricePerHour = nf.format(reportBeanBDA.getAveragePricePerHour());		
	%>
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
								<td>${bdaAvgPricePerHour}
								</td>								
								<td>${nonBDAAvgPricePerHour}
								</td>
							</tr>	
							<tr class="odd">
								<td>
								<label><b>Cost per project(In Dollars)</b></label>
								</td>
								<td>${bdaCostPerProject}
								</td>								
								<td>${nonBDACostPerProject}
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
								<td>${bdaOrgCosts}
								</td>								
								<td>${nonBDAOrgCosts}									
								</td>
							</tr>						
						</g:if>	
					</tbody>
				</table>
				<table>
					<tbody>
						<tr class="odd">
							<td>
							<label><b>Total Saving(In Dollars)</b></label>
							</td>
							<td><b>${totalSavingDollars}</b>
							</td>								
						</tr>
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