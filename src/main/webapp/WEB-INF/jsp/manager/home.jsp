<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Manager home page</title>
</head>
<body>
<div>
    <c:if test="${sessionScope.userLogin != null}">
        <a href="${pageContext.request.contextPath}/app/logout">Logout</a>
    </c:if>
</div>
<div>
    <div>
        <a type="button" href="${pageContext.request.contextPath}/app/orders"><h3>Orders</h3></a>
        <a type="button" href="${pageContext.request.contextPath}/app/responses"><h3>Responses</h3></a>
        <a type="button" href="${pageContext.request.contextPath}/app/users"><h3>Users</h3></a>
    </div>
</div>
</body>
</html>
