package com.ssg.market.global.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@Getter
@Builder
public class SuccessResponse<T> {
    private boolean success;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime timeStamp;

    public static <T> SuccessResponse<T> response(boolean success, T data) {
        return SuccessResponse.<T>builder()
                .timeStamp(ZonedDateTime.now(TimeZone.getTimeZone("Asia/Seoul").toZoneId()))
                .success(success)
                .data(data)
                .build();
    }

    public static <T> ResponseEntity<SuccessResponse<T>> success() {
        return ResponseEntity.ok(response(true, null));
    }

    public static <T> ResponseEntity<SuccessResponse<T>> success(T data) {
        return ResponseEntity.ok(response(true, data));
    }
}
