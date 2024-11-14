package site.talent_trade.api.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;

@Repository
public class BaseMemberRepositoryImpl implements BaseMemberRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Member findByMemberId(Long memberId) {
    return Optional.ofNullable(em.createQuery("select m from Member m"
            + " where m.id =:memberId", Member.class)
        .setParameter("memberId", memberId)
        .getSingleResult())
        .orElseThrow(() -> new CustomException(ExceptionStatus.MEMBER_NOT_FOUND));
  }

  @Override
  public Member findByEmail(String email) {
    return Optional.ofNullable(em.createQuery("select m from Member m"
            + " where m.email =:email", Member.class)
        .setParameter("email", email)
        .getSingleResult())
        .orElseThrow(() -> new CustomException(ExceptionStatus.MEMBER_NOT_FOUND));
  }

  @Override
  public boolean existsByEmail(String email) {
    return !em.createQuery("select m from Member m"
            + " where m.email =:email", Member.class)
        .setParameter("email", email)
        .setMaxResults(1)
        .getResultList().isEmpty();
  }

  @Override
  public Member findByNickname(String nickname) {
    return Optional.ofNullable(em.createQuery("select m from Member m"
            + " where m.nickname =:nickname", Member.class)
        .setParameter("nickname", nickname)
        .getSingleResult())
        .orElseThrow(() -> new CustomException(ExceptionStatus.MEMBER_NOT_FOUND));
  }

  @Override
  public boolean existsByNickname(String nickname) {
    return !em.createQuery("select m from Member m"
            + " where m.nickname =:nickname", Member.class)
        .setParameter("nickname", nickname)
        .setMaxResults(1)
        .getResultList().isEmpty();
  }

  @Override
  public Member findMemberWithProfileById(Long memberId) {
    return Optional.ofNullable(em.createQuery("select m from Member m"
            + " left join fetch m.profile"
            + " where m.id =:memberId", Member.class)
        .setParameter("memberId", memberId)
        .getSingleResult())
        .orElseThrow(() -> new CustomException(ExceptionStatus.MEMBER_NOT_FOUND));
  }
}
