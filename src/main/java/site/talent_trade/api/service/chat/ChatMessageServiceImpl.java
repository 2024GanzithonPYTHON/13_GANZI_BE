package site.talent_trade.api.service.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.talent_trade.api.domain.chat.Message;
import site.talent_trade.api.repository.chat.ChatMessageRepository;
import site.talent_trade.api.repository.member.MemberRepository;

import java.util.Optional;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private MemberRepository memberRepository;


    //메시지 보내기 , 가장 마지막 채팅 내역 ChatRoom의 lastMessage,lastMessageAt에 저장
    @Override
    public void saveMessage(Message message) {
        chatMessageRepository.save(message);
    }

    //채팅방 상세 내용 조회
    @Override
    public Optional<Message> getMessagesByChatRoomId(Long roomId) {
        return chatMessageRepository.findById(roomId);
    }
}
