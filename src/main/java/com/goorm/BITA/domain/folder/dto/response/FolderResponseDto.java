package com.goorm.BITA.domain.folder.dto.response;

import com.goorm.BITA.domain.file.dto.reponse.FileResponseDto;
import com.goorm.BITA.domain.folder.Folder;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FolderResponseDto {
    private Long folderId;
    private String name;
    // null
    private List<FolderResponseDto> subFolders;
    private List<FileResponseDto> files;

    public static FolderResponseDto toDto(Folder folder) {
        List<FolderResponseDto> subFolderDtos = folder.getSubFolders().stream()
            .map(FolderResponseDto::toDto)
            .collect(Collectors.toList());
        List<FileResponseDto> fileDtos = folder.getFiles().stream()
            .map(FileResponseDto::toDto)
            .collect(Collectors.toList());

        return new FolderResponseDto(folder.getId(), folder.getName(), subFolderDtos, fileDtos);
    }
}