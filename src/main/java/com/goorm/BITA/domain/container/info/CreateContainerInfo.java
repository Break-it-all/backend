package com.goorm.BITA.domain.container.info;

import com.goorm.BITA.common.enums.ContainerLanguage;
import com.goorm.BITA.common.enums.ContainerMode;
import com.goorm.BITA.domain.container.domain.Container;
import com.goorm.BITA.domain.user.domain.User;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateContainerInfo {
    private String name;
    private ContainerMode mode;
    private ContainerLanguage language;
    private String description;
    private Long userId;

    public Container from(User user) {
        return Container.createContainer(name, mode, language, description, ZonedDateTime.now() ,user);
    }
}
