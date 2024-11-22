package site.talent_trade.api.dto.notification.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Data;
import site.talent_trade.api.domain.notification.Notification;

@Data
public class NotificationListDTO {

  List<NotificationDTO> notifications;

  @JsonCreator
  public NotificationListDTO(List<Notification> notifications) {
    this.notifications = notifications.stream().map(NotificationDTO::new).toList();
  }
}
