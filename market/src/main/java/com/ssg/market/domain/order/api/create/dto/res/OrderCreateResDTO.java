package com.ssg.market.domain.order.api.create.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Schema(description = "주문 생성 출력 DTO")
@Getter
public class OrderCreateResDTO {

    @Schema(description = "주문 번호")
    private Long orderId;

    @Schema(description = "주문 전체 금액")
    private Integer totalPrice;

    @Schema(description = "각 주문 상품의 실구매금액")
    private List<ItemInfoDTO> itemInfoList;

    @Builder
    @Getter
    public static class ItemInfoDTO {
        private Long itemId;
        private Integer finalPrice;
    }
}
