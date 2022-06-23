<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
<div>
    <a href="${pageContext.request.contextPath}/app/home"><h3>Home</h3></a>
    <h2>Orders</h2>
    <div>
        <h3>Search:</h3>
        <form method="post" action="${pageContext.request.contextPath}/app/orders/search">
            <label>Order status:
                <select name="orderStatus">
                    <option value=""></option>
                    <c:forEach var="status" items="${orderStatuses}">
                        <option value="${status}" ${status == selectedOrderStatus ? 'selected' : ''}>
                            <c:out value="${status}"/>
                        </option>
                    </c:forEach>
                </select>
            </label>
            <label>Master:
                <select name="masterId">
                    <option value=""></option>
                    <c:forEach var="master" items="${masters}">
                        <option value="${master.id}" ${master.id == selectedMasterId ? 'selected' : ''}>
                            <c:out value="${master.surname} ${master.name}"/>
                        </option>
                    </c:forEach>
                </select>
            </label>
            <input type="submit" value="Find">
            <div>
                <h3>Sort by:</h3>
                <div>
                    <label>
                        <input type="radio" name="sortBy" value="price" ${sortingChecked == 'price' ? 'checked' : ''}>
                        Price
                    </label>
                    <label>
                        <input type="radio" name="sortBy" value="order-status" ${sortingChecked == 'order-status' ? 'checked' : ''}>
                        Order status
                    </label>
                    <label>
                        <input type="radio" name="sortBy" value="start-date" ${sortingChecked == 'start-date' ? 'checked' : ''}>
                        Start date
                    </label>
                </div>
            </div>
            <input type="submit" value="Sort">
        </form>
    </div>

    <jsp:useBean id="orderList" scope="request" type="java.util.List"/>
    <c:if test="${fn:length(orderList) > 0}">
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
                    <td><a type="button" href="${pageContext.request.contextPath}/app/orders/edit-form?orderId=${order.id}">Edit</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

</body>
</html>
