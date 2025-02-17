package com.kolown.porring.account.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Entity
@Table(name = "oauth_accounts")
@DiscriminatorValue(value = "OAUTH")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthAccount extends Account implements OAuth2User {
    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_type_code")
    private OAuthType oauthType;

    @Column(unique = true, nullable = false)
    private String oauthNumber;


    public OAuthAccount(OAuthType oauthType, String oauthNumber) {
        this.oauthType = oauthType;
        this.oauthNumber = oauthNumber;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

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
        return this.oauthNumber;
    }

    @Override
    public String getName() {
        return null;
    }
}
