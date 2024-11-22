package site.talent_trade.api.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import site.talent_trade.api.domain.review.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, BaseReviewRepository{

}
