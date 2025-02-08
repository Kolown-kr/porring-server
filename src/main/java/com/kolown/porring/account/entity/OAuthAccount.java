package com.kolown.porring.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "oauth_accounts")
@DiscriminatorValue(value = "OAUTH")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthAccount extends Account {
    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_type_code")
    private OAuthType type;

    @Column(unique = true, nullable = false)
    private Long oauthNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    public OAuthAccount(OAuthType oauthType, Long oauthNumber) {
        this.type = oauthType;
        this.oauthNumber = oauthNumber;
    }
}
