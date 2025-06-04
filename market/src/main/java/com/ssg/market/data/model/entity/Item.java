package com.ssg.market.data.model.entity;

import com.ssg.market.global.errorhandling.exception.ItemStockNotEnoughException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "ITEMS")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseTimeEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @Column(name = "ORIGINAL_PRICE", columnDefinition = "INT", nullable = false)
    private Integer originalPrice;

    @Column(name = "DISCOUNT_PRICE", columnDefinition = "INT", nullable = false)
    private Integer discountPrice;

    @Column(name = "STOCK", columnDefinition = "INT", nullable = false)
    private Integer stock;

    public void decreaseStock(Integer quantity) {
        if (stock < quantity) {
            throw new ItemStockNotEnoughException();
        }
        this.stock -= quantity;
    }

    public void updateStock(int quantity) {
        this.stock += quantity;
    }

    public Integer calculateFinalPrice(int quantity) {
        return (this.originalPrice - this.discountPrice) * quantity;
    }
}
