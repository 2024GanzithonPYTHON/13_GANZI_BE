package site.talent_trade.api.dto.chat.response;

import lombok.Builder;
import lombok.Data;
import site.talent_trade.api.domain.member.Talent;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatRoomResponseDTO {

    private Long roomId;
    private String opponentNickname; //상대방 닉네임
    private Talent talent; //재능
    private String detailTalent; //세부재능
    private String lastMessage; //마지막 메시지
    private LocalDateTime lastMessageAt; //마지막 메시지를 받은 시간
    private boolean completed; //성사됐는지 안됐는지

    public ChatRoomResponseDTO(Long roomId, String opponentNickname, Talent talent, String detailTalent, String lastMessage, LocalDateTime lastMessageAt, boolean completed) {
        this.roomId = roomId;
        this.opponentNickname = opponentNickname;
        this.talent = talent;
        this.detailTalent = detailTalent;
        this.lastMessage = lastMessage;
        this.lastMessageAt = lastMessageAt;
        this.completed = completed;
    }


}
