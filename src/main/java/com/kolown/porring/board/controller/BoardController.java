package com.kolown.porring.board.controller;

import com.kolown.porring.board.exception.BoardPermissionException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kolown.porring.account.entity.Account;
import com.kolown.porring.board.dto.request.CreateBoardRequestDto;
import com.kolown.porring.board.dto.request.ReactionRequestDto;
import com.kolown.porring.board.dto.request.UpdateBoardRequestDto;
import com.kolown.porring.board.dto.response.BoardResponseDto;
import com.kolown.porring.board.entity.Board;
import com.kolown.porring.board.service.BoardService;
import com.kolown.porring.board.service.ReactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final ReactionService reactionService;

    @PostMapping()
    public ResponseEntity<BoardResponseDto> createBoard(
        @RequestBody CreateBoardRequestDto createBoardRequestDto,
        @AuthenticationPrincipal Account account
    ) {

        Board board = boardService.createBoard(createBoardRequestDto, account);
        // TODO: myReaction, reactions, isFollower를 제외한 반환 중인데, 형식에 대한 논의 필요
        return ResponseEntity.ok(
            BoardResponseDto.builder().postId(board.getId()).authorId(account.getId()).imageUrl(board.getImgUrls())
                .build());
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDto>> getBoards(
        @RequestParam int pageSize,
        @RequestParam int pageNumber,
        @RequestParam(required = false) String tag,
        @AuthenticationPrincipal Account account
    ) {
        // TODO : 랜덤 게시물에 대한 페이지네이션이 추가되어야합니다.
        var boards = boardService.getBoardsByRandom(pageSize, account);
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getTags(@RequestParam(required = false) String searchParam) {
        // TODO : 태그 기능은 엘라스틱서치 관련해서 반환해야하므로 일단 보류
        return ResponseEntity.ok(Arrays.asList("tag1", "tag2", "tag3"));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(
        @PathVariable("boardId") long boardId,
        @AuthenticationPrincipal Account account
    ) throws BoardPermissionException {
        boardService.deleteBoard(boardId, account);
        return ResponseEntity.ok("success");
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<String> updateBoard(
        @PathVariable("boardId") long boardId,
        @RequestBody UpdateBoardRequestDto updateBoardRequestDto,
        @AuthenticationPrincipal Account account
    ) throws BoardPermissionException {
        boardService.updateBoard(boardId, updateBoardRequestDto, account);
        return ResponseEntity.ok("success");
    }

    @PutMapping("/{boardId}/reaction")
    public ResponseEntity<String> updateBoardReaction(
        @PathVariable("boardId") long boardId,
        @RequestBody ReactionRequestDto reactionRequestDto,
        @AuthenticationPrincipal Account account
    ) {
        reactionService.updateBoardReaction(boardId, account, reactionRequestDto);
        return ResponseEntity.ok("success");
    }

    @ExceptionHandler(value = BoardPermissionException.class)
    public ResponseEntity<Map<String, String>> handleBoardPermissionException(BoardPermissionException e) {
        Map<String, String> map = new HashMap<>();
        map.put("error type", e.getStatus().getReasonPhrase());
        map.put("code", Integer.toString(e.getStatus().value()));
        map.put("message", e.getMessage());
        return new ResponseEntity<>(map, e.getStatus());
    }
}
