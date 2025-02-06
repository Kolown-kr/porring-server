package com.kolown.porring.board.entity;

import com.kolown.porring.account.entity.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReactionId {
    private Long boardId;
    private Long accountId;
}
