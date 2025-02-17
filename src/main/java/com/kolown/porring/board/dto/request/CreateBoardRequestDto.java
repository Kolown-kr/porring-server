package com.kolown.porring.board.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// 원래라면 Builder 를 만들지 않을 생각이나, 시딩을 위해 추가함
@Builder
@Getter
public class CreateBoardRequestDto {
    public String imageUrl; // 4:3 비율 + 선명
    public String thumbnailUrl; // 정사각형 비율 + 덜 선명
    public String galleryUrl; // 목록 보기용 - 4:3 비율 + 덜 선명
    public String description;
    public List<String> tags;
}
