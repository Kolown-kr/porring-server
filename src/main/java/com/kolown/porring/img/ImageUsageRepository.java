package com.kolown.porring.img;

import com.kolown.porring.img.entity.ImageUsage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageUsageRepository extends JpaRepository<ImageUsage,String> {

    // board_id가 null이고 주어진 날짜 이전에 생성된 이미지 url조회
    @Query("SELECT i.s3Key FROM ImageUsage i WHERE i.boardId IS NULL AND i.createdAt <= :beforeDate")
    List<String> findUnusedImagesOlderThan(@Param("beforeDate") LocalDateTime beforeDate);

    // 삭제된 이미지 DB에서 삭제
    // 사용된 이미지 DB에서 삭제
    void deleteByS3Key(String s3Key);

    Optional<ImageUsage> findImageUsageByS3Key(@Param("s3key") String s3key);

}
