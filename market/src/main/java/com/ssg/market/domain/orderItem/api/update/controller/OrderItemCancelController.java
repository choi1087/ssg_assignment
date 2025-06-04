package com.ssg.market.domain.orderItem.api.update.controller;

import com.ssg.market.domain.orderItem.api.update.dto.req.OrderItemCancelReqDTO;
import com.ssg.market.domain.orderItem.api.update.dto.res.OrderItemCancelResDTO;
import com.ssg.market.domain.orderItem.api.update.service.OrderItemCancelService;
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

import static com.ssg.market.global.response.SuccessResponse.success;

@RestController
@Tag(name = "ORDER_ITEM_CANCEL", description = "주문 취소 API")
@RequestMapping("/order/cancel")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Invalid Input Error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
})
public class OrderItemCancelController {

    private final OrderItemCancelService orderItemCancelService;

    @Operation(summary = "(필수) 주문 상품 개별 취소",
            description = "주문 내의 상품을 취소 처리한다.")
    @PostMapping(value = "", produces = "application/json; charset=utf-8")
    public ResponseEntity<SuccessResponse<OrderItemCancelResDTO>> cancelOrderItem(
            @RequestBody @Valid OrderItemCancelReqDTO reqDTO
    ) {
        return success(orderItemCancelService.cancelOrder(reqDTO));
    }
}
