package site.talent_trade.api.repository.chat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import site.talent_trade.api.domain.chat.ChatRoom;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;

@Repository
public class BaseChatRoomRepositoryImpl implements BaseChatRoomRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public ChatRoom findChatRoomByTwoMemberIds(Long fromMemberId, Long toMemberId) {
    try {
      return em.createQuery("SELECT c FROM ChatRoom c"
              + " WHERE (c.fromMember.id = :fromMemberId AND c.toMember.id = :toMemberId) "
              + "OR (c.fromMember.id = :toMemberId AND c.toMember.id = :fromMemberId)", ChatRoom.class)
          .setParameter("fromMemberId", fromMemberId)
          .setParameter("toMemberId", toMemberId)
          .getSingleResult();
    } catch (NoResultException e) {
     throw new CustomException(ExceptionStatus.CHAT_ROOM_NOT_FOUND);
    }
  }

}
