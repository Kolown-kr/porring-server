package com.kolown.porring.board.service;

import com.kolown.porring.account.entity.AccountFollow;
import com.kolown.porring.account.repository.AccountFollowRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.kolown.porring.board.dto.response.BoardResponseDto;
import com.kolown.porring.board.entity.ReactionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.kolown.porring.account.entity.Account;
import com.kolown.porring.board.dto.request.CreateBoardRequestDto;
import com.kolown.porring.board.dto.request.UpdateBoardRequestDto;
import com.kolown.porring.board.entity.Board;
import com.kolown.porring.board.repository.BoardRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReactionService reactionService;
    private final AccountFollowRepository accountFollowRepository;

    public Board createBoard(CreateBoardRequestDto createBoardRequestDto, Account account) {
        // TODO: tag 들도 등록해야한다.
        Board board = new Board(account, createBoardRequestDto.getImageUrl(), createBoardRequestDto.getDescription());
        return boardRepository.save(board);
    }

    // TODO: 두 계정이 같을 경우 어떻게 해야하는가
    public List<BoardResponseDto> getBoardsOfAccount(Account account, Account targetAccount) {

        Optional<AccountFollow> accountFollow =
            accountFollowRepository.findByFollowerIdAndFolloweeId(account.getId(), targetAccount.getId());

        return boardRepository.findByAccountId(targetAccount.getId()).stream().map(board ->
                BoardResponseDto.builder()
                    .postId(board.getId())
                    .authorId(board.getAccount().getId())
                    .myReaction(reactionService.getBoardReactionOfAccount(board.getId(), account))
                    .imageUrl(board.getImgUrls())
                    .isFollower(accountFollow.isPresent())
                    .tags(List.of())
                    .reactions(reactionService.getBoardReactionList(board.getId()))
                    .build())
            .collect(Collectors.toList());
    }


    // TODO: 성능 문제가 생기지 않을까?
    @Transactional
    public List<BoardResponseDto> getBoardsByRandom(int count, Account account) {
        return boardRepository.findRandomBoardsOfOthers(count, account.getId()).stream().map(board -> {
            List<ReactionType> reactions = reactionService.getBoardReactionList(board.getId());
            ReactionType myReaction = reactionService.getBoardReactionOfAccount(board.getId(), account);
            // TODO: 해당 함수 구현 필요

            return BoardResponseDto.builder()
                .postId(board.getId())
                .imageUrl(board.getImgUrls())
                .isFollower(false)
                .authorId(board.getAccount().getId())
                .tags(Arrays.asList())
                .reactions(reactions)
                .myReaction(myReaction)
                .build();
        }).collect(Collectors.toList());
    }

    // TODO : 후에 Account 체크 하면 좋을 것 같습니다.
    @Transactional
    public Board updateBoard(long boardId, UpdateBoardRequestDto updateBoardRequestDto, Account account) {

        Board board = boardRepository.findById(boardId).orElseThrow();

        if (!validateBoardPermissions(board, account)) {
            throw new Error("You are not allowed to update this board");
        }

        if (updateBoardRequestDto.getDescription() != null) {
            board.updateDescription(updateBoardRequestDto.getDescription());
        }

        if (updateBoardRequestDto.getTags() != null) {
            board.replaceTags(updateBoardRequestDto.getTags());
        }

        boardRepository.save(board);
        // TODO : Elastic Search 를 사용하진 않아서 태그를 어떻게 할지는 보류 상태
        return board;
    }

    @Transactional
    public void deleteBoard(long boardId, Account account) {
        Board board = boardRepository.findById(boardId).orElseThrow();

        if (!validateBoardPermissions(board, account)) {
            throw new Error("You are not allowed to delete this board");
        }

        boardRepository.deleteById(boardId);
    }

    public boolean validateBoardPermissions(Board board, Account account) {
        return board.getAccount().getId().equals(account.getId());
    }
}
