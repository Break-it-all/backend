package com.goorm.BITA.domain.container.dto.response;

import com.goorm.BITA.common.enums.ContainerLanguage;
import com.goorm.BITA.domain.container.domain.Container;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Getter
public class ContainerListResponseDto {
    private Long containerId;
    private String name;
    private String mode;
    private ContainerLanguage language;
    private String description;
    private ZonedDateTime createdAt;

    public static ContainerListResponseDto from(Container container) {
        return new ContainerListResponseDto(container.getId(), container.getName(), container.getMode(), container.getLanguage(), container.getDescription(), container.getCreatedAt());
    }
}