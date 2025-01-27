package com.kolown.porring.board.entity;

import com.kolown.porring.account.entity.Account;
import com.kolown.porring.board.enums.ReactionType;
import com.kolown.porring.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account user;

    @Enumerated(EnumType.STRING)
    private ReactionType type;

    private String comment;  // 코멘트가 있는 경우
}