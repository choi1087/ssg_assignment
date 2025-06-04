package com.ssg.market.global.errorhandling.exception;

import com.ssg.market.global.errorhandling.BusinessException;
import com.ssg.market.global.errorhandling.ErrorCode;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemDuplicatedException extends BusinessException {
    public ItemDuplicatedException(List<Long> itemIdList) {
        super(ErrorCode.ITEM_ID_DUPLICATED,
                String.format(ErrorCode.ITEM_ID_DUPLICATED.getMessage(), itemIdList.toString()));
    }
}
