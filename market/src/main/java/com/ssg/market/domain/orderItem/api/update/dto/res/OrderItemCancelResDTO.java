package com.ssg.market.domain.orderItem.api.update.dto.res;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Schema(description = "주문 상품 개별 취소 출력 DTO")
@Getter
public class OrderItemCancelResDTO {
    @Schema(description = "취소된 상품 ID")
    private Long itemId;

    @Schema(description = "취소된 상품명")
    private String itemName;

    @Schema(description = "취소된 상품 수량")
    private Integer quantity;

    @Schema(description = "환불 금액")
    private Integer refundPrice;

    @Schema(description = "남은 전체 주문 금액")
    private Integer totalPrice;
}
