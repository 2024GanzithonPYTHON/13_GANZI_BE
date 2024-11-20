package site.talent_trade.api.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import site.talent_trade.api.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, BaseMemberRepository,
    JpaSpecificationExecutor<Member> {


}
