package site.talent_trade.api.dto.member.request;

import lombok.Data;

/**
 * 회원가입 DTO
 */
@Data
public class SignupDTO {

  private String email;
  private String password;
  private String name;
  private String nickname;
  private String birth;
  private String phone;

  public SignupDTO(String email, String password, String name, String nickname, String birth,
      String phone) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.nickname = nickname;
    this.birth = birth;
    this.phone = phone;
  }
}
