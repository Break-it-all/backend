package com.goorm.BITA;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "테스트 요청 dto")
@Data
public class TestRequestDto {
    @Schema(description = "테스트 입력 데이터입니다.", example = "test message example", maximum = "100", minimum = "1", required = true)
    private String message;
}
