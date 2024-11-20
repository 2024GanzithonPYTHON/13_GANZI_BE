package site.talent_trade.api.dto.commnuity.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostDetailDTO {
    //게시글 상세 조회 DTO

    private PostResponseDTO post; // 게시글 정보
    private List<CommentResponseDTO> comments; // 댓글 목록

}
