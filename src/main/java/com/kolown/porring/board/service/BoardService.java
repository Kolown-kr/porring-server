package com.kolown.porring.board.service;

import java.util.List;

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

    public Board createBoard(CreateBoardRequestDto createBoardRequestDto, Account account) {
        // TODO: tag 들도 등록해야한다.
        // tag는 중복되지 않게 들어가야한다.
        Board board = new Board(account, createBoardRequestDto.getImageUrl(), createBoardRequestDto.getDescription());
        return boardRepository.save(board);
    }

    public List<Board> getBoardsByRandom(int count) {
        return boardRepository.findRandomBoards(count);
    }

    // TODO : 후에 Account 체크 하면 좋을 것 같습니다.
    public Board updateBoard(long boardId, UpdateBoardRequestDto updateBoardRequestDto) {

        Board board = boardRepository.findById(boardId).orElseThrow();

        if (!updateBoardRequestDto.getDescription().isEmpty()) {
            board.setDescription(updateBoardRequestDto.getDescription().orElseThrow());
        }

        boardRepository.save(board);
        // TODO : Elastic Search 를 사용하진 않아서 태그를 어떻게 할지는 보류 상태
        return board;
    }

    public void deleteBoard(long boardId) {
        boardRepository.deleteById(boardId);
    }
}
