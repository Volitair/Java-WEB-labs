<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order create form</title>
</head>
<body>
<h2>Form</h2>
<form method="post" action=${pageContext.request.contextPath}/app/orders/create>
    <label>Description: <input type="text" name="description"></label>

    <input type="submit" value="Create">
</form>
</body>
</html>
