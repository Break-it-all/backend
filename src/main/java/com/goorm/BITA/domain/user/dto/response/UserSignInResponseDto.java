package com.goorm.BITA.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserSignInResponseDto {
    private String email;
    private String name;
    private String accessToken;

    public static UserSignInResponseDto toDto(UserSignInResponseInfo userSignInResponseInfo) {
        return new UserSignInResponseDto(userSignInResponseInfo.getEmail(), userSignInResponseInfo.getName(), userSignInResponseInfo.getAccessToken());
    }
}