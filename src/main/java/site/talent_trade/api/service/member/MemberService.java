package site.talent_trade.api.service.member;

import site.talent_trade.api.dto.auth.request.RefreshTokenDTO;
import site.talent_trade.api.dto.auth.response.TokenPairDTO;
import site.talent_trade.api.dto.member.request.SigninDTO;
import site.talent_trade.api.dto.member.request.SignupDTO;
import site.talent_trade.api.util.response.ResponseDTO;

public interface MemberService {

  /*회원가입*/
  ResponseDTO<TokenPairDTO> signup(SignupDTO request);

  /*로그인*/
  ResponseDTO<TokenPairDTO> signin(SigninDTO request);

  /*닉네임 중복 체크*/
  ResponseDTO<Boolean> checkNickname(String nickname);

  /*이메일 중복 체크*/
  ResponseDTO<Boolean> checkEmail(String email);

  /*토큰 재발급*/
  ResponseDTO<TokenPairDTO> refreshToken(RefreshTokenDTO request);
}
