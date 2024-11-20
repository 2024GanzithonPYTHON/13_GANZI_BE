package site.talent_trade.api.repository.chat;

import site.talent_trade.api.domain.chat.ChatRoom;

public interface BaseChatRoomRepository {

  /*두 회원간의 채팅방 조회*/
  ChatRoom findChatRoomByTwoMemberIds(Long fromMemberId, Long toMemberId);

}
