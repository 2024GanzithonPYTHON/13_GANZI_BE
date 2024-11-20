package site.talent_trade.api.dto.review.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import site.talent_trade.api.domain.review.Review;

@Data
public class ReviewResponseDTO {

  private int reviewCnt;
  private BigDecimal scoreAvg;
  private List<ReviewList> reviews;

  public ReviewResponseDTO(int reviewCnt, BigDecimal scoreAvg, List<Review> reviews) {
    this.reviewCnt = reviewCnt;
    this.scoreAvg = scoreAvg;
    this.reviews = reviews.stream().map(ReviewList::new).toList();
  }
}
