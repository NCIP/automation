<html>
<title>bda-blueprints</title>
<head>
<link href="stylesheets/onsq.css" rel="stylesheet" type="text/css" />
</head>

<%
bda.blueprints.business.service.StudyService studyService = new bda.blueprints.business.service.StudyServiceImpl();
java.util.Collection studies =  studyService.findAll();
java.util.Iterator itor = studies.iterator();
String name = null;
String researcher = null;
String dateReceived = null;
%>

<BODY BGCOLOR="White" LINK="#FFAA00" VLINK="#FFAA00" ALINK="#330066" LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH="0" MARGINHEIGHT="0">
<FORM action="/bda-blueprints/Controller" method="post">
<INPUT TYPE="hidden" NAME="FROM_PAGE" VALUE="study">
<INPUT TYPE="hidden" NAME="TO_PAGE" VALUE="editstudy">
<table align="center">
  <tr><td colspan="2"><b>Study</b></td></tr>
  <%
  	while (itor.hasNext()) {
       bda.blueprints.business.domain.Study study = (bda.blueprints.business.domain.Study) itor.next(); 
       name = study.getName();
       researcher = study.getResearcher();
       dateReceived = study.getDateReceived();
  %>    
   <tr>
   <td><%=name%></td>
   <td><%=researcher%></td>
   <td><%=dateReceived%></td>
   </tr>
   <%
     }
   %>
   <tr>
     <td><input type="submit" name="add" id="add" value="Add New Study" /></td>
   </tr>
   <%= request.getAttribute("userMessage") %>
</table>
</FORM>
</body>
</html>