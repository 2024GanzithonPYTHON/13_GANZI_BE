package site.talent_trade.api.domain.notification;

import static java.time.Duration.between;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.talent_trade.api.domain.Timestamp;
import site.talent_trade.api.domain.chat.Message;
import site.talent_trade.api.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Notification {

  @Id
  @Column(name = "notification_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "from_member_id")
  private Member fromMember;

  @ManyToOne
  @JoinColumn(name = "to_member_id")
  private Member toMember;

  @Enumerated(EnumType.STRING)
  private NotificationType type;

  @Size(max = 13)
  @Column(length = 13)
  private String content;
  private Long contentId;

  private boolean checked;

  @Embedded
  private Timestamp timestamp;

  /*생성자*/
  @Builder
  public Notification(Member fromMember, Member toMember, NotificationType type, String content,
      Long contentId) {
    this.fromMember = fromMember;
    this.toMember = toMember;
    toMember.getNotifications().add(this);
    this.type = type;
    this.content = truncateContent(content);
    this.contentId = contentId;
    this.checked = false;
    this.timestamp = new Timestamp();
  }

  private String truncateContent(String content) {
    if (content == null) {
      return "";
    }
    if (10 < content.length()) {
      return content.substring(0, 9).trim() + "...";
    }
    return content;
  }

  public void checkNotification() {
    this.checked = true;
  }

  /*유저에게 출력할 시간 문자열*/
  public String getParsedTime() {
    LocalDateTime createdAt = timestamp.getCreatedAt();
    LocalDateTime now = LocalDateTime.now();
    if (createdAt.toLocalDate().isEqual(now.toLocalDate())) {
      return createdAt.format(DateTimeFormatter.ofPattern("a h:mm"));
    } else {
      long daysBetween = between(createdAt, now).toDays();
      return daysBetween + "일 전";
    }
  }

  /*메시지 알림인 경우 데이터 최신화*/
  public void updateMessageNotification(Message message) {
    this.content = truncateContent(message.getContent());
    this.contentId = message.getId();
    this.timestamp = new Timestamp();
    this.checked = false;
  }
}
