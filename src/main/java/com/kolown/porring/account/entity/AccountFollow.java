package com.kolown.porring.account.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_follow")
public class AccountFollow {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account follower;  // 팔로우를 하는 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    private Account following; // 팔로우를 받는 사용자

    private LocalDateTime createdAt;
    // 추가될 수 있는 비즈니스 필드들
    // private boolean isMuted;
    // private boolean isBlocked;
}
