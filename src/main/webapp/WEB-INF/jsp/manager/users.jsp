<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<jsp:useBean id="usersList" scope="request" type="java.util.List"/>
<a href="${pageContext.request.contextPath}/app/home"><h3>Home</h3></a>
<table>
    <thead>
    <tr>
        <th>№</th>
        <th>Name</th>
        <th>Surname</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${usersList}" var="user" varStatus="i">
        <tr>
            <td><c:out value="${i.count}"/></td>
            <td><c:out value="${user.name}"/></td>
            <td><c:out value="${user.surname}"/></td>
            <td><a type="button"
                   href="${pageContext.request.contextPath}/app/users/cash-account/top-up-form?userId=${user.id}">Top up balance</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
