package site.talent_trade.api.repository.image;

import java.util.List;
import site.talent_trade.api.domain.image.Image;

public interface BaseImageRepository {

  List<Image> findAllByMemberId(Long memberId);

  List<Image> findAllByProfileId(Long profileId);

  Image findByImageId(Long imageId);
}
