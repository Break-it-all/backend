package com.goorm.BITA.domain.container.dto.request;

import com.goorm.BITA.common.enums.ContainerLanguage;
import com.goorm.BITA.common.enums.ContainerMode;
import com.goorm.BITA.domain.container.info.CreateContainerInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContainerCreateRequestDto {
    private String name;
    private ContainerMode mode;
    private ContainerLanguage language;
    private String description;
    private Long userId;

    public CreateContainerInfo toCreateContainerInfo(Long userId) {
        return new CreateContainerInfo(name, mode, language, description, userId);
    }
}