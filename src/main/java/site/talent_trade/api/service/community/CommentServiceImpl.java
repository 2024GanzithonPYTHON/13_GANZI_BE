package site.talent_trade.api.service.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.talent_trade.api.domain.Timestamp;
import site.talent_trade.api.domain.community.Comment;
import site.talent_trade.api.domain.community.Post;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.dto.commnuity.request.CommentRequestDTO;
import site.talent_trade.api.dto.commnuity.request.PostRequestDTO;
import site.talent_trade.api.dto.commnuity.response.CommentResponseDTO;
import site.talent_trade.api.dto.commnuity.response.PostResponseDTO;
import site.talent_trade.api.repository.community.CommentRepository;
import site.talent_trade.api.repository.community.PostRepository;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;
import site.talent_trade.api.util.response.ResponseDTO;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentRepository commentRepository;

    //댓글 작성
    @Override
    public ResponseDTO<CommentResponseDTO> saveComment(CommentRequestDTO commentRequestDTO) {

        Member writer = memberRepository.findById(commentRequestDTO.getWriterId())
                .orElseThrow(() -> new CustomException(ExceptionStatus.MEMBER_NOT_FOUND));

        Post post = postRepository.getReferenceById(commentRequestDTO.getPostId());
        // Timestamp 생성
        Timestamp timestamp = Timestamp.create(); // create() 사용

        Comment newComment = Comment.builder()
                .content(commentRequestDTO.getContent())
                .post(post)
                .timestamp(timestamp)
                .member(writer)
                .build();

        Comment savedComment = commentRepository.save(newComment);

        //dto 반환
        CommentResponseDTO commentResponseDTO = CommentResponseDTO.builder()
                .commentId(savedComment.getId())
                .nickname(writer.getNickname())
                .content(savedComment.getContent())
                .talent(writer.getMyTalent().name())
                .talentDetail(writer.getMyTalentDetail())
                .createdAt(savedComment.getTimestamp().getCreatedAt())
                .gender(savedComment.getMember().getGender().name())
                .build();
        return new ResponseDTO<>(commentResponseDTO, HttpStatus.OK);

    }
}
