package com.goorm.BITA.domain.container.domain;

import static javax.persistence.EnumType.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goorm.BITA.common.enums.ContainerLanguage;
import com.goorm.BITA.common.enums.ContainerMode;
import com.goorm.BITA.domain.base.BaseEntity;
import com.goorm.BITA.domain.folder.Folder;
import com.goorm.BITA.domain.user.domain.User;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLDelete(sql = "UPDATE container SET deleted_at = NOW() WHERE container_id = ?")
@Where(clause = "is_deleted_at is null")
public class Container extends BaseEntity {
    @Id
    @Column(name = "CONTAINER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String mode;
    @Column(nullable = false)
    @Enumerated(STRING)
    private ContainerLanguage language;
    // 양방향
    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Folder> folders = new ArrayList<>();

    private String description;
    private ZonedDateTime deletedAt;

    @OneToMany(mappedBy = "container")
    private List<ContainerUser> containerUsers = new ArrayList<>();

    private Container(String name, ContainerMode mode, ContainerLanguage language, String description, ZonedDateTime now, User user) {
        this.name = name;
        this.mode = mode.getMode();
        this.language = language;
        this.description = description;
        this.setCreatedBy(user);
        this.setUpdatedBy(user);
        this.setCreatedAt(now);
        this.setUpdatedAt(now);
    }

    public static Container createContainer(String name, ContainerMode mode, ContainerLanguage language, String description, ZonedDateTime now, User user) {
        return new Container(name, mode, language, description, now, user);
    }

    public void update(String name, User user) {
        this.name = name;
        this.setUpdatedAt(ZonedDateTime.now());
        this.setUpdatedBy(user);
    }

    // 연관관계 편의 메서드
    public void addFolder(Folder folder) {
        this.folders.add(folder);
    }

    public boolean isUser(User user) {
        return containerUsers.stream().anyMatch(containerUser -> containerUser.getUser().equals(user));
    }

    public void addContainerUser(ContainerUser containerUser) {
        this.containerUsers.add(containerUser);
    }
}