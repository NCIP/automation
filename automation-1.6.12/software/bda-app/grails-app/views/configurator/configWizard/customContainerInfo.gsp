<html>
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="layout" content="main" />
	<gui:resources components="['expandablePanel','toolTip','tabView']"/>
        <title>Build Details</title>
    </head>
    <body>
    <g:form action="configWizard">
    <g:if test="${flash.message}">
	<div class="errors"> 
		${flash.message}        
	</div>          
    </g:if> 

    <gui:tabView id="customContainers">
    <gui:tab id='custom1' label='Custom Prefix 1' active="true">
	<g:render template="customTemplate" model="['varPrefix':'custom1Post']" />
    </gui:tab>
    </gui:tabView>
		<form:buttonBar>
			<g:submitButton name="previous" value="Previous"></g:submitButton>
			<g:submitButton name="next" value="Next"></g:submitButton>
			<g:submitButton name="cancel" value="Cancel"></g:submitButton>
		</form:buttonBar>
	</g:form>
    </body>
</html>
