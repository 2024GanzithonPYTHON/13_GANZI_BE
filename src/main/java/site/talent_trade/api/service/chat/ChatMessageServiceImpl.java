package site.talent_trade.api.service.chat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.talent_trade.api.domain.chat.ChatRoom;
import site.talent_trade.api.domain.chat.Message;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.domain.notification.Notification;
import site.talent_trade.api.domain.notification.NotificationType;
import site.talent_trade.api.dto.chat.request.MessagePayload;
import site.talent_trade.api.dto.chat.response.MessageResponseDTO;
import site.talent_trade.api.repository.chat.ChatMessageRepository;
import site.talent_trade.api.repository.chat.ChatRoomRepository;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.repository.notification.NotificationRepository;
import site.talent_trade.api.service.notification.NotificationService;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;
import site.talent_trade.api.util.response.ResponseDTO;

@Service
@Transactional(readOnly=true)
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;


    //메시지 보내기 , 가장 마지막 채팅 내역 ChatRoom의 lastMessage,lastMessageAt에 저장
    @Override
    @Transactional
    public void saveMessage(Message  message) {
        chatMessageRepository.save(message);

        // 해당 채팅방의 마지막 메시지와 시간을 업데이트
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(message.getChatRoom().getId());
        ChatRoom chatRoom = chatRoomOptional.orElseThrow(()
            -> new CustomException(ExceptionStatus.CHAT_ROOM_NOT_FOUND));
        chatRoomOptional.ifPresent(cr -> {
            cr.updateLastMessage(message.getContent(), message.getCreatedAt());
        });

        // 알림 최신화 혹은 생성
        Optional<Notification> notificationOptional =
            notificationRepository.findByFromMemberIdAndChatRoomId(
                message.getFromMember().getId(), message.getChatRoom().getId()
            );

        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notification.updateMessageNotification(message);
        } else {
            Notification notification = Notification.builder()
                .fromMember(message.getFromMember())
                .toMember(message.getToMember())
                .type(NotificationType.MESSAGE)
                .content(message.getContent())
                .contentId(chatRoom.getId())
                .build();
            notificationRepository.save(notification);
        }
    }

    //채팅방 상세 내용 조회
    @Transactional
    @Override
    public ResponseDTO<List<MessageResponseDTO>> getMessagesByChatRoomId(Long roomId, Long memberId) {

        List<Message> messages = chatMessageRepository.findByChatRoomId(roomId); // 수정: 여러 메시지를 조회

        // 메시지가 없을 경우 빈 리스트 반환
        if (messages.isEmpty()) {
            return new ResponseDTO<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }

        Optional<Notification> optionalNotification =
            notificationRepository.findByToMemberIdAndChatRoomId(memberId, roomId);
        optionalNotification.ifPresent(Notification::checkNotification);

        // Message 객체를 MessageResponseDTO로 변환
        List<MessageResponseDTO> messageResponseDTOs = messages.stream()
                .map(MessageResponseDTO::fromEntity)
                .collect(Collectors.toList());

      return new ResponseDTO<>(messageResponseDTOs, HttpStatus.OK);
    }

    //메시지 객체 생성 및 저장
    public MessageResponseDTO createAndSaveMessage(MessagePayload messagePayload) {

        Member fromMember = memberRepository.findById(messagePayload.getFromMemberId())
                .orElseThrow(() -> new CustomException(ExceptionStatus.MEMBER_NOT_FOUND));

        Member toMember = memberRepository.findById(messagePayload.getToMemberId())
                .orElseThrow(() -> new CustomException(ExceptionStatus.MEMBER_NOT_FOUND));



        LocalDateTime createdAt = messagePayload.getCreatedAt() != null ? messagePayload.getCreatedAt() : LocalDateTime.now();

        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(messagePayload.getChatRoomId());
        ChatRoom chatRoom = optionalChatRoom.orElseThrow(() -> new CustomException(ExceptionStatus.CHAT_ROOM_NOT_FOUND));


        Message newMessage = Message.builder()
                .chatRoomId(messagePayload.getChatRoomId())
                .fromMember(fromMember)
                .toMember(toMember)
                .content(messagePayload.getContent())
                .createdAt(createdAt)
                .build();

         saveMessage(newMessage);
        return MessageResponseDTO.fromEntity(newMessage);
    }
}
