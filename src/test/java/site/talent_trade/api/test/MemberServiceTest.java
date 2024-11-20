package site.talent_trade.api.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.dto.auth.request.RefreshTokenDTO;
import site.talent_trade.api.dto.auth.response.TokenPairDTO;
import site.talent_trade.api.dto.member.request.SigninDTO;
import site.talent_trade.api.dto.member.request.SignupDTO;
import site.talent_trade.api.dto.member.response.DuplicationCheckDTO;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.service.member.MemberServiceImpl;
import site.talent_trade.api.util.Validator;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;
import site.talent_trade.api.util.jwt.JwtProvider;
import site.talent_trade.api.util.response.ResponseDTO;

public class MemberServiceTest {

  @InjectMocks
  private MemberServiceImpl memberService;

  @Mock
  private MemberRepository memberRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JwtProvider jwtProvider;
  @Mock
  private Validator validator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void 회원가입_성공() {
    SignupDTO signupDTO = new SignupDTO("test@example.com", "password", "name", "nickname", "2000-01-01", "010-1234-5678");
    Member member = Member.builder()
        .email(signupDTO.getEmail())
        .password(signupDTO.getPassword())
        .name(signupDTO.getName())
        .nickname(signupDTO.getNickname())
        .birth(LocalDate.parse(signupDTO.getBirth()))
        .phone(signupDTO.getPhone())
        .build();

    when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
    when(memberRepository.save(any())).thenReturn(member);
    when(jwtProvider.createToken(any(), any())).thenReturn("accessToken", "refreshToken");

    ResponseDTO<TokenPairDTO> response = memberService.signup(signupDTO);

    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getData().getAccessToken()).isEqualTo("accessToken");
    assertThat(response.getData().getRefreshToken()).isEqualTo("refreshToken");
  }

  @Test
  void 로그인_성공() {
    SigninDTO signinDTO = new SigninDTO("test@example.com", "password");
    Member member = Member.builder()
        .email(signinDTO.getEmail())
        .password("encodedPassword")
        .build();

    when(memberRepository.findByEmail(any())).thenReturn(member);
    when(passwordEncoder.matches(any(), any())).thenReturn(true);
    when(jwtProvider.createToken(any(), any())).thenReturn("accessToken", "refreshToken");

    ResponseDTO<TokenPairDTO> response = memberService.signin(signinDTO);

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    assertThat(response.getData().getAccessToken()).isEqualTo("accessToken");
    assertThat(response.getData().getRefreshToken()).isEqualTo("refreshToken");
  }

  @Test
  void 로그인_실패_잘못된_비밀번호() {
    SigninDTO signinDTO = new SigninDTO("test@example.com", "wrongPassword");
    Member member = Member.builder()
        .email(signinDTO.getEmail())
        .password("encodedPassword")
        .build();

    when(memberRepository.findByEmail(any())).thenReturn(member);
    when(passwordEncoder.matches(any(), any())).thenReturn(false);

    CustomException exception = assertThrows(CustomException.class, () -> memberService.signin(signinDTO));

    assertThat(exception.getExceptionStatus()).isEqualTo(ExceptionStatus.WRONG_PASSWORD);
  }

  @Test
  void 닉네임_중복_확인() {
    when(memberRepository.existsByNickname(any())).thenReturn(true);

    ResponseDTO<DuplicationCheckDTO> response = memberService.checkNickname("nickname");

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    assertThat(response.getData()).isEqualTo(new DuplicationCheckDTO(true));
  }

  @Test
  void 이메일_중복_확인() {
    when(memberRepository.existsByEmail(any())).thenReturn(true);

    ResponseDTO<DuplicationCheckDTO> response = memberService.checkEmail("test@example.com");

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    assertThat(response.getData()).isEqualTo(new DuplicationCheckDTO(true));
  }

  @Test
  void 토큰_재발급() {
    RefreshTokenDTO refreshTokenDTO = new RefreshTokenDTO("refreshToken");
    TokenPairDTO tokenPairDTO = new TokenPairDTO("newAccessToken", "newRefreshToken");

    when(jwtProvider.refreshToken(any())).thenReturn(tokenPairDTO);

    ResponseDTO<TokenPairDTO> response = memberService.refreshToken(refreshTokenDTO);

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    assertThat(response.getData().getAccessToken()).isEqualTo("newAccessToken");
    assertThat(response.getData().getRefreshToken()).isEqualTo("newRefreshToken");
  }
}
