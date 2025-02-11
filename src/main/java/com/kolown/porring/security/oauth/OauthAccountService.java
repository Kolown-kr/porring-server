package com.kolown.porring.security.oauth;

import com.kolown.porring.account.entity.OAuthAccount;
import com.kolown.porring.account.entity.OAuthType;
import com.kolown.porring.security.dto.JwtTokenDto;
import com.kolown.porring.security.jwt.CustomUserDetailsService;
import com.kolown.porring.security.jwt.JwtService;
import com.kolown.porring.security.repository.OauthAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthAccountService {

    private final OauthAccountRepository oauthAccountRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    public OAuthAccount getOrCreateOauthAccount(OAuthType oauthType, String oauthNumber) {
        return oauthAccountRepository.findByOauthTypeAndOauthNumber(oauthType, oauthNumber)
                .orElseGet(() -> createOauthAccount(oauthType, oauthNumber));
    }

    private OAuthAccount createOauthAccount(OAuthType oauthType, String oauthNumber) {
        OAuthAccount newAccount = new OAuthAccount(oauthType, oauthNumber);
        return oauthAccountRepository.save(newAccount);
    }

    public JwtTokenDto loginByOauth(OAuthType oAuthType, String oauthNumber) {
        UserDetails userDetails = customUserDetailsService.loadUserByOauthNumber(oAuthType, oauthNumber);

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authRequest);

        String token = jwtService.generateToken(authRequest);

        return JwtTokenDto.builder()
                .type("Bearer ")
                .accessToken(token)
                .build();
    }

}
