package site.talent_trade.api.repository.member;

import site.talent_trade.api.domain.member.Member;

public interface BaseMemberRepository {

  Member findByMemberId(Long memberId);

  Member findByEmail(String email);

  boolean existsByEmail(String email);

  Member findByNickname(String nickname);

  boolean existsByNickname(String nickname);

  Member findMemberWithProfileById(Long memberId);
}
