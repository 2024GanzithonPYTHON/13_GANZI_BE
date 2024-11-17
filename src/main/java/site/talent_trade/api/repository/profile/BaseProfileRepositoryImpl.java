package site.talent_trade.api.repository.profile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import site.talent_trade.api.domain.profile.Profile;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;

@Repository
public class BaseProfileRepositoryImpl implements BaseProfileRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Profile findByProfileId(Long profileId) {
    try {
      return em.createQuery("select p from Profile p"
              + " where p.id =:profileId", Profile.class)
          .setParameter("profileId", profileId)
          .getSingleResult();
    } catch (NoResultException e) {
      throw new CustomException(ExceptionStatus.PROFILE_NOT_FOUND);
    }
  }

  @Override
  public Profile findByMemberId(Long memberId) {
    try {
      return em.createQuery("select p from Profile p"
              + " where p.member.id =:memberId", Profile.class)
          .setParameter("memberId", memberId)
          .getSingleResult();
    } catch (NoResultException e) {
      throw new CustomException(ExceptionStatus.PROFILE_NOT_FOUND);
    }
  }

  @Override
  public Profile findProfileWithMemberById(Long profileId) {
    try {
      return em.createQuery("select p from Profile p"
              + " left join fetch p.member"
              + " where p.id =:profileId", Profile.class)
          .setParameter("profileId", profileId)
          .getSingleResult();
    } catch (NoResultException e) {
      throw new CustomException(ExceptionStatus.PROFILE_NOT_FOUND);
    }
  }
}
