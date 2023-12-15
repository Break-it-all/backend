package com.goorm.BITA.domain.folder;


import com.goorm.BITA.domain.file.File;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    // 하위 폴더를 찾는 쿼리 메서드
    @Query("select f from Folder f where f.parentFolder.id = :folderId")
    List<Folder> findSubFolders(@Param("folderId") Long folderId);

    // 폴더에 속한 파일을 찾는 쿼리 메서드
    @Query("select fi from File fi where fi.folder.id = :folderId")
    List<File> findFiles(@Param("folderId") Long folderId);

}