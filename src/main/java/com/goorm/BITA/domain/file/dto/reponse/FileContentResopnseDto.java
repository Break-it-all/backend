package com.goorm.BITA.domain.file.dto.reponse;

import com.goorm.BITA.domain.file.info.ReadResponseFileInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class FileContentResopnseDto {
    private String content;

    public static FileContentResopnseDto toDto(ReadResponseFileInfo info) {
        return new FileContentResopnseDto(info.getContent());
    }
}
