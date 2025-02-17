package com.kolown.porring.board.repository;

import com.kolown.porring.board.entity.Board;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long> {
    @Query(value = "SELECT * FROM boards ORDER BY RAND() LIMIT :fetchCount", nativeQuery = true)
    List<Board> findRandomBoards(@Param("fetchCount") int fetchCount);
}
