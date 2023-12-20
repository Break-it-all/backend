package com.goorm.BITA.domain.file.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class CompileResponse {
    private String output;
    private String error;
}
