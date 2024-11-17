package site.talent_trade.api.dto.member.request;

import lombok.Data;

/**
 * 로그인 DTO
 */
@Data
public class SigninDTO {

  private String email;
  private String password;

  public SigninDTO(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
