package com.kolown.porring.security.repository;

import com.kolown.porring.account.entity.EmailAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailAccountRepository extends JpaRepository<EmailAccount, Long> {
    boolean existsByEmail(String email);
    Optional<EmailAccount> findByEmail(String email);
}
