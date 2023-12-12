package com.goorm.BITA.domain.user.controller;

import com.goorm.BITA.api.response.ApiResponseDto;
import com.goorm.BITA.domain.user.dto.request.*;
import com.goorm.BITA.domain.user.dto.response.UserSignInResponse;
import com.goorm.BITA.domain.user.dto.response.UserResponse;
import com.goorm.BITA.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    final UserService userService;

    /* 회원가입 */
    @PostMapping("/signup")
    public ApiResponseDto<UserResponse> signup(
            @RequestBody UserSignUpRequest userSignUpRequest
            ) {
        UserResponse user = userService.saveUser(userSignUpRequest);
        return ApiResponseDto.successResponse(user);
    }

    /* 로그인 */
    @PostMapping("/signin")
    public ApiResponseDto<UserSignInResponse> signin(
            @RequestBody UserSignInRequest userSignInRequest
            ) {
        UserSignInResponse user = userService.signin(userSignInRequest);
        return ApiResponseDto.successResponse(user);
    }

    /* 회원정보 수정 */
    @PatchMapping("/update")
    public ApiResponseDto<UserResponse> updateUser(
            @RequestBody UserUpdateInfoRequest userUpdateInfoRequest
            ) {
        UserResponse user = userService.updateUser(userUpdateInfoRequest);
        return ApiResponseDto.successResponse(user);
    }

    /* 비밀번호 재설정 */
    @PatchMapping("/reset-password")
    public ApiResponseDto<?> resetPassword(
            @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest
            ) {
        userService.resetPassword(userUpdatePasswordRequest);
        return ApiResponseDto.successWithoutDataResponse();
    }

    /* 회원탈퇴 */
    @DeleteMapping("/delete")
    public ApiResponseDto<?> deleteUser(
            @RequestBody UserDeleteRequest userDeleteRequest
            ) {
        userService.deleteUser(userDeleteRequest);
        return ApiResponseDto.successWithoutDataResponse();
    }
}
