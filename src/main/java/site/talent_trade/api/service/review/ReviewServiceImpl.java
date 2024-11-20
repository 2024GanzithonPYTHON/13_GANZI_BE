package site.talent_trade.api.service.review;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.talent_trade.api.domain.chat.ChatRoom;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.domain.review.Review;
import site.talent_trade.api.dto.review.request.ReviewRequestDTO;
import site.talent_trade.api.dto.review.response.ReviewListDTO;
import site.talent_trade.api.dto.review.response.ReviewResponseDTO;
import site.talent_trade.api.repository.chat.ChatRoomRepository;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.repository.review.ReviewRepository;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;
import site.talent_trade.api.util.response.ResponseDTO;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final MemberRepository memberRepository;
  private final ChatRoomRepository chatRoomRepository;

  @Override
  @Transactional
  public ResponseDTO<ReviewResponseDTO> writeReview(Long fromMemberId, Long toMemberId,
      ReviewRequestDTO request) {
    if (fromMemberId.equals(toMemberId)) {
      throw new CustomException(ExceptionStatus.CAN_NOT_WRITE_REIVEW_TO_MYSELF);
    }

    ChatRoom chatRoom = chatRoomRepository.findChatRoomByTwoMemberIds(fromMemberId, toMemberId);
    if (!chatRoom.isCompleted()) {
      throw new CustomException(ExceptionStatus.TRADE_NOT_COMPLETED);
    }

    // 유저 조회
    Member fromMember = memberRepository.findByMemberId(fromMemberId);
    Member toMember = memberRepository.findMemberWithProfileById(toMemberId);

    // 리뷰 데이터 생성
    Review review = Review.builder()
        .fromMember(fromMember)
        .toMember(toMember)
        .content(request.getContent())
        .score(request.getScore())
        .build();
    reviewRepository.save(review);

    ReviewResponseDTO response = new ReviewResponseDTO(review);
    return new ResponseDTO<>(response, HttpStatus.CREATED);
  }

  @Override
  public ResponseDTO<ReviewListDTO> getReview(Long memberId) {
    Member member = memberRepository.findMemberWithProfileById(memberId);

    List<Review> reviews = reviewRepository.findByMemberId(memberId);
    ReviewListDTO response = new ReviewListDTO(
        member.getProfile().getReviewCnt(),
        member.getProfile().getScoreAvg(),
        reviews
    );

    return new ResponseDTO<>(response, HttpStatus.OK);
  }
}
