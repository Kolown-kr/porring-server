package com.kolown.porring.security;

import com.kolown.porring.account.entity.OAuthAccount;
import com.kolown.porring.account.entity.OAuthType;
import com.kolown.porring.account.repository.EmailAccountRepository;
import com.kolown.porring.account.repository.OauthAccountRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService implements UserDetailsService {

    private final EmailAccountRepository emailAccountRepository;
    private final OauthAccountRepository oauthAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return emailAccountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuthType platform = OAuthType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String oauthId = attributes.get("id").toString();

        return oauthAccountRepository.findByOauthNumber(oauthId).orElseGet(
                () -> {
                    OAuthAccount newAccount = new OAuthAccount(platform, oauthId);
                    return oauthAccountRepository.save(newAccount);
                });
    }
}
