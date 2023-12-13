package com.goorm.BITA.domain.user.controller;

import com.goorm.BITA.api.response.ApiResponseDto;
import com.goorm.BITA.domain.user.dto.request.EmailAuthCheckRequest;
import com.goorm.BITA.domain.user.dto.request.EmailAuthCreateRequest;
import com.goorm.BITA.domain.user.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EmailAuthController {
    final EmailAuthService emailAuthService;

    /* 인증을 위한 이메일 발송 */
    @PostMapping("/api/email-auth")
    public ApiResponseDto<?> sendEmail(
            @RequestBody EmailAuthCreateRequest emailAuthCreateRequest
    ) {
        long id = emailAuthService.saveEmailAuth(emailAuthCreateRequest.getEmail());
        return ApiResponseDto.successResponse(id);
    }

    /* 이메일 인증 확인 */
    @GetMapping("/api/email-auth")
    public ApiResponseDto checkEmailAuth(
            @RequestBody EmailAuthCheckRequest emailAuthCheckRequest
            ) {
        boolean isEmailAuth = emailAuthService.isEmailVerified(emailAuthCheckRequest);
        return ApiResponseDto.successResponse(isEmailAuth);
    }

    /* 이메일 인증 완료
    * 이메일에 전송된 URL 클릭 시
    * 해당 요청을 여기서 처리
    * */
    @GetMapping("/email-auth")
    public ApiResponseDto<?> emailAuth(
            @RequestParam String emailToken
    ) {
        emailAuthService.updateEmailAuth(emailToken);
        return ApiResponseDto.successWithoutDataResponse();
    }
}
