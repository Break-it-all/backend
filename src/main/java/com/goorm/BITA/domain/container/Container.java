package com.goorm.BITA.domain.container;

import static javax.persistence.EnumType.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goorm.BITA.common.enums.ContainerLanguage;
import com.goorm.BITA.common.enums.ContainerMode;
import com.goorm.BITA.domain.base.BaseEntity;
import com.goorm.BITA.domain.folder.Folder;
import java.time.LocalDateTime;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLDelete(sql = "UPDATE container SET is_deleted_at = NOW() WHERE container_id = ?")
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
    private List<Folder> folders;

    private String description;
    private LocalDateTime isDeletedAt;

    private Container(String name, ContainerMode mode, ContainerLanguage language, String description, LocalDateTime now) {
        //TODO 유저 추가하기
        this.name = name;
        this.mode = mode.getMode();
        this.language = language;
        this.description = description;
        this.setCreatedBy(null);
        this.setUpdatedBy(null);
        this.setCreatedAt(now);
        this.setUpdatedAt(now);
    }

    public static Container createContainer(String name, ContainerMode mode, ContainerLanguage language, String description, LocalDateTime now) {
        return new Container(name, mode, language, description, now);
    }

    public void update(String name) {
        this.name = name;
        this.setUpdatedAt(LocalDateTime.now());
    }

    // 연관관계 편의 메서드
    public void addFolder(Folder folder) {
        this.folders.add(folder);
    }
}