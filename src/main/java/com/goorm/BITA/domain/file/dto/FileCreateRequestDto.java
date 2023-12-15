package com.goorm.BITA.domain.file.dto;

import com.goorm.BITA.common.enums.ContainerLanguage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileCreateRequestDto {
    private MultipartFile file;
    private String name;
    private ContainerLanguage language;
}