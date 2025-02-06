package com.kolown.porring.board.entity;

import com.kolown.porring.account.entity.Account;
import com.kolown.porring.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column
    private String imgUrls;

    @Column
    private String description;

    public Board(Account account, String imgUrls, String description) {
        this.account = account;
        this.imgUrls = imgUrls;
        this.description = description;
    }
}
