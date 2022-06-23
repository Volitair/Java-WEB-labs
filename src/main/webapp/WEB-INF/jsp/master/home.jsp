<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Master Home Page</title>
</head>
<body>
<div>
    <c:if test="${sessionScope.userLogin != null}">
        <a href="${pageContext.request.contextPath}/app/logout">Logout</a>
    </c:if>
</div>
<jsp:useBean id="orderList" scope="request" type="java.util.List"/>
<div>
    <h2>My Orders:</h2>
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
                <td>
                    <a type="button" href="${pageContext.request.contextPath}/app/orders/edit-form?orderId=${order.id}">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
