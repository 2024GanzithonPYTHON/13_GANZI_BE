package site.talent_trade.api.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.domain.member.Talent;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;

@Repository
public class BaseMemberRepositoryImpl implements BaseMemberRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Member findByMemberId(Long memberId) {
    try {
      return em.createQuery("select m from Member m"
              + " where m.id =:memberId", Member.class)
          .setParameter("memberId", memberId)
          .getSingleResult();
    } catch (NoResultException e) {
      throw new CustomException(ExceptionStatus.MEMBER_NOT_FOUND);
    }
  }

  @Override
  public Member findByEmail(String email) {
    try {
      return em.createQuery("select m from Member m"
              + " where m.email =:email", Member.class)
          .setParameter("email", email)
          .getSingleResult();
    } catch (NoResultException e) {
      throw new CustomException(ExceptionStatus.MEMBER_NOT_FOUND);
    }
  }

  @Override
  public boolean existsByEmail(String email) {
    return !em.createQuery("select 1 from Member m"
            + " where m.email =:email", Integer.class)
        .setParameter("email", email)
        .setMaxResults(1)
        .getResultList().isEmpty();
  }

  @Override
  public Member findByNickname(String nickname) {
    try {
      return em.createQuery("select m from Member m"
              + " where m.nickname =:nickname", Member.class)
          .setParameter("nickname", nickname)
          .getSingleResult();
    } catch (NoResultException e) {
      throw new CustomException(ExceptionStatus.MEMBER_NOT_FOUND);
    }
  }

  @Override
  public boolean existsByNickname(String nickname) {
    return !em.createQuery("select 1 from Member m"
            + " where m.nickname =:nickname", Integer.class)
        .setParameter("nickname", nickname)
        .setMaxResults(1)
        .getResultList().isEmpty();
  }

  @Override
  public Member findMemberWithProfileById(Long memberId) {
    try {
      return em.createQuery("select m from Member m"
              + " left join fetch m.profile"
              + " where m.id =:memberId", Member.class)
          .setParameter("memberId", memberId)
          .getSingleResult();
    } catch (NoResultException e) {
      throw new CustomException(ExceptionStatus.MEMBER_NOT_FOUND);
    }
  }

  @Override
  public List<Member> findRandomMemberByTalent(Talent talent) {
    return em.createQuery("select m from Member m"
            + " where m.myTalent =:talent"
            + " order by function('RAND')", Member.class)
        .setParameter("talent", talent.name())
        .setMaxResults(3)
        .getResultList();
  }
}
