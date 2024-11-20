package site.talent_trade.api.domain.review;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.talent_trade.api.domain.Timestamp;
import site.talent_trade.api.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor
public class Review {

  @Id @Column(name = "review_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="from_member_id")
  private Member fromMember;

  @ManyToOne
  @JoinColumn(name="to_member_id")
  private Member toMember;

  private int score;
  private String content;

  @Embedded
  private Timestamp timestamp;

  /*생성자*/
  @Builder
  public Review(Member fromMember, Member toMember, int score, String content) {
    this.fromMember = fromMember;
    this.fromMember.getMyReviews().add(this);
    this.toMember = toMember;
    this.score = score;
    this.content = content;
    this.timestamp = new Timestamp();
  }
}
