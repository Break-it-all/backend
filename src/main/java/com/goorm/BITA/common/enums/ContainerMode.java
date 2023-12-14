package com.goorm.BITA.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ContainerMode {
    multi("MULTI_EDIT"),
    pair("PAIR_PROGRAMMING");
    private String mode;
}