package com.goorm.BITA.domain.s3;

import com.goorm.BITA.domain.s3.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private final S3UploadService s3UploadService;

    @Autowired
    public FileUploadController(S3UploadService s3UploadService) {
        this.s3UploadService = s3UploadService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // S3에 파일 업로드하고 업로드된 파일의 URL 반환
            String fileUrl = s3UploadService.saveFile(file);
            return "File uploaded successfully. URL: " + fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed: " + e.getMessage();
        }
    }
}
