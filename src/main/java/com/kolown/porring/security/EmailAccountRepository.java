package com.kolown.porring.security;

import com.kolown.porring.account.entity.EmailAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailAccountRepository extends JpaRepository<EmailAccount, Long> {
    boolean existsByEmail(String email);
    EmailAccount findByEmail(String email);
}
