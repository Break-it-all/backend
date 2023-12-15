package com.goorm.BITA.domain.container.dto;

import com.goorm.BITA.common.enums.ContainerLanguage;
import com.goorm.BITA.common.enums.ContainerMode;
import com.goorm.BITA.domain.container.Container;
import java.time.LocalDateTime;
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

    public Container from() {
        LocalDateTime now = LocalDateTime.now();
        return Container.createContainer(name, mode, language, description, now);
    }
}


