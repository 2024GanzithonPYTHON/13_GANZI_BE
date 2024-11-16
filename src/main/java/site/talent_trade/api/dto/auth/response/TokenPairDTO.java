package site.talent_trade.api.dto.auth.response;

import lombok.Data;

/**
 * 액세스, 리프레시 토큰 DTO
 */
@Data
public class TokenPairDTO {

  private String accessToken;
  private String refreshToken;

  public TokenPairDTO(String access, String refresh) {
    this.accessToken = access;
    this.refreshToken = refresh;
  }
}
