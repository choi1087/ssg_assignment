package com.ssg.market.domain.order.api.create.controller;

import com.ssg.market.domain.order.api.create.dto.req.OrderCreateReqListDTO;
import com.ssg.market.domain.order.api.create.dto.res.OrderCreateResDTO;
import com.ssg.market.domain.order.api.create.service.OrderCreateService;
import com.ssg.market.global.response.ErrorResponse;
import com.ssg.market.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "ORDER", description = "주문 생성 API")
@RequestMapping("/order/create")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Invalid Input Error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
})
public class OrderCreateController {

    private final OrderCreateService orderCreateService;

    @Operation(summary = "(필수) 주문 생성",
            description = "새로운 주문을 생성합니다.")
    @PostMapping(value = "", produces = "application/json; charset=utf-8")
    public ResponseEntity<SuccessResponse<OrderCreateResDTO>> createOrder(
            @RequestBody @Valid OrderCreateReqListDTO reqDTOListDTO
    ) {
        return SuccessResponse.success(orderCreateService.createOrder(reqDTOListDTO.getOrdeReqDTOList()));
    }
}
