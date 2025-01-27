package com.kolown.porring.board.entity;

import com.kolown.porring.account.entity.Account;
import com.kolown.porring.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {
    @Id
    @Column(name = "board_id")
    private String id;

    @Column(name = "img_urls")
    private String imageUrls;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account user;

    @OneToMany(mappedBy = "boards")
    private List<Reaction> reactions = new ArrayList<>();
}