package com.kolown.porring.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "email_accounts")
@DiscriminatorValue(value = "EMAIL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAccount extends Account {
    private String email;

    @Column(columnDefinition = "CHAR(60)")
    private String password;

    public EmailAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }
}
