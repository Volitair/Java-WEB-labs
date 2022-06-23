package com.example.javaweblabs.util;

import com.example.javaweblabs.enums.OrderStatus;

public class OrderFilterProperties {
    private OrderStatus orderStatus;
    private Integer masterId;

    public OrderFilterProperties(OrderStatus orderStatus, Integer masterId) {
        this.orderStatus = orderStatus;
        this.masterId = masterId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }
}
