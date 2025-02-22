package com.kolown.porring.img;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/imgs")
public class ImageController {

    private final S3Service s3Service;

    @PostMapping("")
    public ResponseEntity<String> uploadImg(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        String url = s3Service.uploadFile(multipartFile);
        return ResponseEntity.ok(url);
    }

}
