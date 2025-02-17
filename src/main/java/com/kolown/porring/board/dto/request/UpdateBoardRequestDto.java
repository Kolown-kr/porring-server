package com.kolown.porring.board.dto.request;

import java.util.List;
import java.util.Optional;

import lombok.Getter;

@Getter
public class UpdateBoardRequestDto {

    public Long postId;
    public Optional<String> description;
    public Optional<List<String>> tags;

}
