package com.kolown.porring.account.entity;

import com.kolown.porring.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "sub_type")
@Getter
public abstract class Account extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
}
