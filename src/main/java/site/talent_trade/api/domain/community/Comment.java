package site.talent_trade.api.domain.community;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import site.talent_trade.api.domain.Timestamp;
import site.talent_trade.api.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@AllArgsConstructor  //Builder 객체에 구성할 모든 필드를 포함하는 생성자 생성
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    //Comment 에서 관련 Post를 명시적으로 호출할 때만 DB에서 조회
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정(필요할 때만 로드 -> 성능 향상)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Size(max = 170) // 내용 최대 170자 제한
    @Column(length = 170, nullable = false)
    private String content;

    @Embedded
    Timestamp timestamp;


}
