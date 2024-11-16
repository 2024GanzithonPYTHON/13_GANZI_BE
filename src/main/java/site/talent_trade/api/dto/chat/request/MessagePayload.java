package site.talent_trade.api.dto.chat.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MessagePayload {

    private Long chatRoomId;
    private Long fromMemberId;
    private Long toMemberId;
    private String content;
    private LocalDateTime createdAt;

}
