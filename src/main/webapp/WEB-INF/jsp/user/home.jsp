<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User home page</title>
</head>
<body>
<div>
    <c:if test="${sessionScope.userLogin != null}">
        <a href="${pageContext.request.contextPath}/app/logout">Logout</a>
    </c:if>
</div>
<div>
    <jsp:useBean id="cashAccount" scope="request" type="com.example.javaweblabs.entity.CashAccount"/>
    <h2>User balance: <c:out value="${cashAccount.balance}"/></h2>
</div>
<div>
    <h2>Orders</h2>
    <table>
        <thead>
        <tr>
            <th>№</th>
            <th>Order status</th>
            <th>Price</th>
            <th>Start date</th>
            <th>End date</th>
            <th>Master</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <jsp:useBean id="orderList" scope="request" type="java.util.List"/>
        <c:forEach items="${orderList}" var="order" varStatus="i">
            <tr>
                <td><c:out value="${i.count}"/></td>
                <td><c:out value="${order.orderStatus}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${order.price == null}">
                            price not announced
                        </c:when>
                        <c:otherwise>
                            <c:out value="${order.price}"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${order.startDate}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${order.endDate == null}">
                            -
                        </c:when>
                        <c:otherwise>
                            <c:out value="${order.endDate}"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${order.master.surname} ${order.master.name}"/></td>
                <td><c:out value="${order.description}"/></td>
                <c:set var="status" value="${order.orderStatus}"/>
                <c:if test="${status == 'DONE' || status == 'WAITING_FOR_PAYMENT' || status == 'PAID'}">
                    <td>
                        <a type="button" href="${pageContext.request.contextPath}/app/responses/create-form?orderId=${order.id}">Make a response</a>
                    </td>
                </c:if>
                <c:if test="${status == 'WAITING_FOR_PAYMENT'}">
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/app/orders/pay">
                            <input type="number" name="orderId" value="${order.id}" hidden>
                            <input type="submit" value="Pay">
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div>
    <a type="button"
       href="${pageContext.request.contextPath}/app/orders/create-form">Create new order</a>
</div>
</body>
</html>
