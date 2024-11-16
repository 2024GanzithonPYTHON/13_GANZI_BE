package site.talent_trade.api.controller.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import site.talent_trade.api.domain.chat.ChatRoom;
import site.talent_trade.api.domain.chat.Message;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.dto.chat.request.MessagePayload;
import site.talent_trade.api.dto.chat.response.MessageDTO;
import site.talent_trade.api.service.chat.ChatMessageService;
import site.talent_trade.api.service.chat.ChatRoomService;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    private final ChatRoomService chatRoomService;

    // 특정 채팅방의 메시지 목록 조회
    @GetMapping("chat/room/{roomId}/messages")
    public ResponseEntity<Optional<Message>> getMessagesByRoomId(@PathVariable Long roomId) {
        Optional<Message> messages = chatMessageService.getMessagesByChatRoomId(roomId);
        log.debug("Messages fetched for roomId {}: {}", roomId, messages);
        return ResponseEntity.ok(messages);
    }

    @MessageMapping("/send/message")
    @SendTo("/topic/chat")
    @Transactional
    public MessageDTO sendMessage(@RequestBody MessagePayload messagePayload) {

        Long chatRoomId = messagePayload.getChatRoomId();

        // 전달된 memberId로 `Member` 객체를 찾는다.
        Member fromMember = chatRoomService.findMemberById(messagePayload.getFromMemberId());
        Member toMember = chatRoomService.findMemberById(messagePayload.getToMemberId());

        LocalDateTime createdAt = messagePayload.getCreatedAt() != null ? messagePayload.getCreatedAt() : LocalDateTime.now();

        // 새로운 메시지를 생성하여 저장
        Message newMessage = Message.builder()
                .chatRoomId(chatRoomId)
                .fromMember(fromMember)
                .toMember(toMember)
                .content(messagePayload.getContent())
                .createdAt(createdAt)
                .build();

        // 메시지를 저장
        chatMessageService.saveMessage(newMessage);

        // 메시지를 MessageDTO로 변환하여 반환
        return MessageDTO.fromEntity(newMessage);
    }
}
