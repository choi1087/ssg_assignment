package com.ssg.market.domain.order.api.read.dto.res;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Schema(description = "주문 상품 조회 출력 DTO")
@Getter
public class OrderGetItemListResDTO {
    @Schema(description = "취소된 상품 정보")
    private List<ItemInfoDTO> itemInfoDTOList;

    @Schema(description = "주문 전체 금액")
    private Integer totalPrice;

    @Builder
    @Getter
    public static class ItemInfoDTO {
        private Long itemId;
        private String itemName;
        private Integer quantity;
        private Integer finalPrice;
    }
}
