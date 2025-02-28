package com.kolown.porring.account.repository;

import com.kolown.porring.account.entity.AccountFollow;
import org.hibernate.annotations.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountFollowRepository extends JpaRepository<AccountFollow, Long> {
    Optional<AccountFollow> findByFollowerIdAndFolloweeId(long followerId, long followeeId);

    List<AccountFollow> findByFollowerId(long followerId);

    /**
     * Soft 삭제된 레코드를 포함하여 조회하는 메서드
     */
    @Query(value = """
            SELECT * FROM accounts_follow f
                        WHERE f.follower_id = :followerId AND f.followee_id = :followeeId
            """, nativeQuery = true)
    Optional<AccountFollow> findByFollowerIdAndFolloweeIdWithDeleted(
            @Param("followerId") Long followerId,
            @Param("followeeId") Long followeeId
    );

    @Modifying
    @Query(value = "UPDATE accounts_follow SET deleted = false, nickname = :nickname WHERE account_follow_id = :id", nativeQuery = true)
    void restoreById(long id, String nickname);
}
