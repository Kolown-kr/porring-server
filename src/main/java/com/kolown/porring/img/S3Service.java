package com.kolown.porring.img;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    public String uploadFile(MultipartFile file) throws IOException {
        //TODO 현재는 서버에 저장하고 S3에 다시 업로드 하고 있는데 -> pre-signed-url로 교체해야할 필요가 있음

        String randomFileName = UUID.randomUUID().toString();
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String s3Key = randomFileName + fileExtension;

        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        file.transferTo(tempFile.toFile());

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(s3Key)
                        .build(),
                RequestBody.fromFile(tempFile)
        );

        Files.delete(tempFile);

        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + s3Key;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return ""; // 확장자가 없으면 빈 문자열 반환
        }
        return fileName.substring(fileName.lastIndexOf(".")); // 확장자 유지
    }
    public void deleteS3Object(String fileKey) {
        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileKey)
                        .build()
        );
    }


}
