<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title>Hello World :: BDA Certification Dashboard</title>
    <link rel="stylesheet" type="text/css" href="styles.css"/>
</head>
<body style="font-family:Arial, Helvetica, sans-serif">
<h1>Hello - Spring version of BDA Certification Dasboard</h1>

<p>Greetings, it is now <c:out value="${model.now}"/></p>

<table>
    <tr>
        <th>Products</th>
        <th>Certification-Status</th>
        <th>BDA-Enabled</th>
        <th>Single-Command-Build</th>
        <th>Single-Command-Deployment</th>
        <th>Remote-Upgrader</th>
        <th>Database-Integration</th>
        <th>Private-Properties</th>
        <th>Deployment-Shakeout</th>
        <th>Template-Validation</th>
        <th>CI-Build</th>
        <th>CommandLine-Installer</th>
    </tr>
    <c:forEach items="${model.products}" var="prod">
        <tr>
            <td><c:out value="${prod.name}"/></td>
            
            <td class='<c:out value="${prod.certificationStatus.status}"/>'>
                <c:out value="${prod.certificationStatus.status}"/>
            </td>

            <td class='<c:out value="${prod.bdaEnabled.status}"/>'>
                <c:out value="${prod.bdaEnabled.status}"/>
            </td>

            <td class='<c:out value="${prod.singleCommandBuild.status}"/>'>
                <c:out value="${prod.singleCommandBuild.status}"/>
            </td>

            <td class='<c:out value="${prod.singleCommandDeploy.status}"/>'>
                <c:out value="${prod.singleCommandDeploy.status}"/>
            </td>

            <td class='<c:out value="${prod.remoteUpgrade.status}"/>'>
                <c:out value="${prod.remoteUpgrade.status}"/>
            </td>

            <td class='<c:out value="${prod.dbIntegration.status}"/>'>
                <c:out value="${prod.dbIntegration.status}"/>
            </td>

            <td class='<c:out value="${prod.privateProperties.status}"/>'>
                <c:out value="${prod.privateProperties.status}"/>
            </td>

            <td class='<c:out value="${prod.deploymentShakeout.status}"/>'>
                <c:out value="${prod.deploymentShakeout.status}"/>
            </td>

            <td class='<c:out value="${prod.templateValidation.status}"/>'>
                <c:out value="${prod.templateValidation.status}"/>
            </td>

            <td class='<c:out value="${prod.ciBuild.status}"/>'>
                <c:out value="${prod.ciBuild.status}"/>
            </td>

            <td class='<c:out value="${prod.commandLineInstall.status}"/>'>
                <c:out value="${prod.commandLineInstall.status}"/>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>