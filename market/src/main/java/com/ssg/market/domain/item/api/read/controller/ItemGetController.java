package com.ssg.market.domain.item.api.read.controller;

import com.ssg.market.domain.item.api.read.dto.ItemGetAllResDTO;
import com.ssg.market.domain.item.api.read.service.ItemGetAllService;
import com.ssg.market.global.response.ErrorResponse;
import com.ssg.market.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ssg.market.global.response.SuccessResponse.*;

@RestController
@Tag(name = "ITEM_READ", description = "(선택) 상품 조회 API")
@RequestMapping("/item")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Invalid Input Error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
})
public class ItemGetController {

    private final ItemGetAllService itemGetAllService;

    @Operation(summary = "(선택) 전체 상품 조회",
            description = "전체 상품을 조회한다.")
    @GetMapping(value = "", produces = "application/json; charset=utf-8")
    public ResponseEntity<SuccessResponse<List<ItemGetAllResDTO>>> getAllItem() {
        return success(itemGetAllService.getItemlist());
    }
}
