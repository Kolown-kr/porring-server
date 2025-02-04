package com.kolown.porring.security;

import com.kolown.porring.account.entity.Account;
import com.kolown.porring.account.entity.EmailAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final PasswordEncoder passwordEncoder;
    private final EmailAccountRepository emailAccountRepository;

    @Transactional
    public void joinByEmailAndPassword(JoinDto joinDto) throws RuntimeException {

        boolean isExistEmail = emailAccountRepository.existsByEmail(joinDto.getEmail());
        if(isExistEmail){
            throw new RuntimeException("이미 존재하는 이메일 입니다.");
        }

        EmailAccount newEmailAccount = EmailAccount.builder()
                .email(joinDto.getEmail())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .build();
        emailAccountRepository.save(newEmailAccount);
    }
}
