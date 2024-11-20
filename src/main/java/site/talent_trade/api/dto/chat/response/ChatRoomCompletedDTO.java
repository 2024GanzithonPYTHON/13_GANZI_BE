package site.talent_trade.api.dto.chat.response;

import lombok.Builder;
import lombok.Data;
import site.talent_trade.api.domain.member.Talent;

import java.time.LocalDateTime;
@Data
@Builder
public class ChatRoomCompletedDTO {

    private Long roomId;
    private boolean is_completed; //성사됐는지 안됐는지
    private LocalDateTime completedAt; //성사됐는지 안됐는지
}
