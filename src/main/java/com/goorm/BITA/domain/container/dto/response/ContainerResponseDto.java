package com.goorm.BITA.domain.container.dto.response;

import com.goorm.BITA.common.enums.ContainerLanguage;
import com.goorm.BITA.domain.container.domain.Container;
import com.goorm.BITA.domain.folder.dto.response.FolderResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Getter
public class ContainerResponseDto {
    private Long containerId;
    private String name;
    private String mode;
    private ContainerLanguage language;
    private List<FolderResponseDto> folders;
    private List<ContainerUserResponseDto> containerUser;

    public static ContainerResponseDto from(Container container) {
        List<FolderResponseDto> topLevelFolderDtos = container.getFolders().stream()
            .filter(folder -> folder.getParentFolder() == null)
            .map(FolderResponseDto::toDto)
            .collect(Collectors.toList());

        List<ContainerUserResponseDto> containerUserDtos = container.getContainerUsers().stream()
            .map(ContainerUserResponseDto::from)
            .collect(Collectors.toList());

        return new ContainerResponseDto(container.getId(), container.getName(), container.getMode(), container.getLanguage(), topLevelFolderDtos, containerUserDtos);
    }
}