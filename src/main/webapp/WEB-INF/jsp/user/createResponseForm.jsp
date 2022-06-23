<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Response create form</title>
</head>
<body>
<h2>Form</h2>
<form method="post" action=${pageContext.request.contextPath}/app/responses/create>
    <input type="number" name="orderId" value="${orderId}" hidden>
    <label>Response: <input type="text" name="response"></label>

    <input type="submit" value="Create">
</form>
</body>
</html>
