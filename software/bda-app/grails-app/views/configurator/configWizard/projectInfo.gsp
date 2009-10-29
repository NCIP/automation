<html>
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="layout" content="main" />
	<gui:resources components="['expandablePanel']"/>
        <title>Project Info</title>
    </head>
    <body>
    <formset>
	<g:if test="${flash.message}">
		<div class="errors">
			${flash.message}
		</div>
	</g:if>
	<g:form action="configWizard">
		<gui:expandablePanel title="Project Details" expanded="true">
			<form:textField label="Project Name"  name="projectName" title="TextField" readonly="false" />
			<form:textarea label="Project Description"  name="projectDesc" title="TextArea" readonly="false" />
			<form:textField label="Project PM Contact"  name="projectPMContact" title="TextField" readonly="false" />
			<form:textField label="Project Tech Contact"  name="projectTechContact" title="TextField" readonly="false" />
			<form:textField label="Project Web URL"  name="projectWebUrl" title="TextField" readonly="false" />
			<form:textField label="Project SVN Url"  name="projectSVNUrl" title="TextField" readonly="false" />
		</gui:expandablePanel>
		<gui:expandablePanel title="Sub-Projects Info" expanded="true">
			<form:textField label="Number of Sub-Projects" name="subProjectCount" value="1"/>
			<g:each var="subNum" in="${(1..subProjectCount)}">
				<form:textField label="Sub-project ${subNum} Prefix" name="subProject${subNum}Prefix"/>
			</g:each>
				
		</gui:expandablePanel>
		<gui:expandablePanel title="Architecture Info" expanded="true">
		</gui:expandablePanel>
		<form:buttonBar>
			<g:submitButton name="next" value="Next"></g:submitButton>
			<g:submitButton name="cancel" value="Cancel"></g:submitButton>
		</form:buttonBar>
	</g:form>
    </formset>
    </body>
</html>
