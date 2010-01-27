<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head><title>Hello World :: BDA Certification Dashboard</title></head>
<body>
<h1>Hello - Spring version of BDA Certification Dasboard</h1>

<p>Greetings, it is now <c:out value="${now}"/></p>

<table>
    <tr>
        <th>Products</th>
        <th>BDA Enabled</th>
    </tr>
    <c:forEach items="${model.products}" var="prod">
        <tr>
            <td><c:out value="${prod.name}"/></td>
            <td>$<c:out value="${prod.bdaEnabled}"/></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>