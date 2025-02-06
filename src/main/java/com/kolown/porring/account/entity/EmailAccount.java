package com.kolown.porring.account.entity;

import jakarta.persistence.*;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "email_accounts")
@Getter
@DiscriminatorValue("email")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAccount extends Account implements UserDetails {

    public EmailAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Column(name = "email", nullable = false, length = 320)  // RFC 5321 기준
    private String email;

    @Column(name = "password", nullable = false,  columnDefinition = "CHAR(60)")  // BCrypt 해시 길이
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }
}