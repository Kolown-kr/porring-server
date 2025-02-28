package com.kolown.porring.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kolown.porring.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT * FROM boards WHERE account_id != :accountId ORDER BY RAND() LIMIT :fetchCount", nativeQuery = true)
    List<Board> findRandomBoardsOfOthers(@Param("fetchCount") int fetchCount, @Param("accountId") Long accountId);

    List<Board> findByAccountId(Long accountId);

    List<Board> findTop4ByAccountIdOrderByCreatedAtDesc(Long id);

}
