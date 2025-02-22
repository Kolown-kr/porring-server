package com.kolown.porring.board.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// 원래라면 Builder 를 만들지 않을 생각이나, 시딩을 위해 추가함
@Builder
@Getter
public class CreateBoardRequestDto {
    // TODO: 원본 이미지를 받고, 썸네일용, 갤러리용 이미지를 반환할 필요가 있음
    public String imageUrl;
    public String description;
    public List<String> tags;
}
