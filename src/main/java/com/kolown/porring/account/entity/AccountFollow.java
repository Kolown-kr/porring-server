package com.kolown.porring.account.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "accounts_follow")
public class AccountFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_follow_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", referencedColumnName = "account_id", nullable = false)
    private Account follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id", referencedColumnName = "account_id", nullable = false)
    private Account followee;
}
