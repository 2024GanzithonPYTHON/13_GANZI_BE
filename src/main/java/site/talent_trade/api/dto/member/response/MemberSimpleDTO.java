package site.talent_trade.api.dto.member.response;

import lombok.Data;
import site.talent_trade.api.domain.member.Gender;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.domain.member.Talent;

@Data
public class MemberSimpleDTO {

  private Long memberId;
  private String nickname;
  private Gender gender;
  private Talent talent;
  private String comment;

  public MemberSimpleDTO(Member member) {
    this.memberId = member.getId();
    this.nickname = member.getNickname();
    this.gender = member.getGender();
    this.talent = member.getMyTalent();
    this.comment = member.getMyComment();
  }
}
