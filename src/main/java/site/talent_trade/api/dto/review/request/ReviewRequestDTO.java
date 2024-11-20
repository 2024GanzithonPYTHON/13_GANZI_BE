package site.talent_trade.api.dto.review.request;

import lombok.Data;

@Data
public class ReviewRequestDTO {

  private String content;
  private int score;

  public ReviewRequestDTO(String content, int score) {
    this.content = content;
    this.score = score;
  }
}
