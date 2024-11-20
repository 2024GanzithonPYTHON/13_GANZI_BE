package site.talent_trade.api.domain.image;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.talent_trade.api.domain.Timestamp;
import site.talent_trade.api.domain.profile.Profile;

@Entity
@Getter
@NoArgsConstructor
public class Image {

  @Id
  @Column(name = "image_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "profile_id")
  private Profile profile;

  private String originalImageUrl;
  private String thumbnailImageUrl;

  @Embedded
  private Timestamp timestamp;

  /* 생성자 */
  @Builder
  public Image(Profile profile, String originalImageUrl, String thumbnailImageUrl) {
    this.profile = profile;
    this.profile.getImages().add(this);

    this.originalImageUrl = originalImageUrl;
    this.thumbnailImageUrl = thumbnailImageUrl;
    this.timestamp = new Timestamp();
  }

  /*이미지 삭제*/
  public void deleteImage() {
    this.profile.getImages().remove(this);
  }
}
