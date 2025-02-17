package com.kolown.porring.board.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kolown.porring.account.entity.EmailAccount;
import com.kolown.porring.board.dto.request.CreateBoardRequestDto;
import com.kolown.porring.board.dto.request.ReactionRequestDto;
import com.kolown.porring.board.dto.request.UpdateBoardRequestDto;
import com.kolown.porring.board.dto.response.BoardResponseDto;
import com.kolown.porring.board.entity.Board;
import com.kolown.porring.board.entity.ReactionType;
import com.kolown.porring.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private static final int FETCH_BOARD_COUNT = 10;

    /*
     * 이미지 처리는 API 는 보류
     * 
     * @PostMapping("/images/upload")
     * public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile
     * file) {
     * try {
     * 
     * }
     * catch (Exception e) {
     * e.printStackTrace();
     * return ResponseEntity.badRequest().build();
     * }
     * }
     */
    @PostMapping()
    public ResponseEntity<BoardResponseDto> createBoard(
            @RequestBody CreateBoardRequestDto createBoardRequestDto,
            @AuthenticationPrincipal EmailAccount account) {
        Board board = boardService.createBoard(createBoardRequestDto, account);
        // TODO: myReaction, reactions, isFollower를 제외한 반환 중인데, 형식에 대한 논의 필요
        return ResponseEntity.ok(
                BoardResponseDto.builder()
                        .postId(board.getId())
                        .authorId(account.getId())
                        .imageUrl(board.getImgUrls())
                        .build());
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDto>> getBoards(
            @RequestParam int pageSize,
            @RequestParam int pageNumber,
            @RequestParam(required = false) String tag) {
        // TODO : 자신이 만든 포스트는 추가해선 안되는 내용을 추가해야합니다.
        // 랜덤 게시물에 대한 페이지네이션이 추가되어야합니다.
        // 이 문제는 단순히 구현될 비즈니스 로직이 아니라서 논의를 해봐야할 것 같습니다.
        var boards = boardService.getBoardsByRandom(FETCH_BOARD_COUNT);

        // 현재 당장 채울 수 없는 부분은 샘플 데이터 삽입해서 반환
        return ResponseEntity.ok(boards.stream().map(board -> BoardResponseDto.builder()
                .authorId(board.getAccount().getId())
                .postId(board.getId())
                .imageUrl(board.getImgUrls())
                .myReaction(ReactionType.HEART)
                .reactions(new ArrayList<>(Arrays.asList(ReactionType.HEART, ReactionType.LOVE)))
                .isFollower(false)
                .build())
                .collect(Collectors.toList()));
    }

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getTags(@RequestParam(required = false) String searchParam) {
        // TODO : 태그 기능은 엘라스틱서치 관련해서 반환해야하므로 일단 보류
        return ResponseEntity.ok(Arrays.asList(
                "tag1",
                "tag2",
                "tag3"));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable("boardId") long boardId) {
        return ResponseEntity.ok("success");
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<String> updateBoard(
            @PathVariable("boardId") long boardId,
            @RequestBody UpdateBoardRequestDto updateBoardRequestDto) {

        boardService.updateBoard(updateBoardRequestDto);
        return ResponseEntity.ok("success");
    }

    @PutMapping("/{boardId}/reaction")
    public ResponseEntity<String> updateBoardReaction(
            @PathVariable("boardId") long boardId,
            @RequestBody ReactionRequestDto reaction) {

        return ResponseEntity.ok("success");
    }
}
