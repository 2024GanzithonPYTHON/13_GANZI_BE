package site.talent_trade.api.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import site.talent_trade.api.domain.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>, BaseNotificationRepository{

}
