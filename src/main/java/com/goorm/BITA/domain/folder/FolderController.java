package com.goorm.BITA.domain.folder;

import com.goorm.BITA.api.response.ApiResponseDto;
import com.goorm.BITA.domain.folder.dto.request.FolderCreateRequestDto;
import com.goorm.BITA.domain.folder.dto.request.FolderUpdateRequestDto;
import com.goorm.BITA.domain.folder.dto.response.FolderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/container/{containerId}/folder")
    public ApiResponseDto<?> createFolder(
        @PathVariable Long containerId,
        @RequestBody FolderCreateRequestDto requestDto
    ) {
        folderService.createFolder(containerId, requestDto);
        return ApiResponseDto.successWithoutDataResponse();
    }

    // 수정
    @PutMapping("/container/{containerId}/folder/{folderId}")
    public ApiResponseDto<FolderResponseDto> updateFolder(
        @PathVariable Long folderId,
        @RequestBody FolderUpdateRequestDto requestDto
    ) {
        FolderResponseDto folderResponseDto = folderService.updateFolder(folderId, requestDto);
        return ApiResponseDto.successResponse(folderResponseDto);
    }

    // 삭제
    @DeleteMapping("/container/{containerId}/folder/{folderId}")
    public ApiResponseDto<?> deleteFolder(
        @PathVariable Long folderId
    ) {
        folderService.deleteFolder(folderId);
        return ApiResponseDto.successWithoutDataResponse();
    }
}