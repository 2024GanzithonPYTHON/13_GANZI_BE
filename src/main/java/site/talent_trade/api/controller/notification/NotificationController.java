package site.talent_trade.api.controller.notification;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.talent_trade.api.dto.notification.response.NotificationListDTO;
import site.talent_trade.api.service.notification.NotificationService;
import site.talent_trade.api.util.jwt.JwtProvider;
import site.talent_trade.api.util.response.ResponseDTO;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;
  private final JwtProvider jwtProvider;

  @GetMapping("/get")
  public ResponseDTO<NotificationListDTO> getMyNotificationList(HttpServletRequest request) {
    Long memberId = jwtProvider.validateToken(request);
    return notificationService.getMyNotificationList(memberId);
  }

  @PutMapping("/check")
  public ResponseDTO<Void> checkNotification(HttpServletRequest request,
      @RequestParam(name = "id") Long notificationId) {
    Long memberId = jwtProvider.validateToken(request);
    return notificationService.checkNotification(memberId, notificationId);
  }

  @PutMapping("/check/all")
  public ResponseDTO<Void> checkAllNotification(HttpServletRequest request) {
    Long memberId = jwtProvider.validateToken(request);
    return notificationService.checkAllNotification(memberId);
  }
}
