package com.goorm.BITA;

import com.goorm.BITA.api.ApiResponseDto;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Test", description = "tag Test API Controller")
@RestController
@RequestMapping("/api")
public class TestController {

    @Operation(summary = "테스트1번", description = "문자열을 반환한다.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "성공", response = String.class),
        @ApiResponse(code = 404, message = "페이지를 찾을 수 없음")
    })
    @GetMapping("/test")
    public String test() {
        return "docker compose test BITA";
    }

    @Operation(summary = "테스트2번", description = "dto를 반환한다.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "성공", response = TestResponseDto.class),
        @ApiResponse(code = 401, message = "요청 값이 다름")
    })
    @PostMapping("/test2/{testId}")
    public TestResponseDto test2(
        @Parameter(name = "테스트 ID", description = "테스트 ID입니다.", example = "1")
        @PathVariable Integer testId,

        @Parameter(name = "테스트 요청 dto", description = "테스트 요청 dto입니다.")
        @RequestBody TestRequestDto testRequestDto
    ) {
        return new TestResponseDto(testRequestDto.getMessage(), testId);
    }

    @GetMapping("/test3")
    public ApiResponseDto<TestResponseDto> test3 () {
        return ApiResponseDto.successResponse(new TestResponseDto("test3", 3));
    }
}