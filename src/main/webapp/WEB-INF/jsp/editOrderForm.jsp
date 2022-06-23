<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit order</title>
</head>
<body>
<h2>Order</h2>
<jsp:useBean id="order" scope="request" type="com.example.javaweblabs.entity.Order"/>

<c:if test="${order != null}">
    <table>
        <thead>
        <tr>
            <th>Order Id</th>
            <th>Order status</th>
            <th>Price</th>
            <th>Start date</th>
            <th>End date</th>
            <th>Master</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <c:out value="${order.orderStatus}"/>
            </td>
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
        </tr>
        </tbody>
    </table>

    <form method="post" action=${pageContext.request.contextPath}/app/orders/edit>
        <h3>Form</h3>
        <input hidden type="text" name="orderId" value="${order.id}">
        <label>
            New Order Status
            <select name="orderStatus">
                <option value="${order.orderStatus}" selected>
                    <c:out value="${order.orderStatus}"/>
                </option>
                <c:forEach var="newStatus" items="${possibleNewOrderStatuses}">
                    <option value="${newStatus}">
                        <c:out value="${newStatus}"/>
                    </option>
                </c:forEach>
            </select>
                <%-- <c:set var="price" scope="request" value="${order.price}"/>--%>
        </label>
        <c:if test="${sessionScope.get('userRole') == 'MANAGER'}">
            <label>
                New Master
                <select name="masterId">
                    <option value=""></option>
                    <c:forEach var="master" items="${masters}">
                        <option value="${master.id}" ${master.id == order.master.id ? 'selected' : ''}>
                            <c:out value="${master.surname} ${master.name}"/>
                        </option>
                    </c:forEach>
                </select>
            </label>
            <label>
                New Price
                <input type="number" name="price" min="0" value="${order.price}">
            </label>
        </c:if>
        <input type="submit" value="Edit">
    </form>
</c:if>
</body>
</html>
