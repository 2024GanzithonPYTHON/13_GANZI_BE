package site.talent_trade.api.repository.notification;

import java.util.List;
import site.talent_trade.api.domain.notification.Notification;

public interface BaseNotificationRepository {

  /*알림 단건 조회*/
  Notification findByNotificationId(Long notificationId);

  /*유저 ID로 알림 조회*/
  List<Notification> findAllByMemberId(Long memberId);
}
