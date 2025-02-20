package com.kolown.porring.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kolown.porring.board.entity.Reaction;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Reaction.ReactionId> {
    List<Reaction> findByIdBoardId(@Param("boardId") Long boardId);
}
