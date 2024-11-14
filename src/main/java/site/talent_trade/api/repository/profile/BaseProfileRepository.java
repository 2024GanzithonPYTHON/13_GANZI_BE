package site.talent_trade.api.repository.profile;

import site.talent_trade.api.domain.profile.Profile;

public interface BaseProfileRepository {

  Profile findByProfileId(Long profileId);

  Profile findByMemberId(Long memberId);

  Profile findProfileWithMemberById(Long profileId);
}
