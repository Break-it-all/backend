package com.goorm.BITA.domain.user.dto.response;

import com.goorm.BITA.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserResponse {
    private String email;
    private String name;

    public static UserResponse toDto(User user) {
        return new UserResponse(user.getEmail(), user.getName());
    }
}
