package com.kolown.porring.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kolown.porring.board.entity.Reaction;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Reaction.ReactionId> {
    List<Reaction> findByBoardId(@Param("boardId") Long boardId);

    Long countByBoardId(@Param("boardId") Long boardId);

    Optional<Reaction> findByAccountIdAndBoardId(@Param("accountId") Long accountId, @Param("boardId") Long boardId);

    @Query(value = "SELECT EXISTS (SELECT * FROM reactions r " +
        "WHERE r.board_id = :boardId AND r.account_id = :accountId) AS exists",
        nativeQuery = true)
    boolean existsByBoardIdAndAccountIdWithDeleted(
        @Param("boardId") Long boardId,
        @Param("accountId") Long accountId
    );

    @Modifying
    @Query(value =
        "UPDATE reactions SET deleted = false, react_code = :reactionType " +
            "WHERE board_id = :boardId AND account_id = :accountId", nativeQuery = true)
    void restore(
        @Param("boardId") Long boardId,
        @Param("accountId") Long accountId,
        @Param("reactionType") String reactionType
    );
}
