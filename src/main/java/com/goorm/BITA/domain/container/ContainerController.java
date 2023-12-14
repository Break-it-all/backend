package com.goorm.BITA.domain.container;

import com.goorm.BITA.api.response.ApiResponseDto;
import com.goorm.BITA.domain.container.dto.ContainerCreateRequestDto;
import com.goorm.BITA.domain.container.dto.ContainerUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/container")
public class ContainerController {
    private final ContainerService containerService;

    // create
    @PostMapping
    public ApiResponseDto createdContainer(
        @RequestBody ContainerCreateRequestDto requestDto
    ) {
        return ApiResponseDto.successResponse(containerService.createContainer(requestDto));
    }

    // 단일 조회
    @GetMapping("/{containerId}")
    public ApiResponseDto<Container> getContainer(
        @PathVariable Long containerId
    ) {
        // TODO user 받기
        return ApiResponseDto.successResponse(containerService.getContainer(containerId));
    }

    // update
    @PutMapping("/{containerId}")
    public ApiResponseDto<?> updateContainer(
        @PathVariable Long containerId,
        @RequestBody ContainerUpdateRequestDto requestDto
    ) {
        containerService.updateController(requestDto, containerId);
        return ApiResponseDto.successWithoutDataResponse();
    }

    // delete
    @DeleteMapping("/{containerId}")
    public ApiResponseDto<?> deleteContainer(
        @PathVariable Long containerId
    ) {
        containerService.deleteContainer(containerId);
        return ApiResponseDto.successWithoutDataResponse();
    }
}