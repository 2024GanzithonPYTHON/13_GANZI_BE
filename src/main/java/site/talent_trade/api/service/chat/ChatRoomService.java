package site.talent_trade.api.service.chat;

import org.springframework.stereotype.Service;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.dto.chat.response.ChatRoomResponseDTO;
import site.talent_trade.api.dto.member.response.MemberResponseDTO;
import site.talent_trade.api.util.response.ResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ChatRoomService {

    //채팅방 생성
    ResponseDTO<ChatRoomResponseDTO> createChatRoom(Long fromMember, Long toMember);

    //내가 참여한 채팅방 가져오기
    ResponseDTO<List<ChatRoomResponseDTO>> getChatRoomList(Long memberId);

    //id로 참여 멤버 가져오기
    ResponseDTO<MemberResponseDTO> findMemberById(Long memberId);

    //채팅방 아이디로 채팅방 가져오기
    ResponseDTO<ChatRoomResponseDTO> findById(Long roomId);

    // 마지막 메시지 업데이트
    public void updateLastMessage(Long chatRoomId, String lastMessage, LocalDateTime lastMessageAt);

    //성사됐는지 안됐는지 판단


}
