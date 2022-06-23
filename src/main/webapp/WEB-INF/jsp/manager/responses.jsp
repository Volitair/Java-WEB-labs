<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Responses</title>
</head>
<body>
<div>
    <a href="${pageContext.request.contextPath}/app/home"><h3>Home</h3></a>
    <h2>Responses</h2>
</div>
<table>
    <thead>
    <tr>
        <th>№</th>
        <th>Master</th>
        <th>Response</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="responsesList" scope="request" type="java.util.List"/>
    <c:forEach items="${responsesList}" var="response" varStatus="i">
        <tr>
            <td><c:out value="${i.count}"/></td>
            <td><c:out value="${response.master.surname} ${response.master.name}"/></td>
            <td><c:out value="${response.response}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
