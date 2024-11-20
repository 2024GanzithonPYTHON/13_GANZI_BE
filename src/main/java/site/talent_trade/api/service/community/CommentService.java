package site.talent_trade.api.service.community;

import site.talent_trade.api.dto.commnuity.request.CommentRequestDTO;
import site.talent_trade.api.dto.commnuity.request.PostRequestDTO;
import site.talent_trade.api.dto.commnuity.response.CommentResponseDTO;
import site.talent_trade.api.util.response.ResponseDTO;

public interface CommentService {

    ResponseDTO<CommentResponseDTO> saveComment(CommentRequestDTO commentRequestDTO);
}
