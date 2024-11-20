package site.talent_trade.api.dto.commnuity.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDTO {

    private Long commentId; //댓글 아이디

    private String nickname; //닉네임

    private String content; //내용

    private String talent; //재능 분야

    private String talentDetail; //세부 재능

    private LocalDateTime createdAt; //업로드 날짜

    private String gender; //성별
}
