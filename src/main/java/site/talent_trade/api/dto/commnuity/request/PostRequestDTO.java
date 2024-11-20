package site.talent_trade.api.dto.commnuity.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
/*
* 글 작성 데이터 전송 DTO
* */

@Data
@Builder
public class PostRequestDTO {

    private  Long writerId;

    private  String title;

    private String content;

    private int hitCount = 0;

}
