package site.talent_trade.api.dto.chat.response;

import lombok.Builder;
import lombok.Data;
import site.talent_trade.api.domain.chat.Message;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageResponseDTO {
    private Long chatRoomId;
    private Long fromMemberId;
    private String fromMemberName;
    private String content;
    private LocalDateTime createdAt;


    // Message 엔티티를 MessageResponseDTO로 변환하는 메소드 (빌더 패턴 사용)
    public static MessageResponseDTO fromEntity(Message message) {
        return MessageResponseDTO.builder()
                .fromMemberId(message.getFromMember().getId())
                .fromMemberName(message.getFromMember().getName())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }

}
