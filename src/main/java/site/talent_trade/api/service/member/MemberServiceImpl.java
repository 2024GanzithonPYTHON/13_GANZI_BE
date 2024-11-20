package site.talent_trade.api.service.member;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.dto.auth.request.RefreshTokenDTO;
import site.talent_trade.api.dto.auth.response.TokenPairDTO;
import site.talent_trade.api.dto.member.request.SigninDTO;
import site.talent_trade.api.dto.member.request.SignupDTO;
import site.talent_trade.api.dto.member.response.DuplicationCheckDTO;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.util.Validator;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;
import site.talent_trade.api.util.jwt.JwtProvider;
import site.talent_trade.api.util.jwt.TokenType;
import site.talent_trade.api.util.response.ResponseDTO;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final Validator validator;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  @Override
  @Transactional
  public ResponseDTO<TokenPairDTO> signup(SignupDTO request) {
    // 유효성 검사
    validator.validateEmail(request.getEmail());
    validator.validatePassword(request.getPassword());
    validator.validateNickname(request.getNickname());
    validator.validateName(request.getName());

    // 유저 데이터 생성
    Member member = Member.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .name(request.getName())
        .nickname(request.getNickname())
        .birth(LocalDate.parse(request.getBirth()))
        .phone(request.getPhone())
        .gender(request.getGender())
        .build();
    memberRepository.save(member);

    // 토큰 발급 및 응답
    return new ResponseDTO<>(
        new TokenPairDTO(
            jwtProvider.createToken(member.getId(), TokenType.ACCESS),
            jwtProvider.createToken(member.getId(), TokenType.REFRESH)
        ), HttpStatus.CREATED);
  }

  @Override
  public ResponseDTO<TokenPairDTO> signin(SigninDTO request) {
    // 이메일로 회원 조회
    Member member = memberRepository.findByEmail(request.getEmail());

    // 비밀번호 일치 확인
    if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
      throw new CustomException(ExceptionStatus.WRONG_PASSWORD);
    }

    // 토큰 발급 및 응답
    return new ResponseDTO<>(
        new TokenPairDTO(
            jwtProvider.createToken(member.getId(), TokenType.ACCESS),
            jwtProvider.createToken(member.getId(), TokenType.REFRESH)
        ), HttpStatus.OK);
  }

  @Override
  public ResponseDTO<DuplicationCheckDTO> checkNickname(String nickname) {
    // 닉네임 중복 확인
    // 닉네임이 존재하면 true, 존재하지 않으면 false
    DuplicationCheckDTO response = new DuplicationCheckDTO(
        memberRepository.existsByNickname(nickname)
    );
    return new ResponseDTO<>(response, HttpStatus.OK);
  }

  @Override
  public ResponseDTO<DuplicationCheckDTO> checkEmail(String email) {
    // 이메일 중복 확인
    // 이메일이 존재하면 true, 존재하지 않으면 false
    DuplicationCheckDTO response = new DuplicationCheckDTO(
        memberRepository.existsByEmail(email)
    );
    return new ResponseDTO<>(response, HttpStatus.OK);
  }

  @Override
  public ResponseDTO<TokenPairDTO> refreshToken(RefreshTokenDTO request) {
    // 토큰 재발급
    return new ResponseDTO<>(
        jwtProvider.refreshToken(request.getRefreshToken()
        ), HttpStatus.OK);
  }
}
