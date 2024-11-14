package site.talent_trade.api.util.exception;

import lombok.Getter;

@Getter
public enum ExceptionStatus {

  /*400 BAD_REQUEST*/
  BAD_REQUEST(400, "Bad Request"),

  /*401 UNAUTHORIZED*/
  UNAUTHORIZED(401, "Unauthorized"),
  BLACKLISTED_TOKEN(401, "Blacklisted token"),
  INVALID_TOKEN(401, "Invalid token"),
  NOT_ACCESS_TOKEN(401, "Not access token"),
  TOKEN_NOT_FOUND(401, "Token not found"),
  EXPIRED_TOKEN(401, "Expired token"),
  PREMATURE_TOKEN(401, "Premature token"),

  /*403 FORBIDDEN*/
  FORBIDDEN(403, "Forbidden"),

  /*404 NOT_FOUND*/
  NOT_FOUND(404, "Not found"),

  /*409 CONFLICT*/
  CONFLICT(409, "Conflict");

  private final int code;
  private final String msg;

  ExceptionStatus(int code, String message) {
    this.code = code;
    this.msg = message;
  }
}
