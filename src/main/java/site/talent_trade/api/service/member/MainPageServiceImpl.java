package site.talent_trade.api.service.member;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.talent_trade.api.domain.community.SortBy;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.domain.member.MemberSpecification;
import site.talent_trade.api.domain.member.Talent;
import site.talent_trade.api.dto.member.response.MemberListDTO;
import site.talent_trade.api.dto.member.response.MemberPageDTO;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.util.response.ResponseDTO;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {

  private final MemberRepository memberRepository;

  @Override
  public ResponseDTO<MemberListDTO> recommendMembers(Long memberId) {
    Member member = memberRepository.findByMemberId(memberId);
    List<Member> members = memberRepository.findRandomMemberByTalent(member.getId(),
        member.getWishTalent());
    MemberListDTO response = new MemberListDTO(members);
    return new ResponseDTO<>(response, HttpStatus.OK);
  }

  @Override
  public ResponseDTO<MemberPageDTO> getMainPageMembers(Long memberId, int page, Talent talent,
      SortBy sortBy) {
    Specification<Member> spec = Specification.where(MemberSpecification.excludeMember(memberId));
    spec = spec.and(MemberSpecification.hasTalent(talent));

    Specification<Member> sortedSpec = addSortQuery(spec, sortBy);
    MemberPageDTO response = wrapPage(page, sortedSpec);
    return new ResponseDTO<>(response, HttpStatus.OK);
  }

  @Override
  public ResponseDTO<MemberPageDTO> searchMembers(Long memberId, int page, SortBy sortBy,
      String query) {
    Specification<Member> spec = Specification.where(MemberSpecification.excludeMember(memberId));
    spec = spec.and(MemberSpecification.searchByKeyword(query));

    Specification<Member> sortedSpec = addSortQuery(spec, sortBy);
    MemberPageDTO response = wrapPage(page, sortedSpec);
    return new ResponseDTO<>(response, HttpStatus.OK);
  }

  /*6명씩 페이지네이션하여 래핑*/
  private MemberPageDTO wrapPage(int page, Specification<Member> spec) {
    Pageable pageable = PageRequest.of(page, 6);
    Page<Member> pagedMember = memberRepository.findAll(spec, pageable);

    List<Member> members = pagedMember.getContent();
    boolean hasNext = pagedMember.hasNext();
    return new MemberPageDTO(hasNext, page, members);
  }

  /*Specification 객체에 정렬 쿼리 추가*/
  private Specification<Member> addSortQuery(Specification<Member> spec, SortBy sortBy) {
    if (sortBy != null) {
      spec = spec.and(switch (sortBy) {
        case REVIEW -> MemberSpecification.orderByReviewCnt();
        case SCORE -> MemberSpecification.orderByScoreAvg();
        default -> MemberSpecification.orderByCreatedAt();
      });
    } else {
      spec = spec.and(MemberSpecification.orderByCreatedAt());
    }
    return spec;
  }
}
