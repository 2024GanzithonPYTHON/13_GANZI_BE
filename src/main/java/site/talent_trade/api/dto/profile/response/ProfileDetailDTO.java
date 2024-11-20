package site.talent_trade.api.dto.profile.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import site.talent_trade.api.domain.image.Image;
import site.talent_trade.api.domain.member.Gender;
import site.talent_trade.api.domain.member.Talent;
import site.talent_trade.api.domain.profile.MeetingType;
import site.talent_trade.api.domain.profile.Profile;

@Data
public class ProfileDetailDTO {

  private String nickname;
  private Gender gender;
  private Gender preferGender;
  private Talent myTalent;
  private String myTalentDetail;
  private Talent wishTalent;
  private String myComment;
  private String talentIntro;
  private List<String> images;
  private String experienceIntro;
  private String portfolio;
  private String region;
  private MeetingType meetingType;
  private int tradeCnt;
  private BigDecimal scoreAvg;

  public ProfileDetailDTO(Profile profile) {
    this.myTalent = profile.getMember().getMyTalent();
    this.myTalentDetail = profile.getMember().getMyTalentDetail();
    this.wishTalent = profile.getMember().getWishTalent();
    this.myComment = profile.getMember().getMyComment();
    this.talentIntro = profile.getTalentIntro();
    this.images = profile.getImages().stream().map(Image::getThumbnailImageUrl).toList();
    this.experienceIntro = profile.getExperienceIntro();
    this.portfolio = profile.getPortfolio();
    this.region = profile.getRegion();
    this.meetingType = profile.getMeetingType();
    this.tradeCnt = profile.getTradeCnt();
    this.scoreAvg = profile.getScoreAvg();
    this.nickname = profile.getMember().getNickname();
  }


}
