package com.ssg.market.domain.order.api.create.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "주문 생성 입력 DTO")
public class OrderCreateReqDTO {

    @Schema(description = "상품 ID")
    @NotNull(message = "상품 ID는 필수입니다.")
    private Long itemId;

    @Schema(description = "수량")
    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    private Integer quantity;
}
