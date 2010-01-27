<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head><title>Hello World :: BDA Certification Dashboard</title></head>
<body>
<h1>Hello - Spring version of BDA Certification Dasboard</h1>

<p>Greetings, it is now <c:out value="${model.now}"/></p>

<table border="1" width="95%" style="font-size:.6em">
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
        <th>CommandLineInstaller</th>
    </tr>
    <c:forEach items="${model.products}" var="prod">
        <tr>
            <td><c:out value="${prod.name}"/></td>
            <td>$<c:out value="${prod.certificationStatus}"/></td>
            <td>$<c:out value="${prod.bdaEnabled}"/></td>
            <td>$<c:out value="${prod.singleCommandBuild}"/></td>
            <td>$<c:out value="${prod.singleCommandDeploy}"/></td>
            <td>$<c:out value="${prod.remoteUpgrade}"/></td>
            <td>$<c:out value="${prod.dbIntegration}"/></td>
            <td>$<c:out value="${prod.privateProperties}"/></td>
            <td>$<c:out value="${prod.deploymentShakeout}"/></td>
            <td>$<c:out value="${prod.templateValidation}"/></td>
            <td>$<c:out value="${prod.ciBuild}"/></td>
            <td>$<c:out value="${prod.commandLineInstall}"/></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>