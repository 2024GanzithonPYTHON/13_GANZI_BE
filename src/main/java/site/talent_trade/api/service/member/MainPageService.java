package site.talent_trade.api.service.member;

import site.talent_trade.api.domain.community.SortBy;
import site.talent_trade.api.domain.member.Talent;
import site.talent_trade.api.dto.member.response.MemberListDTO;
import site.talent_trade.api.dto.member.response.MemberPageDTO;
import site.talent_trade.api.util.response.ResponseDTO;

public interface MainPageService {

  /*내가 원하는 재능을 가지고 있는 무작위 유저 3명 추천*/
  ResponseDTO<MemberListDTO> recommendMembers(Long memberId);

  /*메인 페이지의 유저 목록 조회*/
  ResponseDTO<MemberPageDTO> getMainPageMembers(int page, Talent talent, SortBy sortBy);

  /*검색어로 회원 검색*/
  ResponseDTO<MemberPageDTO> searchMembers(int page, SortBy sortBy, String query);

}
