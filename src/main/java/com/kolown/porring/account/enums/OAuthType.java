package com.kolown.porring.account.enums;

public enum OAuthType {
    KAKAO("kakao"),
    NAVER("naver"),
    GOOGLE("google");

    private final String provider;

    OAuthType(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }
}
