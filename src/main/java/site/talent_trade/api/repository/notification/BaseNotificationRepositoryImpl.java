package site.talent_trade.api.repository.notification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import site.talent_trade.api.domain.notification.Notification;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;

@Repository
public class BaseNotificationRepositoryImpl implements BaseNotificationRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Notification findByNotificationId(Long notificationId) {
    try {
      return em.createQuery("select n from Notification n"
              + " where n.id = :notificationId", Notification.class)
          .setParameter("notificationId", notificationId)
          .getSingleResult();
    } catch (NoResultException e) {
      throw new CustomException(ExceptionStatus.NOTIFICATION_NOT_FOUND);
    }
  }

  @Override
  public List<Notification> findAllByMemberId(Long memberId) {
    return em.createQuery("select n from Notification n"
            + " left join fetch n.toMember tm"
            + " left join fetch n.fromMember fm"
            + " where tm.id = :memberId"
            + " order by n.timestamp.createdAt desc", Notification.class)
        .setParameter("memberId", memberId)
        .getResultList();
  }
}
