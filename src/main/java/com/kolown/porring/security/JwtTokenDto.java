package com.kolown.porring.security;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class JwtTokenDto {
    private String type;
    private String accessToken;
    private String refreshToken;
}
