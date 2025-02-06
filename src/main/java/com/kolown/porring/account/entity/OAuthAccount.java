package com.kolown.porring.account.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private OAuthType oauthType;

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
        this.oauthType = oauthType;
        this.oauthNumber = oauthNumber;
    }
}
