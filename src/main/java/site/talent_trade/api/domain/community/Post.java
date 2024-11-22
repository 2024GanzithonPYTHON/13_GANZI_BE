package site.talent_trade.api.domain.community;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.talent_trade.api.domain.Timestamp;
import site.talent_trade.api.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@AllArgsConstructor  //Builder 객체에 구성할 모든 필드를 포함하는 생성자 생성
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Size(max = 40) // 제목 최대 40자 제한
    @Column(length = 40, nullable = false)
    private String title;

    @Size(max = 300) // 내용 최대 300자 제한
    @Column(length = 300, nullable = false)
    private String content;

    @Column(nullable = false)
    private int hitCount = 0; // 조회수 기본값 0으로 설정

    @Embedded
    private Timestamp timestamp;

    // 댓글 리스트 추가 (양방향 매핑)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /*post 객체에서 바로 댓글 목록을 가져올 수 있어 조회 성능 향상
      mappedBy="post"를 통해 연관관계의 주인은 Comment */

    //조회수 증가
    public void incrementHitCount() {
        this.hitCount++;
    }
}
