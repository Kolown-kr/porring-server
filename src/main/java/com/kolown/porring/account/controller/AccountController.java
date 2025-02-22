package com.kolown.porring.account.controller;

import com.kolown.porring.account.dto.request.CreateFollowRequest;
import com.kolown.porring.account.dto.request.UpdateFollowNicknameRequest;
import com.kolown.porring.account.dto.response.GetFollowingResponse;
import com.kolown.porring.account.dto.response.GetNicknameResponse;
import com.kolown.porring.account.entity.Account;
import com.kolown.porring.account.service.AccountFollowService;
import com.kolown.porring.security.dto.JoinDto;
import com.kolown.porring.security.dto.JwtTokenDto;
import com.kolown.porring.security.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final JoinService joinService;
    private final AccountFollowService accountFollowService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody JoinDto joinDto) {
        JwtTokenDto token = joinService.loginByEmailAndPassword(joinDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinDto joinDto) {
        joinService.joinByEmailAndPassword(joinDto);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/{targetId}/follow")
    public ResponseEntity<String> createFollow(
            @PathVariable Long targetId,
            @RequestBody CreateFollowRequest createFollowRequest,
            @AuthenticationPrincipal Account account
    ) {
        accountFollowService.createFollow(
                account.getId(),
                targetId,
                createFollowRequest.getUsername()
        );

        return ResponseEntity.ok("success");
    }


    @GetMapping("/{targetId}")
    public ResponseEntity<GetNicknameResponse> getNickname(
            @PathVariable Long targetId,
            @AuthenticationPrincipal Account account
    ) {
        return ResponseEntity.ok(accountFollowService.getNickname(account.getId(), targetId));
    }

    @PutMapping("/{targetId}/follow/nickname")
    public ResponseEntity<String> updateFollowNickname(
            @PathVariable Long targetId,
            @RequestBody UpdateFollowNicknameRequest updateFollowNicknameRequest,
            @AuthenticationPrincipal Account account
    ) {
        accountFollowService.updateNickname(
                account.getId(),
                targetId,
                updateFollowNicknameRequest.getNickname()
        );

        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{targetId}/follow")
    public ResponseEntity<String> deleteFollow(
            @PathVariable Long targetId,
            @AuthenticationPrincipal Account account
    ) {
        accountFollowService.deleteFollow(account.getId(), targetId);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/followings")
    public ResponseEntity<List<GetFollowingResponse>> getMyFollowers(
            @AuthenticationPrincipal Account account
    ) {
        return ResponseEntity.ok(accountFollowService.getFollowingListById(account.getId()));
    }

    @DeleteMapping
    public String deleteAccount() {
        return "success";
    }
}
