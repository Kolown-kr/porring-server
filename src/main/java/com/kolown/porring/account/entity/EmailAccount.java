package com.kolown.porring.account.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email_accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAccount {
    @Id
    @Column(name = "account_id")
    private String id;

    @Column(name = "email", nullable = false, length = 320)  // RFC 5321 기준
    private String email;

    @Column(name = "password", nullable = false, length = 60)  // BCrypt 해시 길이
    private String password;  // 암호화된 비밀번호 저장

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account user;

    // 생성자
    @Builder
    public EmailAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }
}