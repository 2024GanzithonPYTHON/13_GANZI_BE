package site.talent_trade.api.dto.profile.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import site.talent_trade.api.domain.member.Gender;
import site.talent_trade.api.domain.member.Talent;
import site.talent_trade.api.domain.profile.MeetingType;

@Data
public class ProfileEditDTO {

  private String nickname;
  private Talent myTalent;
  private String myTalentDetail;
  private Talent wishTalent;
  private String myComment;
  private String talentIntro;
  private List<Long> deletedImages;
  private List<MultipartFile> images;
  private String experienceIntro;
  private String portfolio;
  private String region;
  private MeetingType meetingType;
  private Gender preferGender;

  public ProfileEditDTO(String nickname, String myTalent, String myTalentDetail, String wishTalent,
      String myComment, String talentIntro, List<Long> deletedImages, List<MultipartFile> images,
      String experienceIntro, String portfolio, String region, String meetingType,
      String preferGender) {
    this.nickname = nickname;
    this.myTalent = Talent.valueOf(myTalent);
    this.myTalentDetail = myTalentDetail;
    this.wishTalent = Talent.valueOf(wishTalent);
    this.myComment = myComment;
    this.talentIntro = talentIntro;
    this.experienceIntro = experienceIntro;
    this.portfolio = portfolio;
    this.region = region;
    this.meetingType = MeetingType.valueOf(meetingType);
    this.preferGender = Gender.valueOf(preferGender);

    this.deletedImages = Objects.requireNonNullElseGet(deletedImages, ArrayList::new);
    this.images = Objects.requireNonNullElseGet(images, ArrayList::new);
  }
}
