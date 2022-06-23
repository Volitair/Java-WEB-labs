package com.example.javaweblabs.service.abstraction;

import com.example.javaweblabs.entity.Order;
import com.example.javaweblabs.enums.OrderStatus;
import com.example.javaweblabs.enums.Role;
import com.example.javaweblabs.util.OrderFilterProperties;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    boolean create(Order order);

    List<Order> findAll();

    List<Order> findAll(OrderFilterProperties orderFilter);

    List<Order> getUserOrders(Integer userId);

    boolean update(Order order);

    boolean changeOrderStatus(Order order, OrderStatus status);

    Optional<Order> findById(Integer id);

    List<OrderStatus> getPossibleNewStatuses(OrderStatus orderStatus, Role role);

    List<Order> getMasterOrders(Integer masterId);

    boolean payOrder(Integer orderId);
}
