package com.goorm.BITA.domain.file;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.goorm.BITA.domain.base.BaseEntity;
import com.goorm.BITA.domain.folder.Folder;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@OnDelete(action = CASCADE)
@SQLDelete(sql = "UPDATE file SET is_deleted_at = NOW() WHERE file_id = ?")
@Where(clause = "is_deleted_at is null")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class File extends BaseEntity {
    @Id
    @Column(name = "FILE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private LocalDateTime isDeletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "FOLDER_ID")
    private Folder folder;

    public File(String name, String url, Folder folder) {
        LocalDateTime now = LocalDateTime.now();
        this.name = name;
        this.url = url;
        this.folder = folder;
        this.setCreatedAt(now);
        this.setUpdatedAt(now);
        this.setCreatedBy(null);
        this.setUpdatedBy(null);
    }

    public static File createFile(String name, String url, Folder folder) {
        return new File(name, url, folder);
    }

    public void updateUpdatedAt(LocalDateTime now) {
        this.setUpdatedAt(now);
    }
}