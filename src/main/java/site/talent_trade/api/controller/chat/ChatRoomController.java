package site.talent_trade.api.controller.chat;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.talent_trade.api.domain.chat.ChatRoom;
import site.talent_trade.api.dto.chat.response.ChatRoomResponseDTO;
import site.talent_trade.api.service.chat.ChatRoomService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    //내가 참여한 채팅방 리스트 조회
    //반환해야하는 값 : 상대방의 닉네임, 분야, 세부분야, 마지막 메시지, 마지막 메시지를 보낸 시간

    @Autowired
    private ChatRoomService chatRoomService;

    //채팅방 생성
    @PostMapping("/createChatRoom")
    public ChatRoom createChatRoom(Long fromMemberId, Long toMemberId) {
        return chatRoomService.createChatRoom(fromMemberId, toMemberId);
    }

    // 내가 참여한 채팅방 조회
    @GetMapping("/getChatRoomList")
    public List<ChatRoomResponseDTO> getMyChatRooms(@RequestParam Long memberId) {
        return chatRoomService.getChatRoomList(memberId);
    }


}
