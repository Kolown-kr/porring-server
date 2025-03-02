package com.kolown.porring.board.service;

import com.kolown.porring.account.entity.Account;
import com.kolown.porring.board.dto.request.ReactionRequestDto;
import com.kolown.porring.board.entity.Board;
import com.kolown.porring.board.entity.Reaction;
import com.kolown.porring.board.entity.ReactionType;
import com.kolown.porring.board.repository.BoardRepository;
import com.kolown.porring.board.repository.ReactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReactionService {
    private final ReactionRepository reactionRepository;
    private final BoardRepository boardRepository;

    public List<ReactionType> getBoardReactionList(Long boardId) {

        Set<ReactionType> reactionSet = new HashSet<>();

        reactionRepository.findByBoardId(boardId).forEach(reaction -> {
            reactionSet.add(reaction.getReactionType());
        });

        return new ArrayList<>(reactionSet);
    }

    public ReactionType getBoardReactionOfAccount(Long boardId, Account account) {
        return reactionRepository
            .findByAccountIdAndBoardId(account.getId(), boardId)
            .orElseThrow()
            .getReactionType();
    }

    @Transactional
    public void updateBoardReaction(
        Long boardId,
        Account account,
        ReactionRequestDto reactionRequestDto
    ) {
        Board board = boardRepository.findById(boardId).orElseThrow();

        Reaction.ReactionId id = new Reaction.ReactionId(boardId, account.getId());

        if (reactionRequestDto.getReactionType() == null) {
            reactionRepository.deleteById(id);
            return;
        }

        boolean reactionExists = reactionRepository.existsByBoardIdAndAccountIdWithDeleted(boardId, account.getId());

        if (reactionExists) {
            Reaction newReaction = new Reaction(board, account, reactionRequestDto.getReactionType());
            reactionRepository.save(newReaction);
            return;
        }

        reactionRepository.restore(boardId, account.getId(), reactionRequestDto.getReactionType().toString());
    }
}
