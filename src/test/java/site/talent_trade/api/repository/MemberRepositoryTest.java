package site.talent_trade.api.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import site.talent_trade.api.domain.member.Gender;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;


public class MemberRepositoryTest {

  @Mock
  private MemberRepository memberRepository;

  private Member existingMember;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    existingMember = Member.builder()
        .email("test@example.com")
        .password("password")
        .name("Test")
        .nickname("nickname")
        .phone("1234567890")
        .birth(LocalDate.of(1990, 1, 1))
        .gender(Gender.MALE)
        .build();
    when(memberRepository.save(any(Member.class))).thenReturn(existingMember);
  }

  @Test
  void 회원ID로_회원찾기_회원존재시_회원반환() {
    when(memberRepository.findByMemberId(existingMember.getId())).thenReturn(existingMember);
    Member member = memberRepository.findByMemberId(existingMember.getId());
    assertNotNull(member);
  }

  @Test
  void 회원ID로_회원찾기_회원없을시_예외발생() {
    when(memberRepository.findByMemberId(999L)).thenThrow(
        new CustomException(ExceptionStatus.NOT_FOUND));
    assertThrows(CustomException.class, () -> memberRepository.findByMemberId(999L));
  }

  @Test
  void 이메일로_회원찾기_이메일존재시_회원반환() {
    when(memberRepository.findByEmail(existingMember.getEmail())).thenReturn(existingMember);
    Member member = memberRepository.findByEmail(existingMember.getEmail());
    assertNotNull(member);
  }

  @Test
  void 이메일로_회원찾기_이메일없을시_예외발생() {
    when(memberRepository.findByEmail("nonexistent@example.com")).thenThrow(
        new CustomException(ExceptionStatus.NOT_FOUND));
    assertThrows(CustomException.class,
        () -> memberRepository.findByEmail("nonexistent@example.com"));
  }

  @Test
  void 이메일존재여부_이메일존재시_true반환() {
    when(memberRepository.existsByEmail(existingMember.getEmail())).thenReturn(true);
    boolean exists = memberRepository.existsByEmail(existingMember.getEmail());
    assertTrue(exists);
  }

  @Test
  void 이메일존재여부_이메일없을시_false반환() {
    when(memberRepository.existsByEmail("nonexistent@example.com")).thenReturn(false);
    boolean exists = memberRepository.existsByEmail("nonexistent@example.com");
    assertFalse(exists);
  }

  @Test
  void 닉네임으로_회원찾기_닉네임존재시_회원반환() {
    when(memberRepository.findByNickname(existingMember.getNickname())).thenReturn(existingMember);
    Member member = memberRepository.findByNickname(existingMember.getNickname());
    assertNotNull(member);
  }

  @Test
  void 닉네임으로_회원찾기_닉네임없을시_예외발생() {
    when(memberRepository.findByNickname("nonexistent")).thenThrow(
        new CustomException(ExceptionStatus.NOT_FOUND));
    assertThrows(CustomException.class, () -> memberRepository.findByNickname("nonexistent"));
  }

  @Test
  void 닉네임존재여부_닉네임존재시_true반환() {
    when(memberRepository.existsByNickname(existingMember.getNickname())).thenReturn(true);
    boolean exists = memberRepository.existsByNickname(existingMember.getNickname());
    assertTrue(exists);
  }

  @Test
  void 닉네임존재여부_닉네임없을시_false반환() {
    when(memberRepository.existsByNickname("nonexistent")).thenReturn(false);
    boolean exists = memberRepository.existsByNickname("nonexistent");
    assertFalse(exists);
  }

  @Test
  void 프로필포함회원찾기_회원존재시_회원반환() {
    when(memberRepository.findMemberWithProfileById(existingMember.getId())).thenReturn(
        existingMember);
    Member member = memberRepository.findMemberWithProfileById(existingMember.getId());
    assertNotNull(member);
    assertNotNull(member.getProfile());
  }

  @Test
  void 프로필포함회원찾기_회원없을시_예외발생() {
    when(memberRepository.findMemberWithProfileById(999L)).thenThrow(new CustomException(
        ExceptionStatus.NOT_FOUND));
    assertThrows(CustomException.class, () -> memberRepository.findMemberWithProfileById(999L));
  }
}
