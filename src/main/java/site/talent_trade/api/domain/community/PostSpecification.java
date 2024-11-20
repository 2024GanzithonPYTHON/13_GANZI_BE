package site.talent_trade.api.domain.community;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import site.talent_trade.api.domain.member.Member;

public class PostSpecification {

    // 필터링: Talent (재능) 기준
    public static Specification<Post> hasTalent(String talent) {
        return (root, query, criteriaBuilder) -> {
            // talent가 null 또는 "전체"인 경우, 필터링을 하지 않음
            if (talent == null || talent.equals("전체")) {
                return criteriaBuilder.conjunction(); // 조건 없이 모든 Post를 반환
            }
            Join<Post, Member> memberJoin = root.join("member");
            return criteriaBuilder.equal(memberJoin.get("myTalent"), talent);
        };
    }

    // 필터링: Talent Detail (재능 상세) 기준
    public static Specification<Post> hasTalentDetail(String talentDetail) {
        return (root, query, criteriaBuilder) -> {
            if (talentDetail == null) {
                return null;  // null이면 필터링 하지 않음
            }
            Join<Post, Member> memberJoin = root.join("member");
            return criteriaBuilder.equal(memberJoin.get("myTalentDetail"), talentDetail);
        };
    }

    // 필터링: 키워드 포함 여부 (내용 기준)
    public static Specification<Post> containsKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) {
                return null;  // null이거나 빈 문자열이면 필터링 하지 않음
            }
            return criteriaBuilder.like(root.get("title"), "%" + keyword + "%");
        };
    }

    // 최신순 정렬 (timestamp 기준)
    public static Specification<Post> latestFirst() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("timestamp").get("createdAt")));
            return null;
        };
    }

    //조회 순 정렬
    public static Specification<Post> hitCountHighest(){
        return (root, query,criteriaBuilder)->{
            query.orderBy(criteriaBuilder.desc(root.get("hitCount")));
            return criteriaBuilder.conjunction(); // 조건 반환
        };
    }

    //댓글 순 정렬
    public static Specification<Post> commentCountHighest(){
        return(root, query, criteriaBuilder) -> {
            Join<Post,Comment> commentJoin = root.join("comments", JoinType.LEFT);
            query.groupBy(root.get("id"))
                    .orderBy(criteriaBuilder.desc(criteriaBuilder.count(commentJoin)));
            return criteriaBuilder.conjunction(); // 조건 반환
        };
    }


    // 평점 높은 순 정렬 (Profile의 평점 기준)
//    public static Specification<Post> ratingHighest() {
//        return (root, query, criteriaBuilder) -> {
//            Join<Post, Member> memberJoin = root.join("member");
//            Join<Member, Profile> profileJoin = memberJoin.join("profile");
//            query.orderBy(criteriaBuilder.desc(profileJoin.get("scoreAvg")));
//            return null;
//        };
//    }

    // 리뷰 많은 순 정렬 (Profile의 리뷰 개수 기준)
//    public static Specification<Post> reviewCountHighest() {
//        return (root, query, criteriaBuilder) -> {
//            Join<Post, Member> memberJoin = root.join("member");
//            Join<Member, Profile> profileJoin = memberJoin.join("profile");
//            query.orderBy(criteriaBuilder.desc(profileJoin.get("reviewCnt")));
//            return null;
//        };
//    }
}
