package site.talent_trade.api.dto.commnuity.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostResponseDTO {

    private Long postId; //포스트 아이디

    private String nickname; //닉네임

    private String title; //제목

    private String content; //내용

    private String talent; //재능 분야

    private String talentDetail; //세부 재능

    private LocalDateTime createdAt; //업로드 날짜

    private int hitCount; //조회수

    private int commentCount; // 댓글 개수 추가

    private String gender; //성별 -> 프로필 이미지 때문에
}
