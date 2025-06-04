package com.ssg.market.domain.order.api.create.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "주문 생성 입력 리스트 DTO")
public class OrderCreateReqListDTO {
    @NotEmpty(message = "주문 항목은 1개 이상이어야 합니다.")
    @Valid
    private List<OrderCreateReqDTO> ordeReqDTOList;
}
