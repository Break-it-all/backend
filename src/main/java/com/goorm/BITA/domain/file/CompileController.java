package com.goorm.BITA.domain.file;

import com.goorm.BITA.api.response.ApiResponseDto;
import com.goorm.BITA.domain.file.dto.CompileRequest;
import com.goorm.BITA.domain.file.dto.CompileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file/compile")
@RequiredArgsConstructor
public class CompileController {
    private final CompileService compileService;

    @GetMapping
    public ApiResponseDto<CompileResponse> compileFile(
            @RequestBody CompileRequest request
    ) {
        CompileResponse response = compileService.compileFile(request);
        return ApiResponseDto.successResponse(response);
    }
}
