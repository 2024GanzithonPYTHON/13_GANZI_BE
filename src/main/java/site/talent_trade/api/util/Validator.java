package site.talent_trade.api.util;

import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;

@Component
@RequiredArgsConstructor
public class Validator {

  private final Pattern emailPattern =
      Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
  private final Pattern passwordPattern =
      Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$");
  private final MemberRepository memberRepository;


  /*이메일 유효성 검사*/
  public void validateEmail(String email) {
    if (!emailPattern.matcher(email).matches()) {
      throw new CustomException(ExceptionStatus.INVALID_EMAIL);
    }
    if (memberRepository.existsByEmail(email)) {
      throw new CustomException(ExceptionStatus.DUPLICATED_EMAIL);
    }
  }

  /*비밀번호 유효성 검사*/
  public void validatePassword(String password) {
    if (!passwordPattern.matcher(password).matches()) {
      throw new CustomException(ExceptionStatus.INVALID_PASSWORD);
    }
  }


  /*닉네임 유효성 검사*/
  public void validateNickname(String nickname) {
    if (10 < nickname.length()) {
      throw new CustomException(ExceptionStatus.NICKNAME_TOO_LONG);
    }
    if (memberRepository.existsByNickname(nickname)) {
      throw new CustomException(ExceptionStatus.DUPLICATED_NICKNAME);
    }
  }


  /*이름 유효성 검사*/
  public void validateName(String name) {
    if (10 < name.length()) {
      throw new CustomException(ExceptionStatus.NAME_TOO_LONG);
    }
  }

  /*한줄 소개 유효성 검사*/
  public void validateMyComment(String myComment) {
    if (70 < myComment.length()) {
      throw new CustomException(ExceptionStatus.MY_COMMENT_TOO_LONG);
    }
  }

  /*재능, 경험 소개 유효성 검사*/
  public void validateIntro(String intro) {
    if (500 < intro.length()) {
      throw new CustomException(ExceptionStatus.MY_COMMENT_TOO_LONG);
    }
  }

  /*선호 지역 유효성 검사*/
  public void validateRegion(String region) {
    if (70 < region.length()) {
      throw new CustomException(ExceptionStatus.REGION_TOO_LONG);
    }
  }
}
