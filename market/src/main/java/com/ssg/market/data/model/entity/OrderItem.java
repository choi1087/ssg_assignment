package com.ssg.market.data.model.entity;

import com.ssg.market.data.model.enumerate.OrderItemStatus;
import com.ssg.market.global.errorhandling.BusinessException;
import com.ssg.market.global.errorhandling.ErrorCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "ORDER_ITEMS")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", foreignKey = @ForeignKey(name = "FK_ORDER_ORDER_ITEM_MAPPING"))
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", foreignKey = @ForeignKey(name = "FK_ITEM_ORDER_ITEM_MAPPING"))
    private Item item;

    @Column(name = "QUANTITY", columnDefinition = "INT", nullable = false)
    private Integer quantity;

    @Column(name = "FINAL_PRICE", columnDefinition = "INT", nullable = false)
    private Integer finalPrice;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 100, nullable = false)
    private OrderItemStatus status = OrderItemStatus.ORDERED;

    public static OrderItem create(Order order, Item item, Integer quantity) {
        item.decreaseStock(quantity); // 재고 차감
        Integer finalPrice = item.calculateFinalPrice(quantity);

        return OrderItem.builder()
                .order(order)
                .item(item)
                .quantity(quantity)
                .finalPrice(finalPrice)
                .build();
    }

    public void cancel() {
        // 이미 취소된 상품에 대한 재취소 요청 시 에러 처리
        if(this.status == OrderItemStatus.CANCELLED) {
            throw new BusinessException(ErrorCode.ORDER_ITEM_ALREADY_DELETED);
        }

        this.item.updateStock(this.quantity); // 취소된 상품 재고 복구
        this.order.refund(this.finalPrice); // 남은 전체 주문 금액 갱신
        this.status = OrderItemStatus.CANCELLED; // 취소 처리
    }
}
