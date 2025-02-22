package com.kolown.porring.board.dto.response;

import com.kolown.porring.board.entity.Reaction;
import com.kolown.porring.board.entity.ReactionType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Board 를 반환할 때 사용하는 DTO
 */
@Getter
@Builder
public class BoardResponseDto {
    public long postId;
    public long authorId;
    public String imageUrl;
    public boolean isFollower;
    public List<ReactionType> reactions;
    public ReactionType myReaction;
}
