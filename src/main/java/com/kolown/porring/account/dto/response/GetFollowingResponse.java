package com.kolown.porring.account.dto.response;

import com.kolown.porring.board.dto.response.BoardResponseDto;
import lombok.Builder;

import java.util.List;

@Builder
public class GetFollowingResponse {
    public String nickname;
    public List<BoardResponseDto> posts;
}
