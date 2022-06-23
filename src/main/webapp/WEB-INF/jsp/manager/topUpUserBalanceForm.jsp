<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Surname</th>
        <th>Balance</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><c:out value="${user.name}"/></td>
        <td><c:out value="${user.surname}"/></td>
        <td><c:out value="${userBalance.balance}"/></td>
    </tr>
    </tbody>
</table>
<form method="post" action=${pageContext.request.contextPath}/app/users/cash-account/top-up>
    <input type="number" name="userId" value="${user.id}" hidden>
    <label>Deposit:
        <input type="number" name="deposit" min="0">
    </label>

    <input type="submit" value="Top up balance">
</form>
</body>
</html>
