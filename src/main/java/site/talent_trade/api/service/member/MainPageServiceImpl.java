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
    List<Member> members = memberRepository.findRandomMemberByTalent(member.getWishTalent());
    MemberListDTO response = new MemberListDTO(members);
    return new ResponseDTO<>(response, HttpStatus.OK);
  }

  @Override
  public ResponseDTO<MemberPageDTO> getMainPageMembers(int page, Talent talent, SortBy sortBy) {
    Specification<Member> spec;
    if (sortBy != null) {
      spec = switch (sortBy.name()) {
        case "REVIEW" -> Specification.where(MemberSpecification.orderByReviewCnt());
        case "SCORE" -> Specification.where(MemberSpecification.orderByScoreAvg());
        default -> Specification.where(MemberSpecification.orderByCreatedAt());
      };
    } else {
      spec = Specification.where(MemberSpecification.orderByCreatedAt());
    }

    Pageable pageable = PageRequest.of(page, 6);
    Page<Member> pagedMember = memberRepository.findAll(spec, pageable);

    List<Member> members = pagedMember.getContent();
    int nextPage = pagedMember.hasNext() ? page + 1 : 0;
    MemberPageDTO response = new MemberPageDTO(nextPage, members);
    return new ResponseDTO<>(response, HttpStatus.OK);
  }

  @Override
  public ResponseDTO<MemberPageDTO> searchMembers(int page, SortBy sortBy, String query) {
    Specification<Member> spec = Specification.where(MemberSpecification.searchByKeyword(query));

    Specification<Member> orderedSpec;
    if (sortBy != null) {
      orderedSpec = spec.and(switch (sortBy.name()) {
        case "REVIEW" -> MemberSpecification.orderByReviewCnt();
        case "SCORE" -> MemberSpecification.orderByScoreAvg();
        default -> MemberSpecification.orderByCreatedAt();
      });
    } else {
      orderedSpec = spec.and(MemberSpecification.orderByCreatedAt());
    }

    Pageable pageable = PageRequest.of(page, 6);
    Page<Member> pagedMember = memberRepository.findAll(orderedSpec, pageable);

    List<Member> members = pagedMember.getContent();
    int nextPage = pagedMember.hasNext() ? page + 1 : 0;
    MemberPageDTO response = new MemberPageDTO(nextPage, members);
    return new ResponseDTO<>(response, HttpStatus.OK);
  }
}
