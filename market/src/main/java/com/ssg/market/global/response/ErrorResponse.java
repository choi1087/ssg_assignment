package com.ssg.market.global.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssg.market.global.errorhandling.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    @Schema(defaultValue = "false", example = "false", description = "요청 성공 여부")
    private boolean success = false;
    @Schema(description = "실패 코드")
    private String code;
    @Schema(description = "실패 메시지")
    private String message;
    @Schema(description = "실패한 API 요청 URI")
    private String path;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime timeStamp;

    private ErrorResponse(String code, String message, String path) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.path = path;
        this.timeStamp = ZonedDateTime.now(TimeZone.getTimeZone("Asia/Seoul").toZoneId());
    }

    public static ErrorResponse of(ErrorCode errorCode, String path) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), path);
    }

    public static ErrorResponse of(ErrorCode errorCode, String message, String path) {
        return new ErrorResponse(errorCode.getCode(), message, path);
    }
}
