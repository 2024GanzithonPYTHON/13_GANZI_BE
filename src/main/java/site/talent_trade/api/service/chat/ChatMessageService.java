package site.talent_trade.api.service.chat;

import site.talent_trade.api.domain.chat.Message;
import site.talent_trade.api.dto.chat.request.MessagePayload;
import site.talent_trade.api.dto.chat.response.MessageResponseDTO;
import site.talent_trade.api.util.response.ResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ChatMessageService {

    // 메시지 저장
    void saveMessage(Message message);

    // 특정 채팅방의 메시지 목록 조회
    ResponseDTO<List<MessageResponseDTO>> getMessagesByChatRoomId(Long roomId);


    //메시지 객체 생성 및 저장
    MessageResponseDTO createAndSaveMessage(MessagePayload messagePayload);
}
