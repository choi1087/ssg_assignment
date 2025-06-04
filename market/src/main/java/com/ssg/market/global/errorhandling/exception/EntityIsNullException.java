package com.ssg.market.global.errorhandling.exception;

import com.ssg.market.global.errorhandling.BusinessException;
import com.ssg.market.global.errorhandling.ErrorCode;
import lombok.Getter;

import java.util.List;

@Getter
public class EntityIsNullException extends BusinessException {
    public EntityIsNullException(
            ErrorCode errorCode,
            List<Long> itemIdList) {
//        super(errorCode, String.format(ErrorCode.ITEM_NOT_FOUND.getMessage(), itemIdList.toString()));
        super(errorCode, String.format(errorCode.getMessage(), itemIdList.toString()));
    }

    public EntityIsNullException(ErrorCode errorCode) {
        super(errorCode);
    }
}
