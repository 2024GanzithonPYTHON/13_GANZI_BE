package site.talent_trade.api.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import site.talent_trade.api.domain.member.Gender;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.domain.profile.Profile;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.repository.profile.ProfileRepository;

public class ProfileRepositoryTest {

  @Mock
  private ProfileRepository profileRepository;
  @Mock
  private MemberRepository memberRepository;

  private Member existingMember;
  private Profile existingProfile;

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

    existingProfile = existingMember.getProfile();
    when(profileRepository.save(any(Profile.class))).thenReturn(existingProfile);
    when(profileRepository.findByProfileId(existingProfile.getId())).thenReturn(existingProfile);
  }

  @Test
  void 프로필ID로_프로필찾기_프로필존재시_프로필반환() {
    Profile profile = profileRepository.findByProfileId(existingProfile.getId());
    assertNotNull(profile);
  }

  @Test
  void 프로필ID로_프로필찾기_프로필존재하지않을시_null반환() {
    when(profileRepository.findByProfileId(any(Long.class))).thenReturn(null);
    Profile profile = profileRepository.findByProfileId(999L);
    assertNull(profile);
  }

  @Test
  void 프로필저장_성공시_프로필반환() {
    Profile profile = profileRepository.save(existingProfile);
    assertNotNull(profile);
    assertEquals(existingProfile, profile);
  }

  @Test
  void 프로필저장_실패시_null반환() {
    when(profileRepository.save(any(Profile.class))).thenReturn(null);
    Profile profile = profileRepository.save(new Profile(existingMember));
    assertNull(profile);
  }
}
