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
				<form:textField label="Average Automated Code Coverage Persentage "  name="averageAutomatedCodeCoveragePersentage" title="TextField" readonly="false" value="${params.portList}">
				  Enter the Average Automated Code Coverage Persentage
				</form:textField>		
				<form:textField label="Number Of Projects In Portfolio "  name="numberOfProjectsInPortfolio" title="TextField" readonly="false" value="${params.projectSCMUrl}">
				  Enter the Number Of Projects In Portfolio
				</form:textField>
				<form:textField label="Average Number Of Deployments Per Project Per Year "  name="averageNumberOfDeploymentsPerProjectPerYear" title="TextField" readonly="false" value="${params.projectBuildTargets}">
				  Enter the Average Number Of Deployments Per Project Per Year
				</form:textField>
				<form:textField label="Average Size Of Projects "  name="averageSizeOfProjects" title="TextField" readonly="false" value="${params.projectBuildFile}">
				  Enter the Average Size Of Projects
				</form:textField>				
				<form:textField label="Number Of Target Environments Per Project "  name="numberOfTargetEnvironmentsPerProject" title="TextField" readonly="false" value="${params.projectBuildOptions}">
				  Enter the Number Of Target Environments Per Project
				</form:textField>				
				<form:textField label="Average Technical Architecture Complexity "  name="averageTechnicalArchitectureComplexity" title="TextField" readonly="false" value="${params.projectBuildOptions}">
				  Average Technical Architecture Complexity
				</form:textField>				
				<form:textField label="Average Engineer Hourly Rate "  name="averageEngineerHourlyRate" title="TextField" readonly="false" value="${params.projectBuildOptions}">
				  Average Engineer Hourly Rate
				</form:textField>				


				
				<div class="buttons">
					<g:actionSubmit value="calculate" action="calculate" />
				</div>
	    		</g:form>
			
	</formset>
</body>
</html>