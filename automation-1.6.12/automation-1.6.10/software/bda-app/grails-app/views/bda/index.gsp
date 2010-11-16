<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="layout" content="main" />
	<title>BDA 2.0</title>
	<g:javascript library="jquery" />
	 <style>
		.fieldWrapper { margin-bottom: 20px;}

		.fieldWrapper label { float: left; width: 150px; }

		.fieldWrapper .fieldText { margin-left: 150px; }

		.fieldWrapper .fieldText .error { color: red; }

		.fieldWrapper .error label { color: red; }

		.fieldWrapper .radioWrapper { margin-left: 150px; } 
	</style>
    
	<gui:resources components="['expandablePanel','dialog','tabView']"/>
</head>

<body>
	<formset>
		<legend><b>BDA 2.0</b></legend>
			<g:if test="${flash.message}">
				<div class="errors">
					${flash.message}
				</div>
			</g:if>
			<gui:dialog
			    id="subProjectDialog"
			    title="Sub Project Configuration Dialog"
			    draggable="true"
			    form="true"		    
			    width="1000px"
			    heigth="1000px"
			    modal="true"
			    triggers="[show:[id:'subProjectConfiguration', on:'click']]">
			       <gui:tabView id="myTabView">
				    <gui:tab label="Sub Project Configuration" active="true">
					<form:textField label="Sub-Project Name "  name="sourceFolder" title="TextField" readonly="false" />
					<form:textField label="Project Source Folder "  name="sourceFolder" title="TextField" readonly="false" />
					<form:textField label="Project Build Target"  name="sourceFolder" title="TextField" readonly="false" />
					<form:textField label="Project Test Target "  name="sourceFolder" title="TextField" readonly="false" />					
				    </gui:tab>			       
				    <gui:tab label="Application Server Configuration" >
				    	<table>
				    		<tr><td><label>Application Server Type</label>&nbsp;&nbsp;&nbsp;&nbsp;<g:select label="Application Server Type " name="subProjectApplicationServerType" from="['jboss','tomcat']" value="${subProjectApplicationServerType}" noSelection="['':'-Choose Application Server Type-']"/></td></tr>

						<tr><td><label>Application Server Version</label>&nbsp;&nbsp;&nbsp;&nbsp;<g:select label="Application Server Type " name="subProjectApplicationServerVersion" from="['4.0.4','4.0.5']" value="${subProjectApplicationServerVersion}" /></td></tr>		
					</table>
				    </gui:tab>
				    <gui:tab label="Database Configuration">
				    	<table>
					    	<tr><td><label>Database Type</label>&nbsp;&nbsp;&nbsp;&nbsp;<g:select label="Database Type " name="subProjectDatabaseType" from="['mysql','oracle']" value="${subProjectApplicationServerType}" noSelection="['':'-Choose Database Type-']"/></td></tr>
	
						<tr><td><label>Database Version</label>&nbsp;&nbsp;&nbsp;&nbsp;<g:select label="Database Type " name="subProjectDatabaseVersion" from="['5.0.27','5.1.30']" value="${subProjectDatabaseVersion}" /></td></tr>		
					</table>
				    </gui:tab>
				</gui:tabView>
			    
			</gui:dialog>		

			<gui:expandablePanel title="Application Configuration" expanded="true">
				
					<form:textField label="Project Name "  name="projectName" title="TextField" readonly="false" />
					<form:textField label="Number of Sub Projects "  name="numberSubProjects" title="TextField" readonly="false" />
					<form:textField label="SVN Repository "  name="numberSubProjects" title="TextField" readonly="false" />

					<form:buttonBar>
						<button id="subProjectConfiguration">Add Sub-Project Configuration</button>
					 </form:buttonBar>					    
				
			</gui:expandablePanel>
			
			<gui:expandablePanel title="LDAP Configuration">
					<form:textField label="LDAP URL "  name="ldapUrl" title="TextField" readonly="false" />
			</gui:expandablePanel>
</br>
		<form:buttonBar>
		  <button id="save">Save</button>
		 </form:buttonBar>			
	</formset>
</body>
</html>