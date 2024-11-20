package site.talent_trade.api.util.S3;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import site.talent_trade.api.dto.image.app.ImageUrlPairDTO;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;
import software.amazon.awssdk.services.s3.S3Client;

@Component
@RequiredArgsConstructor
public class S3Connector {

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;
  private static final long MAX_FILE_SIZE = 3 * 1024 * 1024; // 원본 리사이징 기준 3MB
  private static final String SUFFIX = ".jpg";
  private final S3Client s3Client;
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


  /*이미지 업로드*/
  public ImageUrlPairDTO uploadImage(MultipartFile image) throws IOException {
    if (image.isEmpty()) {
      throw new CustomException(ExceptionStatus.FILE_IS_EMPTY);
    }

    String imageName = generateImageName();

    File originalImage;

    // 썸네일 생성
    String thumbnailImageName = imageName + "_thumbnail";
    File resizedImage = resizeImage(image, thumbnailImageName, true);

    // 이미지가 3MB를 초과하면 리사이징
    if (image.getSize() > MAX_FILE_SIZE) {
      originalImage = resizeImage(image, imageName, false);
    } else {
      originalImage = File.createTempFile(imageName, SUFFIX);
      image.transferTo(originalImage);
    }

    String originalImagePath =  "images/originals/" + imageName + SUFFIX;
    String thumbnailImagePath = "images/thumbnails/" + thumbnailImageName + SUFFIX;

    s3Client.putObject(builder -> builder.bucket(bucket).key(originalImagePath), originalImage.toPath());
    s3Client.putObject(builder -> builder.bucket(bucket).key(thumbnailImagePath), resizedImage.toPath());

    String originalImageUrl = s3Client.utilities().getUrl(builder -> builder.bucket(bucket).key(originalImagePath)).toString();
    String thumbnailImageUrl = s3Client.utilities().getUrl(builder -> builder.bucket(bucket).key(thumbnailImagePath)).toString();

    return new ImageUrlPairDTO(originalImageUrl, thumbnailImageUrl);
  }


  private String generateImageName() {
    LocalDateTime now = LocalDateTime.now();
    String nowString = now.format(formatter);

    String uuid = UUID.randomUUID().toString();
    return nowString + "_" + uuid;
  }


  private File resizeImage(MultipartFile image, String imageName, boolean isThumbnail) throws IOException {
    File tempImage = File.createTempFile(imageName, SUFFIX);

    int imageSize = isThumbnail ? 100 : 2000;

    Thumbnails.of(image.getInputStream())
        .size(imageSize, imageSize)
        .keepAspectRatio(true) // 동일한 비율 유지
        .toFile(tempImage);
    return tempImage;
  }
}
