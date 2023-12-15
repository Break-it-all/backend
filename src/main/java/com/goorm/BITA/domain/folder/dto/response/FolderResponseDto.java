package com.goorm.BITA.domain.folder.dto.response;

import com.goorm.BITA.domain.folder.Folder;
import com.goorm.BITA.domain.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FolderResponseDto {
    private String name;
    private LocalDateTime updatedAt;
    private User createdBy;
    private User updatedBy;
    private Folder parentFolder;
    private List<Folder> subFolders;


    public static FolderResponseDto toDto(Folder folder) {
        return new FolderResponseDto(folder.getName(), folder.getUpdatedAt(), folder.getCreatedBy(), folder.getUpdatedBy(), folder.getParentFolder(), folder.getSubFolders());
    }
}
