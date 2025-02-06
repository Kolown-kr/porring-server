package com.kolown.porring.security;

import com.kolown.porring.account.entity.EmailAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final PasswordEncoder passwordEncoder;
    private final EmailAccountRepository emailAccountRepository;
    private final JwtService jwtService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @Transactional
    public void joinByEmailAndPassword(JoinDto joinDto) throws RuntimeException {

        boolean isExistEmail = emailAccountRepository.existsByEmail(joinDto.email());
        if (isExistEmail) {
            throw new RuntimeException("이미 존재하는 이메일 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(joinDto.password());
        EmailAccount newEmailAccount = new EmailAccount(joinDto.email(), encodedPassword);
        emailAccountRepository.save(newEmailAccount);
    }

    public JwtTokenDto loginByEmailAndPassword(JoinDto joinDto) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(joinDto.email(), joinDto.password());

        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(usernamePasswordAuthenticationToken);

        String token = jwtService.generateToken(authentication);

        return JwtTokenDto.builder()
                .accessToken(token)
                .build();
    }
}
