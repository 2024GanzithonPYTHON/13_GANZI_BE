package site.talent_trade.api.repository.review;

import java.util.List;
import site.talent_trade.api.domain.review.Review;

public interface BaseReviewRepository {

  /*후기 조회*/
  List<Review> findByMemberId(Long memberId);
}
