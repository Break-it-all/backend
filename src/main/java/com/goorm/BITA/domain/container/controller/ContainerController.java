package com.goorm.BITA.domain.container.controller;

import com.goorm.BITA.api.response.ApiResponseDto;
import com.goorm.BITA.domain.container.dto.request.ContainerCreateRequestDto;
import com.goorm.BITA.domain.container.dto.request.ContainerEmailRequestDto;
import com.goorm.BITA.domain.container.dto.request.ContainerUpdateRequestDto;
import com.goorm.BITA.domain.container.dto.response.ContainerListResponseDto;
import com.goorm.BITA.domain.container.dto.response.ContainerResponseDto;
import com.goorm.BITA.domain.container.info.CreateContainerInfo;
import com.goorm.BITA.domain.container.info.FindContainerInfo;
import com.goorm.BITA.domain.container.service.ContainerService;
import com.goorm.BITA.domain.user.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/container")
public class ContainerController {
    private final ContainerService containerService;

    // create
    @PostMapping
    public ApiResponseDto<ContainerResponseDto> createdContainer(
        @RequestBody ContainerCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CreateContainerInfo createContainerInfo = requestDto.toCreateContainerInfo(userDetails.getUserId());
        return ApiResponseDto.successResponse(containerService.createContainer(createContainerInfo));
    }

    // 단일 조회
    @GetMapping("/{containerId}")
    public ApiResponseDto<ContainerResponseDto> getContainer(
        @PathVariable Long containerId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        FindContainerInfo findContainerInfo = new FindContainerInfo(containerId, userDetails.getUserId());
        return ApiResponseDto.successResponse(containerService.findContainer(findContainerInfo));
    }

    // 내가 만든 컨테이너 조회
    @GetMapping("/my")
    public ApiResponseDto<List<ContainerListResponseDto>> getMyContainers(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ApiResponseDto.successResponse(containerService.getMyContainers(userDetails.getUserId()));
    }

    // 공유된 컨테이너 조회
    @GetMapping("/shared")
    public ApiResponseDto<List<ContainerListResponseDto>> getSharedContainers(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ApiResponseDto.successResponse(containerService.getSharedContainers(userDetails.getUserId()));
    }

    // 페어 프로그래밍 역할 바꾸기
    @PostMapping("/switch-role/{containerId}")
    public ApiResponseDto<?> switchUserRole(@PathVariable Long containerId) {
        containerService.switchUserRole(containerId);
        return ApiResponseDto.successWithoutDataResponse();
    }

    // 유저 초대
    @PostMapping("/{containerId}/add-user")
    public ApiResponseDto<?> addUserToContainer(
        @PathVariable Long containerId,
        @RequestBody ContainerEmailRequestDto userEmailDto
    ) {
        containerService.addUserToContainer(containerId, userEmailDto.getEmail());
        return ApiResponseDto.successWithoutDataResponse();
    }

    // update
    @PutMapping("/{containerId}")
    public ApiResponseDto<?> updateContainer(
        @PathVariable Long containerId,
        @RequestBody ContainerUpdateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        containerService.updateController(requestDto, containerId, userDetails.getUserId());
        return ApiResponseDto.successWithoutDataResponse();
    }

    // delete
    @DeleteMapping("/{containerId}")
    public ApiResponseDto<?> deleteContainer(
        @PathVariable Long containerId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        containerService.deleteContainer(containerId, userDetails.getUserId());
        return ApiResponseDto.successWithoutDataResponse();
    }
}