package com.ssg.market.domain.orderItem.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.market.data.model.entity.OrderItem;
import com.ssg.market.data.model.enumerate.OrderItemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssg.market.data.model.entity.QItem.*;
import static com.ssg.market.data.model.entity.QOrder.*;
import static com.ssg.market.data.model.entity.QOrderItem.*;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderItem> findByOrderAndStatus(Long orderId) {
        return queryFactory.selectFrom(orderItem)
                .distinct()
                .join(orderItem.order, order).fetchJoin()
                .join(orderItem.item, item).fetchJoin()
                .where(orderItem.order.id.eq(orderId), orderItem.status.eq(OrderItemStatus.ORDERED)
                ).fetch();
    }
}
