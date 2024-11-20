package site.talent_trade.api.controller.chat;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.talent_trade.api.domain.chat.ChatRoom;
import site.talent_trade.api.dto.chat.response.ChatRoomCompletedDTO;
import site.talent_trade.api.dto.chat.response.ChatRoomResponseDTO;
import site.talent_trade.api.service.chat.ChatRoomService;
import site.talent_trade.api.util.jwt.JwtProvider;
import site.talent_trade.api.util.response.ResponseDTO;

import java.net.http.HttpRequest;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chatrooms")
public class ChatRoomController {
    //내가 참여한 채팅방 리스트 조회
    //반환해야하는 값 : 상대방의 닉네임, 분야, 세부분야, 마지막 메시지, 마지막 메시지를 보낸 시간

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private JwtProvider jwtProvider;

    //채팅방 생성
    @PostMapping
    public ResponseDTO<ChatRoomResponseDTO> createChatRoom(HttpServletRequest request, @RequestParam Long toMemberId) {
        Long fromMemberId = jwtProvider.validateToken(request);
        return chatRoomService.createChatRoom(fromMemberId, toMemberId);
    }

    // 내가 참여한 채팅방 조회
    @GetMapping
    public ResponseDTO<List<ChatRoomResponseDTO>> getMyChatRooms(HttpServletRequest request) {
        Long memberId = jwtProvider.validateToken(request);
        return chatRoomService.getChatRoomList(memberId);
    }

    //만남 성사하기
    @PatchMapping("/{chatRoomId}/complete")
    public ResponseDTO<ChatRoomCompletedDTO> completeChatRoom(HttpServletRequest request, @PathVariable Long chatRoomId) {
        jwtProvider.validateToken(request);
        return chatRoomService.completeChatRoom(chatRoomId);
    }
}
