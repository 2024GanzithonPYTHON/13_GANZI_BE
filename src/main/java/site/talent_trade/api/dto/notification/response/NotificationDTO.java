package site.talent_trade.api.dto.notification.response;

import lombok.Data;
import site.talent_trade.api.domain.notification.Notification;
import site.talent_trade.api.domain.notification.NotificationType;

@Data
public class NotificationDTO {

  private Long notificationId;
  private NotificationType type;
  private String fromMemberNickname;
  private String content;
  private Long contentId;
  private String receivedAt;
  private boolean checked;

  public NotificationDTO(Notification notification) {
    this.notificationId = notification.getId();
    this.type = notification.getType();
    this.fromMemberNickname = notification.getFromMember().getNickname();
    this.content = notification.getContent();
    this.contentId = notification.getContentId();
    this.receivedAt = notification.getParsedTime();
    this.checked = notification.isChecked();
  }
}
