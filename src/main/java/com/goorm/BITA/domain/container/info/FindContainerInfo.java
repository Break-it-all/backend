package com.goorm.BITA.domain.container.info;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
public class FindContainerInfo {
    private Long containerId;
    private Long userId;
}
