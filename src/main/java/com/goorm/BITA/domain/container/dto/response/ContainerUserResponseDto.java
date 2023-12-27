package com.goorm.BITA.domain.container.dto.response;

import com.goorm.BITA.common.enums.UserRole;
import com.goorm.BITA.domain.container.domain.ContainerUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContainerUserResponseDto {
    private Long userId;
    private UserRole role;
    private String name;

    public static ContainerUserResponseDto from(ContainerUser containerUser) {
        return new ContainerUserResponseDto(containerUser.getUser().getId(), containerUser.getRole(), containerUser.getUser().getName());
    }
}

