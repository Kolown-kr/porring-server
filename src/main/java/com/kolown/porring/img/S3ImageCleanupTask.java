package com.kolown.porring.img;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class S3ImageCleanupTask {

    private final ImageUsageRepository imageUsageRepository;
    private final S3Service s3Service;

    @Scheduled(cron = "0 0 3 * * ?") // 매일 새벽 3시 실행
    public void cleanupUnusedImages() {
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        List<String> unusedImages = imageUsageRepository.findUnusedImagesOlderThan(threeDaysAgo);

        for (String s3Key : unusedImages) {
            s3Service.deleteS3Object(s3Key); // S3에서 삭제
            imageUsageRepository.deleteById(s3Key); // DB에서 삭제
        }
    }

}
