package site.talent_trade.api.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import site.talent_trade.api.domain.community.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //댓글 작성

}
