package com.goorm.BITA.domain.folder;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goorm.BITA.domain.base.BaseEntity;
import com.goorm.BITA.domain.container.domain.Container;
import com.goorm.BITA.domain.file.File;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@OnDelete(action = CASCADE)
@SQLDelete(sql = "UPDATE folder SET deleted_at = NOW() WHERE folder_id = ?")
@Where(clause = "deleted_at is null")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Folder extends BaseEntity {

    @Id
    @Column(name = "FOLDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTAINER_ID")
    @JsonBackReference
    private Container container;
    // 자기참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_FOLDER_ID")
    @JsonBackReference
    private Folder parentFolder;
    @OneToMany(mappedBy = "parentFolder")
    @JsonManagedReference
    private List<Folder> subFolders;
    // 양방향
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<File> files;

    private ZonedDateTime deletedAt;

    public Folder(String name, Container container, Folder parentFolder, ZonedDateTime now) {
        this.name = name;
        this.container = container;
        this.parentFolder = parentFolder;
        this.setCreatedBy(null);
        this.setUpdatedBy(null);
        this.setCreatedAt(now);
        this.setUpdatedAt(now);
    }

    public static Folder createRootFolder(String name, Container container) {
        return new Folder(name, container, null, ZonedDateTime.now());
    }

    public static Folder createSubFolder(String name, Container container, Folder parentFolder) {
        return new Folder(name, container, parentFolder, ZonedDateTime.now());
    }

    // 파일 연관관계 편의 메서드
    public void addFile(File file) {
        this.files.add(file);
    }

    public void updateName(String name) {
        this.name = name;
        this.setUpdatedAt(ZonedDateTime.now());
    }
}