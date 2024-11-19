package site.talent_trade.api.repository.image;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import site.talent_trade.api.domain.image.Image;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;

@Repository
public class BaseImageRepositoryImpl implements BaseImageRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public List<Image> findAllByMemberId(Long memberId) {
    return em.createQuery("select i from Image i"
        + " join i.profile p"
        + " where p.member.id =:memberId", Image.class)
        .getResultList();
  }

  @Override
  public List<Image> findAllByProfileId(Long profileId) {
    return em.createQuery("select i from Image i"
        + " where i.profile.id =:profileId", Image.class)
        .getResultList();
  }

  @Override
  public Image findByImageId(Long imageId) {
    try {
      return em.createQuery("select i from Image i"
          + " where i.id =:imageId", Image.class)
          .setParameter("imageId", imageId)
          .getSingleResult();
    } catch (NoResultException e) {
      throw new CustomException(ExceptionStatus.IMAGE_NOT_FOUND);
    }
  }
}
