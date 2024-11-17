package site.talent_trade.api.dto.auth.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

/**
 * 리프레시 토큰 DTO
 */
@Data
public class RefreshTokenDTO {

  private String refreshToken;

  @JsonCreator
  public RefreshTokenDTO(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
