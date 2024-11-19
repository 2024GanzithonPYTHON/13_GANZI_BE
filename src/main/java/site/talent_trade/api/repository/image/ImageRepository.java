package site.talent_trade.api.repository.image;

import org.springframework.data.jpa.repository.JpaRepository;
import site.talent_trade.api.domain.image.Image;

public interface ImageRepository extends JpaRepository<Image, Long>, BaseImageRepository {

}
