package site.talent_trade.api.service.profile;

import site.talent_trade.api.dto.profile.request.ProfileEditDTO;
import site.talent_trade.api.dto.profile.response.ProfileDetailDTO;
import site.talent_trade.api.util.response.ResponseDTO;

public interface ProfileService {

  /*프로필 수정*/
  ResponseDTO<Void> editProfile(Long memberId, ProfileEditDTO request);

  /*회원 프로필 조회(내 프로필, 다른 회원의 프로필 공통 사용)*/
  ResponseDTO<ProfileDetailDTO> getProfile(Long memberId);
}
