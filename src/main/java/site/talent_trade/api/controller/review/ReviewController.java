package site.talent_trade.api.controller.review;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.talent_trade.api.dto.review.request.ReviewRequestDTO;
import site.talent_trade.api.dto.review.response.ReviewListDTO;
import site.talent_trade.api.dto.review.response.ReviewResponseDTO;
import site.talent_trade.api.service.review.ReviewService;
import site.talent_trade.api.util.jwt.JwtProvider;
import site.talent_trade.api.util.response.ResponseDTO;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;
  private final JwtProvider jwtProvider;

  @PostMapping("/write/{toMemberId}")
  public ResponseDTO<ReviewResponseDTO> writeReview(@PathVariable Long toMemberId,
      HttpServletRequest httpServletRequest,
      @RequestBody ReviewRequestDTO request) {
    Long fromMemberId = jwtProvider.validateToken(httpServletRequest);
    return reviewService.writeReview(fromMemberId, toMemberId, request);
  }


  @GetMapping("/get/{memberId}")
  public ResponseDTO<ReviewListDTO> getReview(@PathVariable Long memberId,
      HttpServletRequest httpServletRequest) {
    jwtProvider.validateToken(httpServletRequest);
    return reviewService.getReview(memberId);
  }
}
