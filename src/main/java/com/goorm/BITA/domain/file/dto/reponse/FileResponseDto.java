package com.goorm.BITA.domain.file.dto.reponse;

import com.goorm.BITA.domain.file.File;
import com.goorm.BITA.domain.file.info.CreateResponseFileInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FileResponseDto {
    private Long fileId;
    private String name;
    private String url;

    public static FileResponseDto toDto(File file) {
        return new FileResponseDto(file.getId(), file.getName(), file.getUrl());
    }

    public static FileResponseDto toDto(CreateResponseFileInfo file) {
        return new FileResponseDto(file.getFileId(), file.getName(), file.getUrl());
    }
}