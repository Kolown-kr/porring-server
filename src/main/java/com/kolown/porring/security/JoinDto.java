package com.kolown.porring.security;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JoinDto {
    private String email;
    private String password;
}
