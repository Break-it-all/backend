package com.goorm.BITA.domain.file.info;

import com.goorm.BITA.domain.file.File;
import com.goorm.BITA.domain.folder.Folder;
import com.goorm.BITA.domain.user.domain.User;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateResponseFileInfo {
    private Long fileId;
    private String name;
    private String url;
    private ZonedDateTime deletedAt;
    private Folder folder;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private User createdBy;
    private User updatedBy;


    public static CreateResponseFileInfo toDto(File saveFile) {
        return new CreateResponseFileInfo(
            saveFile.getId(),
            saveFile.getName(),
            saveFile.getUrl(),
            saveFile.getDeletedAt(),
            saveFile.getFolder(),
            saveFile.getCreatedAt(),
            saveFile.getUpdatedAt(),
            saveFile.getCreatedBy(),
            saveFile.getUpdatedBy());
    }
}
