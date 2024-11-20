package site.talent_trade.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import site.talent_trade.api.domain.image.Image;
import site.talent_trade.api.domain.member.Gender;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.domain.profile.Profile;
import site.talent_trade.api.repository.image.ImageRepository;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.repository.profile.ProfileRepository;

public class ImageRepositoryTest {

  @Mock
  private ImageRepository imageRepository;
  @Mock
  private ProfileRepository profileRepository;
  @Mock
  private MemberRepository memberRepository;

  private Member existingMember;
  private Profile existingProfile;
  private Image existingImage;

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

    existingImage = Image.builder()
        .profile(existingProfile)
        .originalImageUrl("originalImageUrl")
        .thumbnailImageUrl("thumbnailImageUrl")
        .build();
    when(imageRepository.save(any(Image.class))).thenReturn(existingImage);
    when(imageRepository.findByImageId(existingImage.getId())).thenReturn(existingImage);
  }

  @Test
  void 이미지ID로_이미지찾기_이미지존재할시_이미지반환() {
    Image foundImage = imageRepository.findByImageId(existingImage.getId());
    assertNotNull(foundImage);
    assertEquals(existingImage.getId(), foundImage.getId());
  }

  @Test
  void 이미지ID로_이미지찾기_이미지존재하지않을시_empty반환() {
    when(imageRepository.findById(999L)).thenReturn(Optional.empty());
    Optional<Image> foundImage = imageRepository.findById(999L);
    assertFalse(foundImage.isPresent());
  }

  @Test
  void 이미지저장_성공시_이미지반환() {
    Image savedImage = imageRepository.save(existingImage);
    assertNotNull(savedImage);
    assertEquals(existingImage, savedImage);
  }

  @Test
  void 이미지삭제_성공시_이미지존재하지않음() {
    imageRepository.deleteById(existingImage.getId());
    when(imageRepository.findByImageId(existingImage.getId())).thenReturn(null);
    Image foundImage = imageRepository.findByImageId(existingImage.getId());
    assertNull(foundImage);
  }
}
