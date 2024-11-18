package site.talent_trade.api.dto.image.app;

import lombok.Data;

@Data
public class ImageUrlPairDTO {

  private String originalImageUrl;
  private String thumbnailImageUrl;

  public ImageUrlPairDTO(String originalImageUrl, String thumbnailImageUrl) {
    this.originalImageUrl = originalImageUrl;
    this.thumbnailImageUrl = thumbnailImageUrl;
  }
}
