package com.kolown.porring.security.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kakao")
@Getter
@Setter
public class KakaoConfig {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String authorizeRequestUrl;
    private String tokenRequestUrl;
    private String userInfoRequestUrl;

    public String getFormattedAuthorizeUrl() {
        return String.format(authorizeRequestUrl, clientId, redirectUri);
    }
}

