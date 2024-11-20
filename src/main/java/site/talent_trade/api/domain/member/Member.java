package site.talent_trade.api.domain.member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.talent_trade.api.domain.Timestamp;
import site.talent_trade.api.domain.profile.Profile;

@Entity @Getter
@NoArgsConstructor
public class Member {

  @Id @Column(name = "member_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "profile_id")
  private Profile profile;

  private String email;
  private String password;
  @Size(max = 10) @Column(length = 10)
  private String name;
  @Size(max = 10) @Column(length = 10)
  private String nickname;
  private String phone;
  private LocalDate birth;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Size(max = 70) @Column(length = 70)
  private String myComment;

  @Enumerated(EnumType.STRING)
  private Talent myTalent;
  private String myTalentDetail;

  @Enumerated(EnumType.STRING)
  private Talent wishTalent;

  private int messageLimit;
  private LocalDateTime lastLoginAt;

  @Embedded
  private Timestamp timestamp;

  /*생성자*/
  @Builder
  public Member(String email, String password, String name, String nickname, String phone,
      LocalDate birth, Gender gender)
  {
    this.email = email;
    this.password = password;
    this.name = name;
    this.nickname = nickname;
    this.phone = phone;
    this.birth = birth;
    this.gender = gender;

    this.messageLimit = 0;
    LocalDateTime now = LocalDateTime.now();
    this.lastLoginAt = now;
    this.timestamp = new Timestamp(now);
    this.profile = new Profile(this);
  }

  /* 닉네임, 재능, 한 줄 소개 수정 메소드 */
  public void updateMember(String nickname, Talent myTalent, String myTalentDetail, Talent wishTalent,
      String myComment) {
    if (nickname != null) this.nickname = nickname;
    if (myTalent != null) this.myTalent = myTalent;
    if (myTalentDetail != null) this.myTalentDetail = myTalentDetail;
    if (wishTalent != null) this.wishTalent = wishTalent;
    if (myComment != null) this.myComment = myComment;
  }

  /* 마지막 활동 시각 기록 메소드 */
  public void updateLastLoginAt() {
    this.lastLoginAt = LocalDateTime.now();
  }

  // Todo: 메시지 횟수 수정 기능 추가
}
