package site.talent_trade.api.service.notification;

import site.talent_trade.api.dto.notification.response.NotificationListDTO;
import site.talent_trade.api.util.response.ResponseDTO;

public interface NotificationService {

  /*나의 알림 조회*/
  public ResponseDTO<NotificationListDTO> getMyNotificationList(Long memberId);

  /*알림 읽음 처리*/
  public ResponseDTO<Void> checkNotification(Long memberId, Long notificationId);

  /*알림 모두 읽음 처리*/
  public ResponseDTO<Void> checkAllNotification(Long memberId);


  public void markNotificationAsReadByContentId(Long contentId, Long memberId);


}
