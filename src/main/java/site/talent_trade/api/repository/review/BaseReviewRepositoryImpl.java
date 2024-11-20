package site.talent_trade.api.repository.review;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import site.talent_trade.api.domain.review.Review;

@Repository
public class BaseReviewRepositoryImpl implements BaseReviewRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public List<Review> findByMemberId(Long memberId) {
    return em.createQuery("select r from Review r"
            + " left join fetch r.fromMember"
            + " where r.toMember.id =:memberId"
            + " order by r.timestamp.createdAt desc", Review.class)
        .setParameter("memberId", memberId)
        .getResultList();
  }
}
