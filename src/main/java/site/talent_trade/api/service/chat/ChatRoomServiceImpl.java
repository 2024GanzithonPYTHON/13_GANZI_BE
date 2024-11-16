package site.talent_trade.api.service.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.talent_trade.api.domain.chat.ChatRoom;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.dto.chat.response.ChatRoomResponseDTO;
import site.talent_trade.api.repository.chat.ChatRoomRepository;
import site.talent_trade.api.repository.member.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public ChatRoom createChatRoom(Long fromMemberId, Long toMemberId) {

        // 채팅방이 존재하는지 확인
        if (chatRoomRepository.findExistingRoom(fromMemberId, toMemberId)) {
            throw new IllegalStateException("이미 채팅방이 존재합니다.");
        }

        // 해당 아이디를 가진 멤버 객체 찾기
        Member fromMember = memberRepository.findById(fromMemberId).orElseThrow(() -> new IllegalArgumentException("From member not found"));
        Member toMember = memberRepository.findById(toMemberId).orElseThrow(() -> new IllegalArgumentException("To member not found"));

        // 채팅방 만들기
        ChatRoom chatRoom = new ChatRoom(fromMember, toMember);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    //내가 참여한 채팅방 조회
    public List<ChatRoomResponseDTO> getChatRoomList(Long memberId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByMemberId(memberId);  // 채팅방 리스트 조회
        return chatRooms.stream()
                .map(chatRoom -> {
                    // 상대방을 결정 (내가 아닌 멤버를 선택)
                    Member opponent = chatRoom.getFromMember().getId().equals(memberId) ? chatRoom.getToMember() : chatRoom.getFromMember();

                    return ChatRoomResponseDTO.builder()
                            .opponentNickname(opponent.getNickname())   // 상대방의 닉네임
                            .talent(opponent.getMyTalent())               // 상대방의 재능
                            .detailTalent(opponent.getMyTalentDetail())   // 상대방의 세부 재능
                            .lastMessage(chatRoom.getLastMessage())     // 마지막 메시지
                            .lastMessageAt(chatRoom.getLastMessageAt()) // 마지막 메시지 시간
                            .build();
                })
                .collect(Collectors.toList());
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    //아이디로 가져오기
    @Override
    public ChatRoom findById(Long roomId) {
        return null;
    }

    //마지막 메시지 업데이트
    @Override
    public void updateLastMessage(Long chatRoomId, String lastMessage, LocalDateTime lastMessageAt) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new IllegalArgumentException("ChatRoom not found with id: " + chatRoomId));

        chatRoom.updateLastMessage(lastMessage, lastMessageAt);

        chatRoomRepository.save(chatRoom);

    }
}
