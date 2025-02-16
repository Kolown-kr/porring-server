package com.kolown.porring.security.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtTokenDto {
    private String type;
    private String accessToken;
    private String refreshToken;
}
