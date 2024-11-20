package site.talent_trade.api.service.review;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.domain.review.Review;
import site.talent_trade.api.dto.review.request.ReviewRequestDTO;
import site.talent_trade.api.dto.review.response.ReviewResponseDTO;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.repository.review.ReviewRepository;
import site.talent_trade.api.util.response.ResponseDTO;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final MemberRepository memberRepository;

  @Override
  public ResponseDTO<Void> writeReview(Long fromMemberId, Long toMemberId,
      ReviewRequestDTO request) {
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

    return new ResponseDTO<>(null, HttpStatus.CREATED);
  }

  @Override
  public ResponseDTO<ReviewResponseDTO> getReview(Long memberId) {
    Member member = memberRepository.findMemberWithProfileById(memberId);

    List<Review> reviews = reviewRepository.findByMemberId(memberId);
    ReviewResponseDTO response = new ReviewResponseDTO(
        member.getProfile().getReviewCnt(),
        member.getProfile().getScoreAvg(),
        reviews
    );

    return new ResponseDTO<>(response, HttpStatus.OK);
  }
}
