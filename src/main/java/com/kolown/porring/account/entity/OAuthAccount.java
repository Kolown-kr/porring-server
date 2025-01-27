package com.kolown.porring.account.entity;

import com.kolown.porring.account.enums.OAuthType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oauth_accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthAccount {
    @Id
    @Column(name = "account_id")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_type_code", nullable = false)
    private OAuthType oauthType;

    @Column(name = "oauth_number", nullable = false)
    private String oauthNumber;  // OAuth 제공자로부터 받은 고유 식별자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    // 생성자
    @Builder
    public OAuthAccount(OAuthType oauthType, String oauthNumber) {
        this.oauthType = oauthType;
        this.oauthNumber = oauthNumber;
    }
}