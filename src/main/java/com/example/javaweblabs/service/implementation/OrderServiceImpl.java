package com.example.javaweblabs.service.implementation;

import com.example.javaweblabs.dao.abstraction.CashAccountDao;
import com.example.javaweblabs.dao.abstraction.DaoFactory;
import com.example.javaweblabs.dao.abstraction.OrderDao;
import com.example.javaweblabs.dao.implementation.ConnectionPoolHolder;
import com.example.javaweblabs.dao.implementation.DefaultDaoFactory;
import com.example.javaweblabs.entity.CashAccount;
import com.example.javaweblabs.entity.Order;
import com.example.javaweblabs.enums.OrderStatus;
import com.example.javaweblabs.enums.Role;
import com.example.javaweblabs.service.abstraction.OrderService;
import com.example.javaweblabs.util.OrderFilterProperties;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderServiceImpl implements OrderService {
    private final DaoFactory daoFactory = DefaultDaoFactory.getInstance();
    private final HashMap<OrderStatus, OrderStatus[]> possibleStatusesToChange = new HashMap<>();
    private final HashMap<Role, OrderStatus[]> possibleStatusesToChangeByRole = new HashMap<>();
    private final ArrayList<OrderStatus> orderStatusesToShowForMaster = new ArrayList<>();

    public OrderServiceImpl() {
        possibleStatusesToChange.put(OrderStatus.REGISTERED, new OrderStatus[]{OrderStatus.CANCELED, OrderStatus.IN_WORK});
        possibleStatusesToChange.put(OrderStatus.IN_WORK, new OrderStatus[]{OrderStatus.DONE});
        possibleStatusesToChange.put(OrderStatus.DONE, new OrderStatus[]{OrderStatus.WAITING_FOR_PAYMENT});
        possibleStatusesToChange.put(OrderStatus.WAITING_FOR_PAYMENT, new OrderStatus[]{OrderStatus.PAID});
        possibleStatusesToChange.put(OrderStatus.PAID, new OrderStatus[]{OrderStatus.PAID});

        possibleStatusesToChangeByRole.put(Role.MASTER, new OrderStatus[]{OrderStatus.IN_WORK, OrderStatus.DONE});
        possibleStatusesToChangeByRole.put(Role.MANAGER, new OrderStatus[]{OrderStatus.WAITING_FOR_PAYMENT, OrderStatus.PAID, OrderStatus.CANCELED});

        orderStatusesToShowForMaster.addAll(Arrays.asList(OrderStatus.REGISTERED, OrderStatus.IN_WORK, OrderStatus.DONE));
    }

    @Override
    public boolean create(Order order) {
        try (OrderDao orderDao = daoFactory.getOrderDao(ConnectionPoolHolder.getConnection())) {
            return orderDao.create(order);
        }
    }

    @Override
    public List<Order> findAll() {
        try (OrderDao orderDao = daoFactory.getOrderDao(ConnectionPoolHolder.getConnection())) {
            List<Order> orderList = orderDao.findAll();
            return orderList;
        }
    }

    @Override
    public List<Order> findAll(OrderFilterProperties orderFilter) {
        try (OrderDao orderDao = daoFactory.getOrderDao(ConnectionPoolHolder.getConnection())) {
            Predicate<Order> orderStatusFilter = new Predicate<Order>() {
                @Override
                public boolean test(Order order) {
                    return orderFilter.getOrderStatus() == null || orderFilter.getOrderStatus().equals(order.getOrderStatus());
                }
            };
            Stream<Order> orderStream = orderDao.findAll().stream()
                    .filter(orderStatusFilter)
                    .filter(order -> orderFilter.getMasterId() == null || orderFilter.getMasterId().equals(order.getMaster().getId()));
            return orderStream.collect(Collectors.toList());
        }
    }

    @Override
    public List<Order> getUserOrders(Integer userId) {
        try (OrderDao orderDao = daoFactory.getOrderDao(ConnectionPoolHolder.getConnection())) {
            return orderDao.findUserOrders(userId);
        }
    }

    @Override
    public boolean update(Order order) {
        try (OrderDao orderDao = daoFactory.getOrderDao(ConnectionPoolHolder.getConnection())) {
            return orderDao.update(order);
        }
    }


    @Override
    public boolean changeOrderStatus(Order order, OrderStatus newStatus) {
        if (!canChangeOrderStatus(order.getOrderStatus(), newStatus)) return false;
        if (newStatus == OrderStatus.DONE && !(order.getOrderStatus() == OrderStatus.DONE))
            order.setEndDate(LocalDate.now());
        order.setOrderStatus(newStatus);
        return true;
    }

    @Override
    public Optional<Order> findById(Integer id) {
        try (OrderDao orderDao = daoFactory.getOrderDao(ConnectionPoolHolder.getConnection())) {
            return orderDao.findById(id);
        }
    }

    @Override
    public List<OrderStatus> getPossibleNewStatuses(OrderStatus orderStatus, Role role) {
        ArrayList<OrderStatus> orderStatusesByRole = new ArrayList<>(Arrays.asList(possibleStatusesToChangeByRole.get(role)));
        return Arrays.stream(possibleStatusesToChange.get(orderStatus))
                .filter(orderStatusesByRole::contains)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getMasterOrders(Integer masterId) {
        try (OrderDao orderDao = daoFactory.getOrderDao(ConnectionPoolHolder.getConnection())) {
            List<Order> masterOrders = orderDao.findMasterOrders(masterId);
            List<Order> filteredMasterOrders = masterOrders.stream()
                    .filter(order -> orderStatusesToShowForMaster.contains(order.getOrderStatus()))
                    .collect(Collectors.toList());
            return filteredMasterOrders;
        }
    }

    @Override
    public boolean payOrder(Integer orderId) {
        Connection connection = ConnectionPoolHolder.getConnection();
        try (OrderDao orderDao = daoFactory.getOrderDao(connection);
             CashAccountDao cashAccountDao = daoFactory.getCashAccountDao(connection)) {
            Optional<Order> orderOptional = orderDao.findById(orderId);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                Integer price = order.getPrice();
                Integer userId = order.getUser().getId();
                Optional<CashAccount> cashAccountOptional = cashAccountDao.findByUserId(userId);
                if (!cashAccountOptional.isPresent()) return false;
                CashAccount cashAccount = cashAccountOptional.get();
                Integer balance = cashAccount.getBalance();
                if (balance > price) {
                    cashAccount.setBalance(cashAccount.getBalance() - price);
                    cashAccountDao.update(cashAccount);
                    order.setOrderStatus(OrderStatus.PAID);
                    orderDao.update(order);
                }
            }
            return false;
        }
    }

    private boolean canChangeOrderStatus(OrderStatus oldStatus, OrderStatus newStatus) {
        ArrayList<OrderStatus> possibleNewStatuses = new ArrayList<>(Arrays.asList(possibleStatusesToChange.get(oldStatus)));
        return possibleNewStatuses.contains(newStatus);
    }
}
