package site.talent_trade.api.util.exception;

import lombok.Data;

@Data
public class ExceptionDTO {

  private String msg;
  private int code;

  public ExceptionDTO(CustomException e) {
    this.msg = e.getExceptionStatus().getMsg();
    this.code = e.getExceptionStatus().getCode();
  }

}
