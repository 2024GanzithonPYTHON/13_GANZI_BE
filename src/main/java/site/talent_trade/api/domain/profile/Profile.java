package site.talent_trade.api.domain.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.talent_trade.api.domain.Timestamp;
import site.talent_trade.api.domain.image.Image;
import site.talent_trade.api.domain.member.Gender;
import site.talent_trade.api.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Profile {

  @Id
  @Column(name = "profile_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "profile")
  private Member member;

  @OneToMany(mappedBy = "profile", orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<Image> images = new ArrayList<>();

  @Lob
  @Size(max = 500)
  @Column(columnDefinition = "TEXT", length = 500)
  private String talentIntro;

  @Lob
  @Size(max = 500)
  @Column(columnDefinition = "TEXT", length = 500)
  private String experienceIntro;

  private String portfolio;

  @Size(max = 70)
  @Column(length = 70)
  private String region;

  private int tradeCnt;
  private int reviewCnt;
  @Column(precision = 2, scale = 1)
  private BigDecimal scoreAvg;
  private int scoreAccum;

  @Enumerated(EnumType.STRING)
  private MeetingType meetingType;

  @Enumerated(EnumType.STRING)
  private Gender preferGender;

  @Embedded
  private Timestamp timestamp;

  /* 생성자 */
  public Profile(Member member) {
    this.member = member;
    this.tradeCnt = 0;
    this.reviewCnt = 0;
    this.scoreAvg = BigDecimal.ZERO;
    this.scoreAccum = 0;
    this.timestamp = new Timestamp();
  }

  public void updateProfile(String talentIntro, String experienceIntro, String portfolio,
      String region, MeetingType meetingType, Gender preferGender) {
    if (talentIntro != null) {
      this.talentIntro = talentIntro;
    }
    if (experienceIntro != null) {
      this.experienceIntro = experienceIntro;
    }
    if (portfolio != null) {
      this.portfolio = portfolio;
    }
    if (region != null) {
      this.region = region;
    }
    if (meetingType != null) {
      this.meetingType = meetingType;
    }
    if (preferGender != null) {
      this.preferGender = preferGender;
    }
  }

  /*리뷰 점수 반영*/
  public void updateScore(int score) {
    this.reviewCnt++;
    this.scoreAccum += score;
    this.scoreAvg = BigDecimal.valueOf(this.scoreAccum)
        .divide(BigDecimal.valueOf(this.reviewCnt), 1, RoundingMode.HALF_UP);
  }

  /*재능 교환 성사 횟수 증가*/
  public void increaseTradeCnt() {
    this.tradeCnt++;
  }
}
