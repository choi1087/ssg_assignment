package com.ssg.market.domain.reset.controller;

import com.ssg.market.domain.reset.service.ResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "RESET", description = "(선택) 리셋 API")
@RequestMapping("/reset")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ResetController {

    private final ResetService resetService;

    @Operation(summary = "(선택) 기본 상품 데이터로 리셋",
            description = "주문, 주문 목록, 상품 데이터를 모두 삭제하고, 기본 상품 데이터 상태로 되돌립니다.")
    @PostMapping("")
    public ResponseEntity<Map<String, String>> resetAllData() {
        resetService.reset();
        Map<String, String> result = Map.of("message", "리셋 성공");
        return ResponseEntity.ok(result);
    }
}
