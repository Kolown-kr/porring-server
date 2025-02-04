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
public class EmailAccount extends Account{

    @Column(name = "email", nullable = false, length = 320)  // RFC 5321 기준
    private String email;

    @Column(name = "password", nullable = false,  columnDefinition = "CHAR(60)")  // BCrypt 해시 길이
    private String password;

    @Builder
    public EmailAccount(String email, String password) {
        super("email");
        this.email = email;
        this.password = password;
    }
}