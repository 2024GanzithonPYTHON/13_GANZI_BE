package site.talent_trade.api.repository.notification;

import java.util.List;
import java.util.Optional;
import site.talent_trade.api.domain.notification.Notification;

public interface BaseNotificationRepository {

  /*알림 단건 조회*/
  Notification findByNotificationId(Long notificationId);

  /*유저 ID로 알림 조회*/
  List<Notification> findAllByMemberId(Long memberId);

  /*확인하지 않은 알림만 조회*/
  List<Notification> findUncheckedNotificationsByMemberId(Long memberId);

  /*메시지에 해당하는 기존 알림이 있는지 조회*/
  Optional<Notification> findByFromMemberIdAndChatRoomId(Long fromMemberId, Long chatRoomId);

  /*나의 해당 채팅방의 알림 조회*/
  Optional<Notification> findByToMemberIdAndChatRoomId(Long toMemberId, Long chatRoomId);

  /*게시글에 해당하는 기존 알림이 있는지 조회*/
  List<Notification> findUncheckedNotificationsByMemberIdAndPostId(Long memberId, Long postId);
}
