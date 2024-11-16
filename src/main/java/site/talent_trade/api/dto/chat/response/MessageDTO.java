package site.talent_trade.api.dto.chat.response;

import lombok.Builder;
import lombok.Getter;
import site.talent_trade.api.domain.chat.Message;

import java.time.LocalDateTime;

@Getter
@Builder
public class MessageDTO {
    private Long messageId;
    private Long fromMemberId;
    private String fromMemberName;
    private String content;
    private LocalDateTime createdAt;

    // 엔티티에서 DTO로 변환하는 메서드
    public static MessageDTO fromEntity(Message message) {
        if (message == null) {
            return null;
        }

        return MessageDTO.builder()
                .messageId(message.getId())
                .fromMemberId(message.getFromMember().getId())  // 메시지 발신자의 ID
                .fromMemberName(message.getFromMember().getName())  // 메시지 발신자의 이름
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
