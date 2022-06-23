package com.example.javaweblabs.dao.abstraction;

import com.example.javaweblabs.entity.Order;

import java.util.List;

public interface OrderDao extends CrudDao<Integer, Order>, AutoCloseable {

    List<Order> findUserOrders(Integer userId);

    List<Order> findMasterOrders(Integer masterId);

    @Override
    void close();
}
