package com.goorm.BITA.domain.file;

import com.goorm.BITA.api.response.ApiResponseDto;
import com.goorm.BITA.domain.file.dto.FileCreateRequestDto;
import com.goorm.BITA.domain.file.dto.FileUpdateRequestDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // 파일 생성
    @PostMapping("/folder/{folderId}/file")
    public ApiResponseDto<?> createFile(
        @ModelAttribute FileCreateRequestDto requestDto,
        @PathVariable Long folderId
    ) throws IOException {
        File file = fileService.createFile(folderId, requestDto, requestDto.getFile());
        return ApiResponseDto.successWithoutDataResponse();
    }

    // 파일 저장 s3에 올리기
    @PutMapping("/file/{fileId}")
    public ApiResponseDto<?> updateFile(
        @PathVariable Long fileId,
        @RequestBody FileUpdateRequestDto requestDto
    ) {
        fileService.updateFile(fileId, requestDto);
        return ApiResponseDto.successWithoutDataResponse();
    }

    // 파일 수정 이름

    // 파일 삭제
}