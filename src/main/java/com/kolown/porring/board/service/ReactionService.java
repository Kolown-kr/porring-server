package com.kolown.porring.board.service;

import com.kolown.porring.account.entity.Account;
import com.kolown.porring.board.dto.request.ReactionRequestDto;
import com.kolown.porring.board.entity.Board;
import com.kolown.porring.board.entity.Reaction;
import com.kolown.porring.board.repository.BoardRepository;
import com.kolown.porring.board.repository.ReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReactionService {
    private final ReactionRepository reactionRepository;
    private final BoardRepository boardRepository;

    public List<Reaction> getAllReactionsByBoardId(Long boardId) {
        return reactionRepository.findByBoardId(boardId);
    }

    public Reaction createReaction(Account account, long boardId, ReactionRequestDto reactionRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow();

        Reaction reaction = new Reaction(board, account, reactionRequestDto.getReactionType());

        return reactionRepository.save(reaction);
    }

    public Reaction updateBoardReaction(Long boardId, Long accountId, ReactionRequestDto reactionRequestDto) {
        Reaction.ReactionId id = new Reaction.ReactionId(boardId, accountId);

        Reaction reaction = reactionRepository.findById(id).orElseThrow();

        reaction.setReactionType(reactionRequestDto.getReactionType());

        return reactionRepository.save(reaction);
    }
}
