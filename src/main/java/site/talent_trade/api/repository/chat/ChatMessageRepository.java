package site.talent_trade.api.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.talent_trade.api.domain.chat.Message;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<Message, Long> {

    //최신 메시지 조회
    @Query("SELECT m FROM Message m WHERE m.chatRoom.id = :chatRoomId ORDER BY m.createdAt DESC")
    List<Message> findLatestMessageByChatRoomId(@Param("chatRoomId") Long chatRoomId);

    //전체 메시지 조회
    @Query("SELECT m FROM Message m WHERE m.chatRoom.id = :roomId ORDER BY m.createdAt DESC")
    List<Message> findByChatRoomId(@Param("roomId") Long roomId);
}
