package site.talent_trade.api.util;

import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;

@Component
@RequiredArgsConstructor
public class Validator {

  private final Pattern emailPattern =
      Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
  private final Pattern passwordPattern =
      Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$");


  /*이메일 유효성 검사*/
  public void validateEmail(String email) {
    if (!emailPattern.matcher(email).matches()) {
      throw new CustomException(ExceptionStatus.INVALID_EMAIL);
    }
  }

  /*비밀번호 유효성 검사*/
  public void validatePassword(String password) {
    if (!passwordPattern.matcher(password).matches()) {
      throw new CustomException(ExceptionStatus.INVALID_PASSWORD);
    }
  }
}
