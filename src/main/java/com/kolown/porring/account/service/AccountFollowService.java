package com.kolown.porring.account.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import com.kolown.porring.account.dto.response.NicknameAndPostsResponse;
import com.kolown.porring.account.dto.response.NicknameResponse;
import com.kolown.porring.account.entity.Account;
import com.kolown.porring.account.entity.AccountFollow;
import com.kolown.porring.account.repository.AccountFollowRepository;
import com.kolown.porring.account.repository.AccountRepository;
import com.kolown.porring.board.repository.BoardRepository;
import com.kolown.porring.board.service.BoardService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountFollowService {
    private final AccountFollowRepository accountFollowRepository;
    private final AccountRepository accountRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @Transactional
    public void createFollow(Long followerId, Long followeeId, String nickname) throws BadRequestException {
        Account follower = accountRepository.findById(followerId).orElseThrow();
        Account followee = accountRepository.findById(followeeId).orElseThrow();

        if (follower.getId() == followee.getId()) {
            throw new BadRequestException("자기 자신을 팔로우할 수 없습니다.");
        }

        Optional<AccountFollow> accountFollow = accountFollowRepository
                .findByFollowerIdAndFolloweeIdWithDeleted(followerId, followeeId);

        if (accountFollow.isPresent()) {
            AccountFollow newAccountFollow = accountFollow.get();
            // TODO: 이 방법이 올바른 방법인지 검증 필요
            accountFollowRepository.restoreById(newAccountFollow.getId(), nickname);
        } else {
            AccountFollow newAccountFollow = new AccountFollow(follower, followee, nickname);
            accountFollowRepository.save(newAccountFollow);
        }
    }

    @Transactional
    public void deleteFollow(Long followerId, Long followeeId) {
        AccountFollow followData = accountFollowRepository
                .findByFollowerIdAndFolloweeId(followerId, followeeId)
                .orElseThrow();

        // TODO: Soft delete 되지 않고 진짜 지우고 있음
        accountFollowRepository.delete(followData);
    }

    public NicknameResponse getNickname(Long followerId, Long followeeId) {
        var accountFollow = accountFollowRepository
                .findByFollowerIdAndFolloweeId(followerId, followeeId)
                .orElseThrow();
        return new NicknameResponse(accountFollow.getNickname());
    }

    public List<NicknameAndPostsResponse> getFollowingListById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        List<AccountFollow> followings = accountFollowRepository.findByFollowerId(account.getId());
        List<NicknameAndPostsResponse> followingResponses = new ArrayList<>();

        for (AccountFollow following : followings) {
            var posts = boardService.getTop4BoardsByAccountId(following.getFollowee().getId());

            followingResponses.add(NicknameAndPostsResponse
                    .builder()
                    .nickname(following.getNickname())
                    .posts(posts)
                    .build());
        }

        return followingResponses;
    }

    @Transactional
    public void updateNickname(Long followerId, Long followeeId, String nickname) {
        // 팔로우 관계인지 확인
        var accountFollow = accountFollowRepository
                .findByFollowerIdAndFolloweeId(followerId, followeeId)
                .orElseThrow();

        accountFollow.updateNickname(nickname);
        // 팔로우 관계가 없으면 에러 출력
        // 팔로우 관계가 있으면 닉네임을 수정
        accountFollowRepository.save(accountFollow);
    }
}
