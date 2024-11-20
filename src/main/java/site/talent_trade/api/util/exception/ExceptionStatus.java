package site.talent_trade.api.util.exception;

import lombok.Getter;

@Getter
public enum ExceptionStatus {

  /*400 BAD_REQUEST*/
  BAD_REQUEST(400, "Bad Request"),
  INVALID_EMAIL(400, "Invalid email"),
  INVALID_PASSWORD(400, "Invalid password"),
  NICKNAME_TOO_LONG(400, "Nickname too long"),
  NAME_TOO_LONG(400, "Name too long"),
  MESSAGE_LIMIT_EXCEEDED(400,"Message Limit exceeded"),

  /*401 UNAUTHORIZED*/
  UNAUTHORIZED(401, "Unauthorized"),
  BLACKLISTED_TOKEN(401, "Blacklisted token"),
  INVALID_TOKEN(401, "Invalid token"),
  NOT_ACCESS_TOKEN(401, "Not access token"),
  TOKEN_NOT_FOUND(401, "Token not found"),
  EXPIRED_TOKEN(401, "Expired token"),
  PREMATURE_TOKEN(401, "Premature token"),
  WRONG_PASSWORD(401, "Wrong password"),

  /*403 FORBIDDEN*/
  FORBIDDEN(403, "Forbidden"),

  /*404 NOT_FOUND*/
  NOT_FOUND(404, "Not found"),
  MEMBER_NOT_FOUND(404, "Member not exists"),
  PROFILE_NOT_FOUND(404, "Profile not exists"),

  /*409 CONFLICT*/
  CONFLICT(409, "Conflict"),
  CHAT_ROOM_ALREADY_EXISTS(409,"Chat room already exists" ),
  DUPLICATED_EMAIL(409, "Duplicated email"),
  DUPLICATED_NICKNAME(409, "Duplicated nickname");

  private final int code;
  private final String msg;

  ExceptionStatus(int code, String message) {
    this.code = code;
    this.msg = message;
  }
}
