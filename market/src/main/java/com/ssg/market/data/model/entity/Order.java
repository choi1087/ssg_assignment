package com.ssg.market.data.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "ORDERS")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "ORDER_ID_SEQ_GEN", sequenceName = "ORDER_SEQ")
public class Order extends BaseTimeEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_ID_SEQ_GEN")
    private Long id;

    @Builder.Default
    @Column(name = "TOTAL_PRICE", columnDefinition = "INT DEFAULT 0", nullable = false)
    private Integer totalPrice = 0;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        this.orderItemList.add(orderItem);
        this.totalPrice += orderItem.getFinalPrice();
    }

    public void refund(Integer refundPrice) {
        this.totalPrice += refundPrice;
    }
}
