package com.ssg.market.domain.item.api.read.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Schema(description = "상품 전체 조회 출력 DTO")
@Getter
public class ItemGetAllResDTO {

    @Schema(description = "상품 아이디")
    private Long itemId;

    @Schema(description = "상품명")
    private String itemName;

    @Schema(description = "판매가격")
    private Integer originalPrice;

    @Schema(description = "할인금액")
    private Integer discountPrice;

    @Schema(description = "재고")
    private Integer stock;
}
