package com.goorm.BITA.domain.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.goorm.BITA.domain.file.info.*;
import com.goorm.BITA.domain.folder.Folder;
import com.goorm.BITA.domain.folder.FolderRepository;
import com.goorm.BITA.domain.s3.S3UploadService;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final S3UploadService s3UploadService;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

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
    public ReadResponseFileInfo readFile(long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다."));

        String name = bucketName;
        String key = file.getUrl();

        String s3Endpoint = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(bucketName + ".s3." + amazonS3Client.getRegion() + ".amazonaws.com/")
                .build()
                .toUriString();

        key = key.replace(s3Endpoint, "");

        S3Object s3Object = amazonS3Client.getObject(name, key);

        try (S3ObjectInputStream content = s3Object.getObjectContent()) {
            String fileContent = IOUtils.toString(content);
            return new ReadResponseFileInfo(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일을 읽을 수 없습니다.");
        }
    }

    // 파일 수정

    // 파일 삭제
}
