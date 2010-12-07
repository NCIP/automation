<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="layout" content="main" />
	<title>BDA Calculator</title>
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
		<legend><b>BDA Calculator</b></legend>
	    		<g:form action="calculate">
				<g:hasErrors>
					<div class="errors">
						<g:renderErrors bean="${instance}" as="list" />
					</div>
				</g:hasErrors>
				<table>
					<tbody>	
						<tr>
							<td>
								<label for="averageAutomatedCodeCoveragePersentage">Average Automated Code Coverage Percentage </label>
							</td>
							<td>
								<select name="averageAutomatedCodeCoveragePersentage" >
									<option value="null">Select Code Coverage Percentage</option>
									<option value="10">10%</option>
									<option value="20">20%</option>
									<option value="30">30%</option>
									<option value="40">40%</option>
									<option value="50">50%</option>
									<option value="60">60%</option>
									<option value="70">70%</option>
									<option value="80">80%</option>
									<option value="90">90%</option>
									<option value="100">100%</option>
								</select> 
							</td>
						</tr>
						<tr>
							<td>
								<label for="numberOfProjectsInPortfolio">Number Of Projects In Portfolio </label>
							</td>
							<td>
								<g:textField name="numberOfProjectsInPortfolio" />
							</td>
						</tr>					
						<tr>
							<td>
								<label for="numberOfProjectsInPortfolio">Average Number Of Deployments Per Project Per Year </label>
							</td>
							<td>
								<g:textField name="averageNumberOfDeploymentsPerProjectPerYear" />
							</td>
						</tr>

					<tr>
						<td>
							<label>Average Size Of Projects</label>&nbsp;
						</td>
						<td>							
							<select name="averageSizeOfProjects" >
								<option value="null">Select Application Size</option>
								<option value="40">Small (0-25,000 SLOC)</option>
								<option value="80">Medium (25K-100K SLOC)</option>
								<option value="120">Large (&gt; 100K SLOC)</option>					
							</select> 
						</td>						
					</tr>	

					<tr>
						<td>
							<label>Number Of Target Environments Per Project</label>&nbsp;
						</td>
						<td>							
							<g:textField name="numberOfTargetEnvironmentsPerProject" />
						</td>						
					</tr>	
				
					<tr>
						<td>
							<label>Average Technical Architecture Complexity</label>&nbsp;
						</td>
						<td>							
							<select name="averageTechnicalArchitectureComplexity" >
								<option value="null">Technical Architecture Complexity</option>
								<option value="1">Low (Standard Tech Stack, Single Containers)</option>
								<option value="2">Medium (Slight Deviations to Tech Stack, More than one container)</option>
								<option value="3">High (Several tech stack deviations and/or multiple containers)</option>					
							</select> 
						</td>						
					</tr>	

					<tr>
						<td>
							<label>Average Engineer Hourly Rate(In Dollars)</label>&nbsp;
						</td>
						<td>							
							<g:textField name="averageEngineerHourlyRate" />
						</td>						
					</tr>

			
					</tbody>	
				</table>
				<div class="buttons">
					<g:actionSubmit value="Calculate" action="calculate" />
				</div>
	    		</g:form>
			
	</formset>
</body>
</html>