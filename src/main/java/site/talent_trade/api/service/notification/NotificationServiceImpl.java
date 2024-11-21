package site.talent_trade.api.service.notification;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.talent_trade.api.domain.notification.Notification;
import site.talent_trade.api.dto.notification.response.NotificationListDTO;
import site.talent_trade.api.repository.notification.NotificationRepository;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;
import site.talent_trade.api.util.response.ResponseDTO;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;

  @Override
  public ResponseDTO<NotificationListDTO> getMyNotificationList(Long memberId) {
    List<Notification> notificationList = notificationRepository.findAllByMemberId(memberId);
    NotificationListDTO response = new NotificationListDTO(notificationList);
    return new ResponseDTO<>(response, HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseDTO<Void> checkNotification(Long memberId, Long notificationId) {
    Notification notification = notificationRepository.findByNotificationId(notificationId);

    // 본인 알림이 아닌 경우 예외 발생
    if (!notification.getToMember().getId().equals(memberId)) {
      throw  new CustomException(ExceptionStatus.FORBIDDEN);
    }
    notification.checkNotification();
    return new ResponseDTO<>(null, HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseDTO<Void> checkAllNotification(Long memberId) {
    List<Notification> notifications = notificationRepository.findUncheckedNotificationsByMemberId(
        memberId);
    notifications.forEach(Notification::checkNotification);
    return new ResponseDTO<>(null, HttpStatus.OK);
  }

  @Transactional
  @Override
  public void markNotificationAsReadByContentId(Long contentId, Long memberId) {
    // contentId로 읽지 않은 알림 조회
    Notification notification = notificationRepository.findByContentIdAndCheckedFalse(contentId);

    // 알림 상태를 본인 알림일 때만 읽음으로 변경
    if (!notification.getToMember().getId().equals(memberId)) {
      throw new CustomException(ExceptionStatus.FORBIDDEN);
    }
    notification.checkNotification();
    notificationRepository.save(notification);

  }
}
