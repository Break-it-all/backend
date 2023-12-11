package com.goorm.BITA;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name = "테스트 응답 dto")
@Data
@AllArgsConstructor
public class TestResponseDto {
    @Schema(description = "테스트 응답 문자열입니다.", example = "test message example", required = true)
    private String message2;
    @Schema(description = "테스트 응답 숫자입니다.", example = "1", maximum = "100", minimum = "1", required = true)
    private Integer code2;
}
