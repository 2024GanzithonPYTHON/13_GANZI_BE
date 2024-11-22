package site.talent_trade.api.controller.community;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.talent_trade.api.dto.commnuity.request.CommentRequestDTO;
import site.talent_trade.api.dto.commnuity.request.PostRequestDTO;
import site.talent_trade.api.dto.commnuity.response.CommentResponseDTO;
import site.talent_trade.api.dto.commnuity.response.PostResponseDTO;
import site.talent_trade.api.service.community.CommentService;
import site.talent_trade.api.util.jwt.JwtProvider;
import site.talent_trade.api.util.response.ResponseDTO;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    private final JwtProvider jwtProvider;

    //댓글 작성
    @PostMapping("/create")
    public ResponseDTO<CommentResponseDTO> createComment(@RequestBody CommentRequestDTO commentRequestDTO, HttpServletRequest request) {
        // JWT 토큰을 통해 사용자 인증
        Long memberId = jwtProvider.validateToken(request);

        commentRequestDTO.setWriterId(memberId);  // 토큰에서 추출한 memberId로 작성자 설정

        return commentService.saveComment(commentRequestDTO);
    }
}
