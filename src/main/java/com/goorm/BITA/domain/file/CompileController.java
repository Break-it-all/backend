package com.goorm.BITA.domain.file;

import com.goorm.BITA.api.response.ApiResponseDto;
import com.goorm.BITA.domain.file.dto.CompileRequest;
import com.goorm.BITA.domain.file.dto.CompileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/file/compile")
@RequiredArgsConstructor
public class CompileController {
    private final CompileService compileService;

    @PostMapping
    public ApiResponseDto<CompileResponse> compileFile(
            @RequestBody CompileRequest request
    ) {
        CompileResponse response = compileService.compileFile(request);
        return ApiResponseDto.successResponse(response);
    }
}
