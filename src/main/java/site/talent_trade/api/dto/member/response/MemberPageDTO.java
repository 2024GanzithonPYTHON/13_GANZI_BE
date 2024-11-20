package site.talent_trade.api.dto.member.response;

import java.util.List;
import lombok.Data;
import site.talent_trade.api.domain.member.Member;

@Data
public class MemberPageDTO {

  private int nextPage;
  private List<MemberSimpleDTO> members;

  public MemberPageDTO(int nextPage, List<Member> members) {
    this.nextPage = nextPage;
    this.members = members.stream().map(MemberSimpleDTO::new).toList();
  }
}
