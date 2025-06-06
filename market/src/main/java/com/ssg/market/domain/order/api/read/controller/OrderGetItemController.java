package com.ssg.market.domain.order.api.read.controller;

import com.ssg.market.domain.order.api.read.dto.res.OrderGetItemListResDTO;
import com.ssg.market.domain.order.api.read.service.OrderGetItemListService;
import com.ssg.market.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssg.market.global.response.SuccessResponse.success;

@RestController
@Tag(name = "ORDER_READ", description = "주문 조회 API")
@RequestMapping("/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Invalid Input Error"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
})
public class OrderGetItemController {
    private final OrderGetItemListService orderGetItemListService;

    @Operation(summary = "(필수) 주문 상품 조회",
            description = "주문번호에 해당하는 상품 목록을 조회한다.")
    @GetMapping(value = "/{orderId}")
    public ResponseEntity<SuccessResponse<OrderGetItemListResDTO>> getOrderItemList(
            @PathVariable Long orderId
    ) {
        return success(orderGetItemListService.getOrderItemList(orderId));
    }
}
