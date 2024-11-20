package site.talent_trade.api.dto.commnuity.request;

import lombok.Builder;
import lombok.Data;
/*
댓글 데이터 전송 dto
* */
@Data
@Builder
public class CommentRequestDTO {
    Long writerId;

    Long postId;

    String content;
}
