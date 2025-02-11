package com.kolown.porring.security;

import com.kolown.porring.account.entity.OAuthAccount;
import com.kolown.porring.security.dto.JwtTokenDto;
import com.kolown.porring.security.dto.TokenResponseDTO;
import com.kolown.porring.security.dto.UserProfileDTO;
import com.kolown.porring.security.jwt.JwtService;
import com.kolown.porring.security.oauth.OauthAccountService;
import com.kolown.porring.security.oauth.OauthService;
import com.kolown.porring.security.oauth.OauthServiceFactory;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthServiceFactory oauthServiceFactory;
    private final OauthAccountService oauthAccountService;
    private final JwtService jwtService;

    @GetMapping("/oauth/login")
    public void socialLogin(@RequestParam("platform") String platform, HttpServletResponse response)
            throws IOException {
        OauthService oauthService = oauthServiceFactory.getOauthService(platform);
        response.sendRedirect(oauthService.getAuthorizationUrl());
    }

    @GetMapping("/oauth/code")
    public ResponseEntity<JwtTokenDto> socialCallback(@RequestParam("platform") String platform,
                                                      @RequestParam(value = "code", required = false) String code,
                                                      HttpServletResponse response)
            throws IOException {
        if (code == null) {
            throw new IllegalArgumentException("OAuth 코드가 전달되지 않았습니다.");
        }
        OauthService oauthService = oauthServiceFactory.getOauthService(platform);
        TokenResponseDTO accessTokenDTO = oauthService.getAccessToken(code)
                .orElseThrow(() -> new RuntimeException(platform + " 로그인 실패"));

        UserProfileDTO userProfileDTO = oauthService.getUserProfile(accessTokenDTO.getAccessToken());

        OAuthAccount oAuthAccount = oauthAccountService.getOrCreateOauthAccount(userProfileDTO.getOAuthType(),
                userProfileDTO.getOauthNumber());

        JwtTokenDto jwtTokenDto = oauthAccountService.loginByOauth(oAuthAccount.getOauthType(),
                oAuthAccount.getOauthNumber());

        return ResponseEntity.ok(jwtTokenDto);
    }

}
