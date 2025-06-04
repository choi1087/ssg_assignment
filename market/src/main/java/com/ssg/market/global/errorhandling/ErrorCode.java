package com.ssg.market.global.errorhandling;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Item error
    ITEM_ID_DUPLICATED(400, "ITEM_000", "중복된 상품 요청입니다. 아이템 ID: %s"),
    ITEM_NOT_FOUND(400, "ITEM_001", "해당 상품을 찾을 수 없습니다. 아이템 ID: %s"),
    ITEM_STOCK_NOT_ENOUGH(400, "ITEM_002", "해당 상품의 재고가 부족합니다. 아이템 ID: %s"),
    ITEM_INVALID_QUANTITY(400, "ITEM_003", "상품 주문의 수량은 0보다 커야 합니다. 아이템 ID: %s"),

    // Order error
    ORDER_NOT_FOUND(400, "ORDER_000", "해당 주문번호가 존재하지 않습니다. 주문번호: %s"),
    ORDER_NOT_ENOUGH(400, "", ""),
    ORDER_INVALID_VALUE(400, "", ""),

    // OrderItem error
    ORDER_ITEM_NOT_FOUND(400, "ORDER_ITEM_000", "해당 주문목록이 존재하지 않습니다."),
    ORDER_ITEM_ALREADY_DELETED(400, "ORDER_ITEM_001", "이미 취소된 상품입니다."),

    // INPUT_DTO_ERROR
    INPUT_DTO_VALIDATION(400, "DTO_ERROR", "유효하지 않은 입력값입니다."),

    // GLOBAL ERROR
    INVALID_PATH_VARIABLE(400, "PATH_001", "필수 경로 변수가 누락되었습니다."),
    INVALID_TYPE_VARIABLE(400, "TYPE_001", "잘못된 타입 입력입니다."),

    // INTERNAL_SERVER_ERROR
    INTERNAL_SERVER_ERROR(500, "SERVER_001", "Internal server error");

    @JsonIgnore
    private final int status;
    private final String code;
    private final String message;
}
