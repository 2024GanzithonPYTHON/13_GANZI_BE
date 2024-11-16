package site.talent_trade.api.service.chat;

import site.talent_trade.api.domain.chat.Message;

import java.util.Optional;

public interface ChatMessageService {

    // 메시지 저장
    void saveMessage(Message message);

    // 특정 채팅방의 메시지 목록 조회
    Optional<Message> getMessagesByChatRoomId(Long roomId);

}
