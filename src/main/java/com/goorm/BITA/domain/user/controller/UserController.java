package com.goorm.BITA.domain.user.controller;

import com.goorm.BITA.api.response.ApiResponseDto;
import com.goorm.BITA.common.enums.ResponseCode;
import com.goorm.BITA.domain.user.dto.request.*;
import com.goorm.BITA.domain.user.dto.response.UserSignInResponse;
import com.goorm.BITA.domain.user.dto.response.UserResponse;
import com.goorm.BITA.domain.user.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 관련 API Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    final UserService userService;

    /* 회원가입 */
    @Tag(name = "User", description = "사용자 관련 API Controller")
    @Operation(summary = "회원가입", description = "회원가입을 수행한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "성공", response = UserResponse.class),
            @ApiResponse(code = 4001, message = "요청 값이 다름")
    })
    @PostMapping("/signup")
    public ApiResponseDto<UserResponse> signup(
            @Parameter(name = "회원가입 요청 dto", description = "회원가입 요청 dto입니다.")
            @RequestBody UserSignUpRequest userSignUpRequest
            ) {
        UserResponse user = userService.saveUser(userSignUpRequest);
        return ApiResponseDto.successResponse(user);
    }

    /* 로그인 */
    @Tag(name = "User", description = "사용자 관련 API Controller")
    @Operation(summary = "로그인", description = "로그인을 수행한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "성공", response = UserSignInResponse.class),
            @ApiResponse(code = 4001, message = "요청 값이 다름")
    })
    @PostMapping("/signin")
    public ApiResponseDto<UserSignInResponse> signin(
            @Parameter(name = "로그인 요청 dto", description = "로그인 요청 dto입니다.")
            @RequestBody UserSignInRequest userSignInRequest
            ) {
        UserSignInResponse user = userService.signin(userSignInRequest);
        return ApiResponseDto.successResponse(user);
    }

    /* 회원정보 수정 */
    @Tag(name = "User", description = "사용자 관련 API Controller")
    @Operation(summary = "회원정보 수정", description = "회원정보(name)을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "성공", response = UserResponse.class),
            @ApiResponse(code = 4001, message = "요청 값이 다름")
    })
    @PatchMapping("/update")
    public ApiResponseDto<UserResponse> updateUser(
            @Parameter(name = "회원정보 수정 요청 dto", description = "회원정보 수정 요청 dto입니다.")
            @RequestBody UserUpdateInfoRequest userUpdateInfoRequest
            ) {
        UserResponse user = userService.updateUser(userUpdateInfoRequest);
        return ApiResponseDto.successResponse(user);
    }

    /* 비밀번호 재설정 */
    @Tag(name = "User", description = "사용자 관련 API Controller")
    @Operation(summary = "비밀번호 재설정", description = "비밀번호를 재설정한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "성공"),
            @ApiResponse(code = 4001, message = "요청 값이 다름")
    })
    @PatchMapping("/reset-password")
    public ApiResponseDto<?> resetPassword(
            @Parameter(name = "비밀번호 재설정 요청 dto", description = "비밀번호 재설정 요청 dto입니다.")
            @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest
            ) {
        userService.resetPassword(userUpdatePasswordRequest);
        return ApiResponseDto.successWithoutDataResponse();
    }

    /* 회원탈퇴 */
    @Tag(name = "User", description = "사용자 관련 API Controller")
    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 수행한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "성공"),
            @ApiResponse(code = 4001, message = "요청 값이 다름")
    })
    @DeleteMapping("/delete")
    public ApiResponseDto<?> deleteUser(
            @Parameter(name = "회원탈퇴 요청 dto", description = "회원탈퇴 요청 dto입니다.")
            @RequestBody UserDeleteRequest userDeleteRequest
            ) {
        userService.deleteUser(userDeleteRequest);
        return ApiResponseDto.successWithoutDataResponse();
    }
}
