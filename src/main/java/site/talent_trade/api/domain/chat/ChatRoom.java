package site.talent_trade.api.domain.chat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.talent_trade.api.domain.Timestamp;
import site.talent_trade.api.domain.member.Member;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom {

    @Id
    @Column(name = "chatroom_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_member_id", nullable = false)
    private Member fromMember;  //로그인 한 사용자

    @ManyToOne
    @JoinColumn(name = "to_member_id", nullable = false)
    private Member toMember;  //대화 상대방

    @Column(nullable = true)  // null 허용
    private String lastMessage;  //채팅방 마지막 메시지

    @Column(nullable = true)  // null 허용
    private LocalDateTime lastMessageAt; //마지막 시간

    private boolean isCompleted; //연결이 됐나?
    private LocalDateTime completedAt; //연결된 시간
    private boolean fromMemberRead;  //보낸자가 읽었는가 -> 이건 알림 때문에 만든 필드
    private boolean toMemberRead;  //받은자가 읽었는가 -> 추후 알림 구현할 떄 추가


    @Embedded
    private Timestamp timestamp;


    public ChatRoom(Member fromMember, Member toMember) {
        this.fromMember = fromMember;
        this.toMember = toMember;
        this.timestamp = new Timestamp();
    }

    // 명시적 업데이트 메서드
    public void updateLastMessage(String lastMessage, LocalDateTime lastMessageAt) {
        this.lastMessage = lastMessage;
        this.lastMessageAt = lastMessageAt;
    }

    public void completeChatRoom() {
        this.isCompleted = true;  // 채팅방 상태를 true로 설정
        this.completedAt = LocalDateTime.now();  // 현재 시간 저장
    }
}
