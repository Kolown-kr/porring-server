package com.kolown.porring.security.repository;

import com.kolown.porring.account.entity.OAuthAccount;
import com.kolown.porring.account.entity.OAuthType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthAccountRepository extends JpaRepository<OAuthAccount, String> {

    Optional<OAuthAccount> findByOauthNumber(String oauthNumber);

    Optional<OAuthAccount> findByOauthTypeAndOauthNumber(OAuthType oauthType, String oauthNumber);

}
