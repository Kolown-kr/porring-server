package com.kolown.porring.board.dto.request;

import java.util.List;
import java.util.Optional;

import lombok.Getter;

@Getter
public class UpdateBoardRequestDto {

    public String description;
    public List<String> tags;

}
