package com.kolown.porring.security;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtTokenDto {
    private String type;
    private String accessToken;
    private String refreshToken;
}
