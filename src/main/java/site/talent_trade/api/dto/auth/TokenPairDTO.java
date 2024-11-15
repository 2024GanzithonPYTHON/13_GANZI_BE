package site.talent_trade.api.dto.auth;

import lombok.Data;

@Data
public class TokenPairDTO {

  private String accessToken;
  private String refreshToken;

  public TokenPairDTO(String access, String refresh) {
    this.accessToken = access;
    this.refreshToken = refresh;
  }
}
