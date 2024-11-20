package site.talent_trade.api.service.review;

import site.talent_trade.api.dto.review.request.ReviewRequestDTO;
import site.talent_trade.api.dto.review.response.ReviewListDTO;
import site.talent_trade.api.dto.review.response.ReviewResponseDTO;
import site.talent_trade.api.util.response.ResponseDTO;

public interface ReviewService {

  /*리뷰 작성*/
  public ResponseDTO<ReviewResponseDTO> writeReview(Long fromMemberId, Long toMemberId,
      ReviewRequestDTO request);

  /*리뷰 조회*/
  public ResponseDTO<ReviewListDTO> getReview(Long memberId);
}
