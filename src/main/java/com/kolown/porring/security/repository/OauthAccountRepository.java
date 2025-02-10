package com.kolown.porring.security.repository;

import com.kolown.porring.account.entity.OAuthAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthAccountRepository extends JpaRepository<OAuthAccount, String> {

    Optional<OAuthAccount> findByOauthNumber(String oauthNumber);
}
