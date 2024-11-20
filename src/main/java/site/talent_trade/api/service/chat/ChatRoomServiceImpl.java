package site.talent_trade.api.service.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.talent_trade.api.domain.chat.ChatRoom;
import site.talent_trade.api.domain.member.Member;
import site.talent_trade.api.dto.chat.response.ChatRoomCompletedDTO;
import site.talent_trade.api.dto.chat.response.ChatRoomResponseDTO;
import site.talent_trade.api.dto.member.response.MemberResponseDTO;
import site.talent_trade.api.repository.chat.ChatRoomRepository;
import site.talent_trade.api.repository.member.MemberRepository;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;
import site.talent_trade.api.util.response.ResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MemberRepository memberRepository;


    @Override
    @Transactional
    public ResponseDTO<ChatRoomResponseDTO> createChatRoom(Long fromMemberId, Long toMemberId) {

        // 채팅방이 존재하는지 확인
        if (chatRoomRepository.findExistingRoom(fromMemberId, toMemberId)) {
            throw new CustomException(ExceptionStatus.CHAT_ROOM_ALREADY_EXISTS);
        }

        // 해당 아이디를 가진 멤버 객체 찾기
        Member fromMember = memberRepository.findById(fromMemberId).orElseThrow(() -> new IllegalArgumentException("From member not found"));
        Member toMember = memberRepository.findById(toMemberId).orElseThrow(() -> new IllegalArgumentException("To member not found"));

        // 메시지 제한 확인 및 업데이트
        fromMember.updateMessageLimit(); // 메시지 제한을 날짜 기준으로 갱신

        // 메시지 제한이 3개 초과일 경우 예외 처리
        if (fromMember.getMessageLimit() > 3) {
            throw new CustomException(ExceptionStatus.MESSAGE_LIMIT_EXCEEDED); // 커스텀 예외
        }

        // 채팅방 만들기
        ChatRoom chatRoom = new ChatRoom(fromMember, toMember);

        // 메시지 횟수 증가
        fromMember.incrementMessageLimit();
        memberRepository.save(fromMember);

        chatRoomRepository.save(chatRoom);


        // DTO로 변환 후 반환
        ChatRoomResponseDTO chatRoomResponseDTO = ChatRoomResponseDTO.builder()
                .roomId(chatRoom.getId())
                .opponentNickname(toMember.getNickname())
                .talent(toMember.getMyTalent())
                .detailTalent(toMember.getMyTalentDetail())
                .completed(chatRoom.isCompleted())
                .build();

        // ResponseDTO 래핑하여 반환
        return new ResponseDTO<>(chatRoomResponseDTO, HttpStatus.CREATED);
    }

    //내가 참여한 채팅방 조회
    public ResponseDTO<List<ChatRoomResponseDTO>> getChatRoomList(Long memberId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByMemberId(memberId);  // 채팅방 리스트 조회

        List<ChatRoomResponseDTO> chatRoomResponseDTOs = chatRooms.stream()
                .map(chatRoom -> {
                    // 상대방을 결정 (내가 아닌 멤버를 선택)
                    Member opponent = chatRoom.getFromMember().getId().equals(memberId) ? chatRoom.getToMember() : chatRoom.getFromMember();

                    return ChatRoomResponseDTO.builder()
                            .roomId(chatRoom.getId())
                            .opponentNickname(opponent.getNickname())   // 상대방의 닉네임
                            .talent(opponent.getMyTalent())               // 상대방의 재능
                            .detailTalent(opponent.getMyTalentDetail())   // 상대방의 세부 재능
                            .lastMessage(chatRoom.getLastMessage())     // 마지막 메시지
                            .lastMessageAt(chatRoom.getLastMessageAt()) // 마지막 메시지 시간
                            .completed(chatRoom.isCompleted())
                            .build();
                })
                .collect(Collectors.toList());

        return new ResponseDTO<>(chatRoomResponseDTOs, HttpStatus.OK);
    }

    public ResponseDTO<MemberResponseDTO> findMemberById(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->  new CustomException(ExceptionStatus.MEMBER_NOT_FOUND));

        // Member -> MemberResponseDTO 변환
        MemberResponseDTO memberResponseDTO = convertToMemberResponseDTO(member);

        // ResponseDTO 래핑하여 반환
        return new ResponseDTO<>(memberResponseDTO, HttpStatus.OK);
    }

    //아이디로 가져오기
    @Override
    public ResponseDTO<ChatRoomResponseDTO> findById(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.CHAT_ROOM_ALREADY_EXISTS));

        // DTO로 변환 후 반환
        ChatRoomResponseDTO chatRoomResponseDTO = ChatRoomResponseDTO.builder()
                .roomId(chatRoom.getId())
                .opponentNickname(chatRoom.getFromMember().getNickname())  // 예시로 'fromMember' 사용
                .talent(chatRoom.getFromMember().getMyTalent())
                .detailTalent(chatRoom.getFromMember().getMyTalentDetail())
                .completed(chatRoom.isCompleted())
                .build();

        return new ResponseDTO<>(chatRoomResponseDTO, HttpStatus.OK);
    }


    //마지막 메시지 업데이트
    @Override
    @Transactional
    public void updateLastMessage(Long chatRoomId, String lastMessage, LocalDateTime lastMessageAt) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new CustomException(ExceptionStatus.CHAT_ROOM_ALREADY_EXISTS));

        chatRoom.updateLastMessage(lastMessage, lastMessageAt);

        chatRoomRepository.save(chatRoom);

    }
    @Transactional
    @Override
    public ResponseDTO<ChatRoomCompletedDTO> completeChatRoom(Long chatRoomId) {
        // 채팅방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.CHAT_ROOM_NOT_FOUND));

        // 상태 업데이트
        chatRoom.completeChatRoom();

        chatRoomRepository.save(chatRoom);

        // DTO로 변환 후 반환
        ChatRoomCompletedDTO chatRoomCompletedDTO = ChatRoomCompletedDTO.builder()
                .roomId(chatRoom.getId())
                .is_completed(chatRoom.isCompleted())
                .completedAt(chatRoom.getCompletedAt())
                .build();

        // 응답 반환
        return new ResponseDTO<>(chatRoomCompletedDTO, HttpStatus.OK);
    }

    private MemberResponseDTO convertToMemberResponseDTO(Member member) {
        return new MemberResponseDTO(
                member.getId(),
                member.getNickname());
    }


}
