package site.talent_trade.api.controller.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import site.talent_trade.api.dto.chat.request.MessagePayload;
import site.talent_trade.api.dto.chat.response.MessageResponseDTO;
import site.talent_trade.api.service.chat.ChatMessageService;
import site.talent_trade.api.util.response.ResponseDTO;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/chatrooms")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;


    // 특정 채팅방의 메시지 목록 조회
    @GetMapping("/{roomId}/messages")
    public ResponseDTO<List<MessageResponseDTO>> getMessagesByRoomId(@PathVariable Long roomId) {
        return chatMessageService.getMessagesByChatRoomId(roomId);
    }

    @MessageMapping("/chatrooms/{roomId}/send")
    @SendTo("/topic/chatrooms/{roomId}")
    @Transactional
    public ResponseDTO<MessageResponseDTO> sendMessage(@RequestBody MessagePayload messagePayload) {
        MessageResponseDTO messageResponseDTO = chatMessageService.createAndSaveMessage(messagePayload);
        return new ResponseDTO<>(messageResponseDTO, HttpStatus.OK);
    }
}