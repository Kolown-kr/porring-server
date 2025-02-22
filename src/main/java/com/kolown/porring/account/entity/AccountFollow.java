package com.kolown.porring.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

@Entity
@Table(name = "accounts_follow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete(columnName = "deleted", strategy = SoftDeleteType.DELETED)
@Getter
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


    @Column(name = "nickname", columnDefinition = "VARCHAR(255)")
    private String nickname;

    public AccountFollow(Account follower, Account followee, String nickname) {
        this.follower = follower;
        this.followee = followee;
        this.nickname = nickname;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
