package com.goorm.BITA.domain.file;

import com.goorm.BITA.domain.file.info.CreateRequestFileInfo;
import com.goorm.BITA.domain.file.info.CreateResponseFileInfo;
import com.goorm.BITA.domain.file.info.SaveRequestFileInfo;
import com.goorm.BITA.domain.folder.Folder;
import com.goorm.BITA.domain.folder.FolderRepository;
import com.goorm.BITA.domain.s3.S3UploadService;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

        private final FileRepository fileRepository;
        private final FolderRepository folderRepository;
        private final S3UploadService s3UploadService;

    @Transactional
    public CreateResponseFileInfo createFile(CreateRequestFileInfo fileInfo) {
        Folder folder = folderRepository.findById(fileInfo.getFolderId())
            .orElseThrow(() -> new RuntimeException("폴더를 찾을 수 없습니다."));
        // S3에 파일 업로드
        String fileUrl = s3UploadService.uploadFile(folder.getId());
        File createFile = File.createFile(fileInfo.getName(), fileUrl, folder);
        folder.addFile(createFile);
        File saveFile = fileRepository.save(createFile);
        return CreateResponseFileInfo.toDto(saveFile);
    }

    @Transactional
    public void updateFile(SaveRequestFileInfo fileInfo) {
        File file = fileRepository.findById(fileInfo.getFileId())
            .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다."));

        // 파일의 내용을 S3에 업데이트
        s3UploadService.updateFileContentInS3(file.getUrl(), fileInfo.getContent());
        file.updateUpdatedAt(ZonedDateTime.now());
        fileRepository.save(file);
    }

    // 파일 조회

    // 파일 수정

    // 파일 삭제
}
