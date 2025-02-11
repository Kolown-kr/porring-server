package com.kolown.porring.security.oauth;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OauthServiceFactory {

    private final KakaoOauthService kakaoOauthService;

    public OauthService getOauthService(String provider) {
        if ("kakao".equalsIgnoreCase(provider)) {
            return kakaoOauthService;
        }
        throw new IllegalArgumentException("지원되지 않는 OAuth 공급자입니다: " + provider);
    }
}
