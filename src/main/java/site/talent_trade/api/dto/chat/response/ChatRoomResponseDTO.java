package site.talent_trade.api.dto.chat.response;

import lombok.Builder;
import lombok.Getter;
import site.talent_trade.api.domain.member.Talent;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomResponseDTO {

    private String opponentNickname; //상대방 닉네임
    private Talent talent; //재능
    private String detailTalent; //세부재능
    private String lastMessage; //마지막 메시지
    private LocalDateTime lastMessageAt; //마지막 메시지를 받은 시간

    public ChatRoomResponseDTO(String opponentNickname, Talent talent, String detailTalent, String lastMessage, LocalDateTime lastMessageAt) {
        this.opponentNickname = opponentNickname;
        this.talent = talent;
        this.detailTalent = detailTalent;
        this.lastMessage = lastMessage;
        this.lastMessageAt = lastMessageAt;
    }


}
