package site.talent_trade.api.dto.member.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Data;
import site.talent_trade.api.domain.member.Member;

@Data
public class MemberListDTO {

  private List<MemberSimpleDTO> members;

  @JsonCreator
  public MemberListDTO(List<Member> members) {
    this.members = members.stream().map(MemberSimpleDTO::new).toList();
  }
}
