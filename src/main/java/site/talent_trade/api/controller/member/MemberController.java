package site.talent_trade.api.controller.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.talent_trade.api.dto.auth.request.RefreshTokenDTO;
import site.talent_trade.api.dto.auth.response.TokenPairDTO;
import site.talent_trade.api.dto.member.request.SigninDTO;
import site.talent_trade.api.dto.member.request.SignupDTO;
import site.talent_trade.api.service.member.MemberService;
import site.talent_trade.api.util.response.ResponseDTO;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/signup")
  public ResponseDTO<TokenPairDTO> signup(@RequestBody SignupDTO request) {
    return memberService.signup(request);
  }

  @PostMapping("/signin")
  public ResponseDTO<TokenPairDTO> signin(@RequestBody SigninDTO request) {
    return memberService.signin(request);
  }

  @PostMapping("/token/refresh")
  public ResponseDTO<TokenPairDTO> refreshToken(@RequestBody RefreshTokenDTO request) {
    return memberService.refreshToken(request);
  }

  @GetMapping("/email/duplicate")
  public ResponseDTO<Boolean> checkEmail(@RequestParam String email) {
    return memberService.checkEmail(email);
  }

  @GetMapping("/nickname/duplicate")
  public ResponseDTO<Boolean> checkNickname(@RequestParam String nickname) {
    return memberService.checkNickname(nickname);
  }
}
