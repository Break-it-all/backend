package com.goorm.BITA.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserSignInResponse {
    // TODO: Apply JWT
    private String email;
    private String name;

    public static UserSignInResponse toDto(String email, String name) {
        return new UserSignInResponse(email, name);
    }
}