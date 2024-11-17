package site.talent_trade.api.domain.chat;


import jakarta.persistence.*;
import lombok.*;
import site.talent_trade.api.domain.member.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor  //Builder 객체에 구성할 모든 필드를 포함하는 생성자 생성
public class Message {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chatroom_id", insertable = false, updatable = false)
    private ChatRoom chatRoom;

    @Column(name = "chatroom_id")
    private Long chatRoomId;

    @ManyToOne
    @JoinColumn(name = "from_member_id")
    private Member fromMember; //로그인 한 사용자

    @ManyToOne
    @JoinColumn(name = "to_member_id")
    private Member toMember; //대화 상대방

    private String content;
    private LocalDateTime createdAt;

}
