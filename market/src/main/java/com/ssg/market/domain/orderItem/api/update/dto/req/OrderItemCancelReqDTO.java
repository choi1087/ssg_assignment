package com.ssg.market.domain.orderItem.api.update.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "주문 상품 개별 취소 입력 DTO")
public class OrderItemCancelReqDTO {

    @Schema(description = "주문번호")
    @NotNull(message = "주문번호는 필수입니다.")
    private Long orderId;

    @Schema(description = "취소할 상품 ID")
    @NotNull(message = "상품 ID는 필수입니다.")
    private Long itemId;
}
