package com.goorm.BITA.domain.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

    public String uploadFile(MultipartFile multipartFile, Long folderId) throws IOException {
        String key = "container" + "/" + folderId + "_" + Instant.now().toEpochMilli();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, key, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, key).toString();
    }

    public void updateFileContentInS3(String fileUrl, String newContent) {
        String fileKey = extractFileKeyFromUrl(fileUrl);

        ObjectMetadata metadata = new ObjectMetadata();
        byte[] contentAsBytes = newContent.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream contentAsStream = new ByteArrayInputStream(contentAsBytes);
        metadata.setContentLength(contentAsBytes.length);
        metadata.setContentType("text/plain");
        metadata.setContentDisposition("inline");
        amazonS3.putObject(bucket, fileKey, contentAsStream, metadata);
    }

    // URL에서 파일 키 추출하는 메서드
    private String extractFileKeyFromUrl(String fileUrl) {
        URI uri = URI.create(fileUrl);
        String path = uri.getPath();
        return path.substring(path.indexOf('/') + 1);
    }
}