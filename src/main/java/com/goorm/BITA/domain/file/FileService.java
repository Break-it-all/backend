package com.goorm.BITA.domain.file;

import com.goorm.BITA.domain.file.dto.FileCreateRequestDto;
import com.goorm.BITA.domain.file.dto.FileUpdateRequestDto;
import com.goorm.BITA.domain.folder.Folder;
import com.goorm.BITA.domain.folder.FolderRepository;
import com.goorm.BITA.domain.s3.S3UploadService;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

        private final FileRepository fileRepository;
        private final FolderRepository folderRepository;
        private final S3UploadService s3UploadService;

    @Transactional
    public File createFile(Long folderId, FileCreateRequestDto requestDto, MultipartFile multipartFile) throws IOException {
        Folder folder = folderRepository.findById(folderId)
            .orElseThrow(() -> new RuntimeException("폴더를 찾을 수 없습니다."));
        // S3에 파일 업로드
        String fileUrl = s3UploadService.uploadFile(multipartFile, folder.getId());
        File createFile = File.createFile(requestDto.getName(), fileUrl, folder);
        folder.addFile(createFile);
        return fileRepository.save(createFile);
    }

    @Transactional
    public void updateFile(Long fileId, FileUpdateRequestDto requestDto) {
        File file = fileRepository.findById(fileId)
            .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다."));

        // 파일의 내용을 S3에 업데이트
        s3UploadService.updateFileContentInS3(file.getUrl(), requestDto.getContent());
        file.updateUpdatedAt(LocalDateTime.now());
        fileRepository.save(file);
    }

    // 파일 조회

    // 파일 수정

    // 파일 삭제
}
