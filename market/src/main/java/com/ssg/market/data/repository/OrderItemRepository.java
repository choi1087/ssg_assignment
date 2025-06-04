package com.ssg.market.data.repository;

import com.ssg.market.data.model.entity.Item;
import com.ssg.market.data.model.entity.Order;
import com.ssg.market.data.model.entity.OrderItem;
import com.ssg.market.domain.orderItem.repository.OrderItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemRepositoryCustom {
    Optional<OrderItem> findByOrderAndItem(Order order, Item item);
}
