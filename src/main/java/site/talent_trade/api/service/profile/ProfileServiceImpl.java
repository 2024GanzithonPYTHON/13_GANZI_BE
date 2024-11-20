package site.talent_trade.api.service.profile;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.talent_trade.api.domain.image.Image;
import site.talent_trade.api.domain.profile.Profile;
import site.talent_trade.api.dto.image.app.ImageUrlPairDTO;
import site.talent_trade.api.dto.profile.request.ProfileEditDTO;
import site.talent_trade.api.dto.profile.response.ProfileDetailDTO;
import site.talent_trade.api.repository.image.ImageRepository;
import site.talent_trade.api.repository.profile.ProfileRepository;
import site.talent_trade.api.util.S3.S3Connector;
import site.talent_trade.api.util.Validator;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;
import site.talent_trade.api.util.response.ResponseDTO;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  private final ProfileRepository profileRepository;
  private final ImageRepository imageRepository;
  private final Validator validator;
  private final S3Connector s3Connector;

  @Override
  @Transactional
  public ResponseDTO<Void> editProfile(Long memberId, ProfileEditDTO request) {
    // 유효성 검사
    validator.validateNickname(request.getNickname());
    validator.validateIntro(request.getTalentIntro(), request.getExperienceIntro());
    validator.validateMyComment(request.getMyComment());
    validator.validateRegion(request.getRegion());

    Profile profile = profileRepository.findProfileWithMemberById(memberId);

    // 수정 결과 이미지 개수가 3개를 초과하는 경우 예외 발생
    int currentImage = profile.getImages().size();
    int imageToDelete = request.getDeletedImages().size();
    int imageToAdd = request.getImages().size();
    if (3 < currentImage - imageToDelete + imageToAdd) {
      throw new CustomException(ExceptionStatus.IMAGE_LIMIT_EXCEEDED);
    }

    // 회원 정보 수정
    profile.getMember().updateMember(request.getNickname(), request.getMyTalent(),
        request.getMyTalentDetail(), request.getWishTalent(), request.getMyComment());

    // 프로필 수정
    profile.updateProfile(request.getTalentIntro(), request.getExperienceIntro(),
        request.getPortfolio(), request.getRegion(), request.getMeetingType(),
        request.getPreferGender());

    // 이미지 삭제
    deleteImages(request.getDeletedImages());

    // 이미지 추가
    uploadImage(profile, request.getImages());

    return new ResponseDTO<>(null, HttpStatus.OK);
  }

  @Override
  public ResponseDTO<ProfileDetailDTO> getProfile(Long memberId) {
    Profile profile = profileRepository.findProfileWithMemberById(memberId);
    return new ResponseDTO<>(new ProfileDetailDTO(profile), HttpStatus.OK);
  }


  /*이미지 추가*/
  private void uploadImage(Profile profile, List<MultipartFile> images) {
    images.forEach(image -> {
       ImageUrlPairDTO imageUrlPair = s3Connector.uploadImage(image);
      Image.builder()
          .profile(profile)
          .originalImageUrl(imageUrlPair.getOriginalImageUrl())
          .thumbnailImageUrl(imageUrlPair.getThumbnailImageUrl());
    });
  }


  /*이미지 삭제*/
  private void deleteImages(List<Long> deletedImages) {
    deletedImages.forEach(imageId -> {
      imageRepository.findByImageId(imageId).deleteImage();
      imageRepository.deleteById(imageId);
    });
  }
}
