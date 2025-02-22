package com.kolown.porring.common.seed;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.kolown.porring.account.entity.EmailAccount;
import com.kolown.porring.board.dto.request.CreateBoardRequestDto;
import com.kolown.porring.board.service.BoardService;
import com.kolown.porring.security.repository.EmailAccountRepository;

import lombok.RequiredArgsConstructor;

/*
 * Develop 환경을 위한 샘플 데이터 시딩을 위한 클래스
 *
 * 해당 클래스는 단순 CommandLineRunner 이며, 샘플데이터를 Java에서 넣어주고 있습니다.
 * DDL 보다 이 방법이 데이터를 엄밀하게 넣어줄 수 있을 것 같아 이 방법을 사용합니다.
 *
 * 다른 의견이 있다면 알려주세요.
 */
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DevDataInjector implements CommandLineRunner {

    private final BoardService boardService;
    private final EmailAccountRepository emailAccountRepository;

    @Override
    public void run(String... args) throws Exception {
        // 샘플 데이터용 유저 작성
        var user = new EmailAccount("test@naver.com", "test1234");

        emailAccountRepository.save(user);
        /*
         * 샘플 포스트 작성 (20개 정도)
         * 샘플 데이터 이미지 URL 은 전부 플레이스 홀더 이미지이다.
         * 썸네일 및 갤러리 이미지는 아래에 해당된다.
         *
         * 원본이미지 링크 https://placehold.co/1200x900/png
         * 썸네일이미지 링크 https://placehold.co/300x300/png
         * 갤러리이미지 링크 https://placehold.co/400x300/png
         * 안드로이드 요청을 반영한 결과이므로 실제 API에서도 위처럼 적용되어야한다.
         *
         */
        for (int i = 0; i < 20; ++i) {
            var tags = new ArrayList<String>();
            tags.add("tag" + i);

            var createBoardDto = CreateBoardRequestDto
                    .builder()
                    .description("테스트 데이터 " + i)
                    .imageUrl("https://placehold.co/1200x900/png")
                    .tags(tags)
                    .build();

            boardService.createBoard(createBoardDto, user);
        }
    }
}
