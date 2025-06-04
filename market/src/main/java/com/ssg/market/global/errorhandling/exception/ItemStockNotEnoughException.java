package com.ssg.market.global.errorhandling.exception;

import com.ssg.market.global.errorhandling.BusinessException;
import com.ssg.market.global.errorhandling.ErrorCode;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemStockNotEnoughException extends BusinessException {
    public ItemStockNotEnoughException(List<Long> itemIdList) {
        super(ErrorCode.ITEM_STOCK_NOT_ENOUGH,
                String.format(ErrorCode.ITEM_STOCK_NOT_ENOUGH.getMessage(), itemIdList.toString()));
    }

    public ItemStockNotEnoughException() {
        super(ErrorCode.ITEM_STOCK_NOT_ENOUGH);
    }
}
