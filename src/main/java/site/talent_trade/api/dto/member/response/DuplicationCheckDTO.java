package site.talent_trade.api.dto.member.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

@Data
public class DuplicationCheckDTO {

  private boolean duplicated;

  @JsonCreator
  public DuplicationCheckDTO(boolean duplicated) {
    this.duplicated = duplicated;
  }

}
