package com.kolown.porring.security.jwt;

import com.kolown.porring.account.entity.OAuthAccount;
import com.kolown.porring.account.entity.OAuthType;
import com.kolown.porring.security.repository.EmailAccountRepository;
import com.kolown.porring.security.repository.OauthAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmailAccountRepository emailAccountRepository;
    private final OauthAccountRepository oauthAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return emailAccountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public UserDetails loadUserByOauthNumber(OAuthType oAuthType, String oauthNumber) {
        return oauthAccountRepository.findByOauthTypeAndOauthNumber(oAuthType, oauthNumber)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }


}
