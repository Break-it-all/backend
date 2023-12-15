package com.goorm.BITA.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserSignInResponse {
    private String email;
    private String name;
    private String accessToken;
    private String refreshToken;

    public static UserSignInResponse toDto(String email, String name, String accessToken, String refreshToken) {
        return new UserSignInResponse(email, name, accessToken, refreshToken);
    }
}
