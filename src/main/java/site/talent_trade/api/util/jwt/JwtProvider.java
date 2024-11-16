package site.talent_trade.api.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.dto.auth.response.TokenPairDTO;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;

@Component
@RequiredArgsConstructor
public class JwtProvider {


  @Value("${spring.application.name}")
  private String issuer;
  @Value("${service.jwt.access-expiration}")
  private Long accessExpiration;
  @Value("${service.jwt.refresh-expiration}")
  private Long refreshExpiration;
  @Value("${service.jwt.secret-key}")
  private String key;
  private SecretKey secretKey;
  private final Blacklist blacklist;
  private final MemberRepository memberRepository;

  @PostConstruct
  public void init() {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
  }


  /*토큰 발급*/
  public String createToken(Long memberId, TokenType tokenType) {
    Member member = memberRepository.findByMemberId(memberId);
    Date expiration;
    Date now = new Date();

    if (tokenType == TokenType.ACCESS) {
      expiration = new Date(now.getTime() + accessExpiration);
    } else {
      expiration = new Date(now.getTime() + refreshExpiration);
    }

    // 마지막 로그인 시간 업데이트
    member.updateLastLoginAt();

    return Jwts.builder()
        .header().type("JWT").and()
        .id(UUID.randomUUID().toString())
        .issuer(issuer)
        .subject(memberId.toString())
        .claim("type", tokenType.name())
        .issuedAt(now)
        .expiration(expiration)
        .signWith(secretKey, SIG.HS512)
        .compact();
  }


  /*리프레시 토큰 검증 후 새로운 액세스 토큰 발급*/
  public TokenPairDTO refreshToken(String refreshToken) {
    Jws<Claims> claims = Jwts.parser().verifyWith(secretKey).build()
        .parseSignedClaims(refreshToken);

    if (!claims.getPayload().get("type").equals(TokenType.REFRESH.name())) {
      throw new CustomException(ExceptionStatus.INVALID_TOKEN);
    }
    if (claims.getPayload().getIssuedAt().after(new Date())) {
      throw new CustomException(ExceptionStatus.PREMATURE_TOKEN);
    }
    Date expireAt = claims.getPayload().getExpiration();
    if (expireAt.before(new Date())) {
      throw new CustomException(ExceptionStatus.EXPIRED_TOKEN);
    }
    if (blacklist.containsToken(refreshToken)) {
      throw new CustomException(ExceptionStatus.BLACKLISTED_TOKEN);
    }
    Long memberId = Long.parseLong(claims.getPayload().getSubject());
    blacklist.putToken(refreshToken, expireAt.toString());

    return new TokenPairDTO(createToken(memberId, TokenType.ACCESS),
        createToken(memberId, TokenType.REFRESH));
  }


  /*로그아웃*/
  public void logout(String accessToken, String refreshToken) {
    Date accessTokenExpiration = Jwts.parser().verifyWith(secretKey).build()
        .parseSignedClaims(accessToken).getPayload().getExpiration();
    Date refreshTokenExpiration = Jwts.parser().verifyWith(secretKey).build()
        .parseSignedClaims(refreshToken).getPayload().getExpiration();

    blacklist.putToken(accessToken, accessTokenExpiration.toString());
    blacklist.putToken(refreshToken, refreshTokenExpiration.toString());
  }


  /*요청 헤더에서 토큰 추출*/
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    return bearerToken.replace("Bearer ", "");
  }


  /*토큰 유효성 확인 및 유저 ID 추출*/
  public Long validateToken(HttpServletRequest request) {
    String token = this.resolveToken(request);
    Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        .getPayload();
    Date now = new Date();

    if (blacklist.containsToken(token)) {
      throw new CustomException(ExceptionStatus.BLACKLISTED_TOKEN);
    } else if (claims.getExpiration().before(now)) {
      throw new CustomException(ExceptionStatus.EXPIRED_TOKEN);
    } else if (claims.getIssuedAt().after(new Date())) {
      throw new CustomException(ExceptionStatus.PREMATURE_TOKEN);
    } else if (!claims.get("type").equals(TokenType.ACCESS.name())) {
      throw new CustomException(ExceptionStatus.NOT_ACCESS_TOKEN);
    }
    Long memberId = Long.parseLong(claims.getSubject());

    // 마지막 로그인 시간 업데이트
    memberRepository.findByMemberId(memberId).updateLastLoginAt();

    return memberId;
  }
}
