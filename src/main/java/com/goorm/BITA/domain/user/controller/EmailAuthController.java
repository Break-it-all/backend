package com.goorm.BITA.domain.user.controller;

import com.goorm.BITA.api.response.ApiResponseDto;
import com.goorm.BITA.domain.user.dto.request.EmailAuthCheckRequest;
import com.goorm.BITA.domain.user.dto.request.EmailAuthCreateRequest;
import com.goorm.BITA.domain.user.dto.response.EmailAuthCheckResponse;
import com.goorm.BITA.domain.user.dto.response.EmailAuthCreateResponse;
import com.goorm.BITA.domain.user.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email-auth")
public class EmailAuthController {
    final EmailAuthService emailAuthService;

    /* 인증을 위한 이메일 발송 */
    @PostMapping
    public ApiResponseDto<EmailAuthCreateResponse> sendEmail(
            @RequestBody EmailAuthCreateRequest emailAuthCreateRequest
    ) {
        EmailAuthCreateResponse response = emailAuthService.saveEmailAuth(emailAuthCreateRequest.getEmail());
        return ApiResponseDto.successResponse(response);
    }

    /* 이메일 인증 확인 */
    @GetMapping
    public ApiResponseDto<EmailAuthCheckResponse> checkEmailAuth(
            @RequestBody EmailAuthCheckRequest emailAuthCheckRequest
            ) {
        EmailAuthCheckResponse response = emailAuthService.isEmailVerified(emailAuthCheckRequest);
        return ApiResponseDto.successResponse(response);
    }

    /* 이메일 인증 완료
    * 이메일에 전송된 URL 클릭 시
    * 해당 요청을 여기서 처리
    * */
    @GetMapping("/verify")
    public ApiResponseDto<?> emailAuth(
            @RequestParam String emailToken
    ) {
        emailAuthService.updateEmailAuth(emailToken);
        return ApiResponseDto.successWithoutDataResponse();
    }
}
