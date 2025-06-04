package com.ssg.market.domain.orderItem.repository;

import com.ssg.market.data.model.entity.OrderItem;

import java.util.List;

public interface OrderItemRepositoryCustom {
    List<OrderItem> findByOrderAndStatus(Long orderId);
}
