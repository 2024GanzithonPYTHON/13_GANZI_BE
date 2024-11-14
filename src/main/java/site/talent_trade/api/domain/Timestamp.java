package site.talent_trade.api.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EntityListeners;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * CreatedAt, ModifiedAt을 관리하는 Timestamp 클래스
 */
@Getter
@Embeddable
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Timestamp {

  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime modifiedAt;
}
