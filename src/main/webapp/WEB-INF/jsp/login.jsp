<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div>
    <c:if test="${sessionScope.userLogin != null}">
        <a href="${pageContext.request.contextPath}/app/home">Home</a>
    </c:if>
    <%--    <c:if test="${sessionScope.userLogin == null}">--%>
    <%--        <a href="${pageContext.request.contextPath}/app/login"></a>--%>
    <%--    </c:if>--%>
    <c:if test="${sessionScope.userLogin != null}">
        <a href="${pageContext.request.contextPath}/app/logout">Logout</a>
    </c:if>
</div>
<div>
    <c:if test="${sessionScope.userLogin == null}">
        <form id="loginForm" method="post" action="${pageContext.request.contextPath}/app/login">
            <div>
                <label>Login:<input class="form-control" type="text" name="login"></label>
            </div>
            <div>
                <label>Password:<input class="form-control" type="password" name="password"></label>
            </div>
            <div>
                <input type="submit" value="Login">
            </div>
        </form>
    </c:if>
</div>
</body>
</html>
