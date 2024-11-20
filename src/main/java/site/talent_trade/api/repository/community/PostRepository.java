package site.talent_trade.api.repository.community;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.talent_trade.api.domain.community.Post;
import site.talent_trade.api.domain.member.Talent;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    //멤버 아이디로 내가 쓴 게시물 조회하기
    List<Post> findByMemberId(Long memberId);

}