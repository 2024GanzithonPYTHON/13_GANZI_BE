package site.talent_trade.api.controller.profile;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.talent_trade.api.dto.profile.request.ProfileEditDTO;
import site.talent_trade.api.dto.profile.response.ProfileDetailDTO;
import site.talent_trade.api.service.profile.ProfileService;
import site.talent_trade.api.util.jwt.JwtProvider;
import site.talent_trade.api.util.response.ResponseDTO;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileService profileService;
  private final JwtProvider jwtProvider;

  @PutMapping("/edit")
  public ResponseDTO<Void> editProfile(HttpServletRequest httpServletRequest,
      ProfileEditDTO request)
  {
    Long memberId = jwtProvider.validateToken(httpServletRequest);
    return profileService.editProfile(memberId, request);
  }


  @GetMapping("/detail/{memberId}")
  public ResponseDTO<ProfileDetailDTO> getProfile(HttpServletRequest httpServletRequest,
      @PathVariable Long memberId)
  {
    jwtProvider.validateToken(httpServletRequest);
    return profileService.getProfile(memberId);
  }


  @GetMapping("/mine")
  public ResponseDTO<ProfileDetailDTO> getMyProfile(HttpServletRequest httpServletRequest)
  {
    Long memberId = jwtProvider.validateToken(httpServletRequest);
    return profileService.getProfile(memberId);
  }
}
