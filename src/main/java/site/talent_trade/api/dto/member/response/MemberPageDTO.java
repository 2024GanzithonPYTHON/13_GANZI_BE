package site.talent_trade.api.dto.member.response;

import java.util.List;
import lombok.Data;
import site.talent_trade.api.domain.member.Member;

@Data
public class MemberPageDTO {

  private boolean hasNext;
  private int currentPage;
  private List<MemberSimpleDTO> members;

  public MemberPageDTO(boolean hasNext, int currentPage, List<Member> members) {
    this.hasNext = hasNext;
    this.currentPage = currentPage;
    this.members = members.stream().map(MemberSimpleDTO::new).toList();
  }
}
