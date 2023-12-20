package com.goorm.BITA.domain.file;

import com.goorm.BITA.api.response.ApiResponseDto;
import com.goorm.BITA.domain.file.dto.FileCreateRequestDto;
import com.goorm.BITA.domain.file.dto.FileUpdateRequestDto;
import com.goorm.BITA.domain.file.dto.reponse.FileResponseDto;
import com.goorm.BITA.domain.file.info.CreateRequestFileInfo;
import com.goorm.BITA.domain.file.info.CreateResponseFileInfo;
import com.goorm.BITA.domain.file.info.SaveRequestFileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // 파일 생성
    @PostMapping("/folder/{folderId}/file")
    public ApiResponseDto<FileResponseDto> createFile(
        @ModelAttribute FileCreateRequestDto requestDto,
        @PathVariable Long folderId
    ) {
        CreateRequestFileInfo fileRequestInfo = new CreateRequestFileInfo(requestDto.getName(), requestDto.getLanguage(), folderId);
        CreateResponseFileInfo fileResponseInfo = fileService.createFile(fileRequestInfo);
        FileResponseDto fileResponseDto = FileResponseDto.toDto(fileResponseInfo);
        return ApiResponseDto.successResponse(fileResponseDto);
    }

    // 파일 저장 s3에 올리기
    @PutMapping("/file/{fileId}")
    public ApiResponseDto<?> updateFile(
        @PathVariable Long fileId,
        @RequestBody FileUpdateRequestDto requestDto
    ) {
        SaveRequestFileInfo fileRequestInfo = new SaveRequestFileInfo(fileId, requestDto.getContent());
        fileService.updateFile(fileRequestInfo);
        return ApiResponseDto.successWithoutDataResponse();
    }
}