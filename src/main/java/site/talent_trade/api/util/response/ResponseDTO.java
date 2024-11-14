package site.talent_trade.api.util.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 응답에 사용되는 공용 DTO입니다.
 * code와 msg는 HttpStatus를 통해 설정합니다.
 * @param <T>
 */
@Data
public class ResponseDTO<T> {

  @JsonIgnore
  private HttpStatus status;

  private int code;
  private String msg;
  private T data;

  public ResponseDTO(T data, HttpStatus status) {
    this.status = status;
    this.code = status.value();
    this.msg = status.getReasonPhrase();
    this.data = data;
  }
}
