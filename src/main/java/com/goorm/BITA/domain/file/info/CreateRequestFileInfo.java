package com.goorm.BITA.domain.file.info;

import com.goorm.BITA.common.enums.ContainerLanguage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateRequestFileInfo {
    private String name;
    private ContainerLanguage language;
    private Long folderId;
}
