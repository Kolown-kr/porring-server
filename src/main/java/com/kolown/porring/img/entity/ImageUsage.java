package com.kolown.porring.img.entity;

import com.kolown.porring.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "image_usage")
@NoArgsConstructor
@Getter
public class ImageUsage extends BaseTimeEntity {
    @Id
    @Column(name = "s3_key", length = 255, nullable = false)
    private String s3Key;

    @Column(name = "board_id")
    @Nullable
    private Long boardId;

    public ImageUsage(String s3Key) {
        this.s3Key = s3Key;
        this.boardId = null;
    }

    public void enrollBoardId(Long boardId){
        this.boardId = boardId;
    }
}
