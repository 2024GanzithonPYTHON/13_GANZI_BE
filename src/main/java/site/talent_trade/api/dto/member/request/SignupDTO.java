package site.talent_trade.api.dto.member.request;

import lombok.Data;
import site.talent_trade.api.domain.member.Gender;

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
  private Gender gender;

  public SignupDTO(String email, String password, String name, String nickname, String birth,
      String phone, String gender) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.nickname = nickname;
    this.birth = birth;
    this.phone = phone;
    this.gender = Gender.valueOf(gender);
  }
}
