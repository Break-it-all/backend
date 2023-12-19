package com.goorm.BITA.domain.container.domain;

import com.goorm.BITA.common.enums.UserRole;
import com.goorm.BITA.domain.user.domain.User;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class ContainerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "container_id", nullable = false)
    private Container container;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public ContainerUser(Container container, User user, UserRole role) {
        this.container = container;
        this.user = user;
        this.role = role;
    }

    public void switchRole() {
        this.role = (this.role == UserRole.navigator) ? UserRole.driver : UserRole.navigator;
    }
}
