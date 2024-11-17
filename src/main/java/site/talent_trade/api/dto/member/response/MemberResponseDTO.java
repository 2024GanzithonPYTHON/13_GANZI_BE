package site.talent_trade.api.dto.member.response;

import lombok.Data;
import site.talent_trade.api.domain.member.Member;

@Data
public class MemberResponseDTO {

    private Long memberId;
    private String nickname;

    // public 생성자로 변경
    public MemberResponseDTO(Long id, String nickname) {
        this.memberId = id;
        this.nickname = nickname;
    }

}

