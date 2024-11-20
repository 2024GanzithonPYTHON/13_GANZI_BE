package site.talent_trade.api.dto.review.response;

import java.time.LocalDateTime;
import lombok.Data;
import site.talent_trade.api.domain.member.Gender;
import site.talent_trade.api.domain.member.Talent;
import site.talent_trade.api.domain.review.Review;

@Data
public class ReviewList {

  private Long memberId;
  private String nickname;
  private String content;
  private Gender gender;
  private Talent talent;
  private LocalDateTime createdAt;

  public ReviewList(Review review) {
    this.memberId = review.getFromMember().getId();
    this.nickname = review.getFromMember().getNickname();
    this.content = review.getContent();
    this.gender = review.getFromMember().getGender();
    this.talent = review.getFromMember().getMyTalent();
    this.createdAt = review.getTimestamp().getCreatedAt();
  }
}
