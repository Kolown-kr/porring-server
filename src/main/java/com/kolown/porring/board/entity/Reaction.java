package com.kolown.porring.board.entity;

import com.kolown.porring.account.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "reactions")
@Getter
public class Reaction {
    @EmbeddedId
    private ReactionId id;

    @MapsId("boardId")
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @MapsId("accountId")
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "react_code")
    private ReactionType reactionType;

    private boolean deleted;
}
