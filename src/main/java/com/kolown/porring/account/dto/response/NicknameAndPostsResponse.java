package com.kolown.porring.account.dto.response;

import java.util.List;

import com.kolown.porring.board.dto.response.BoardResponseDto;

import lombok.Builder;

@Builder
public class NicknameAndPostsResponse {
    public String nickname;
    public List<BoardResponseDto> posts;
}
