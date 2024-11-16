package site.talent_trade.api.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.talent_trade.api.domain.chat.ChatRoom;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // 두 멤버 간 채팅방이 이미 존재하는지 확인
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ChatRoom c " +
            "WHERE (c.fromMember.id = :fromMemberId AND c.toMember.id = :toMemberId) " +
            "OR (c.fromMember.id = :toMemberId AND c.toMember.id = :fromMemberId)")
    boolean findExistingRoom(@Param("fromMemberId") Long fromMemberId, @Param("toMemberId") Long toMemberId);


    /* 내가 참여한 채팅방 리스트 조회
      로그인한 사용자가 toMember일 수도 있음. -> 상대방이 내게 요청하여 채팅방을 만들어진 경우가 이에 해당
      그래서 둘 다 확인해야함. */

    @Query("SELECT c FROM ChatRoom c WHERE c.fromMember.id = :memberId OR c.toMember.id = :memberId")
    List<ChatRoom> findByMemberId(@Param("memberId") Long memberId);
}

