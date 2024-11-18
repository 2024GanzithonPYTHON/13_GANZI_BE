package site.talent_trade.api.util.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import site.talent_trade.api.util.exception.CustomException;
import site.talent_trade.api.util.exception.ExceptionStatus;
import site.talent_trade.api.util.jwt.JwtProvider;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class FilterChannelInterceptor implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {

        //STOMP의 헤더에 직접 접근
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        log.info(">>>>>> headerAccessor : {}", headerAccessor);
        assert headerAccessor != null;
        log.info(">>>>> headAccessorHeaders : {}", headerAccessor.getCommand());

        // CONNECT 또는 SEND 명령에 대해서만 처리
        if (Objects.equals(headerAccessor.getCommand(), StompCommand.CONNECT)
                || Objects.equals(headerAccessor.getCommand(), StompCommand.SEND)) {

            // Authorization 헤더에서 토큰 추출
            String token = removeBrackets(String.valueOf(headerAccessor.getNativeHeader("Authorization")));
            log.info(">>>>>> Token received : {}", token);
            try {
                // 토큰을 사용하여 사용자 ID 추출
                Long memberId = jwtProvider.getMemberIdFromToken(token);

                // WebSocket 헤더에 AccountId를 추가하여, 후속 메시지에서 사용할 수 있도록 함
                headerAccessor.addNativeHeader("AccountId", String.valueOf(memberId));
                log.info(">>>>>> AccountId added to header : {}", memberId);

            } catch (Exception e) {
                log.warn(">>>>> Authentication Failed in FilterChannelInterceptor : ", e);
                // 인증 실패 시 예외 처리 (예: 401 Unauthorized 응답 처리)
                throw new CustomException(ExceptionStatus.INVALID_TOKEN);
            }
        }
        return message;
    }

    // Authorization 헤더에서 대괄호([])를 제거하는 메서드
    public String removeBrackets(String token) {
        // Trim and log the token before processing
        token = token.trim();
        log.info("Token before processing: {}", token);  // 디버깅 로그 추가


        if (token.startsWith("[") && token.endsWith("]")) {
            token = token.substring(1, token.length() - 1);
            log.info("Token after removing brackets: {}", token);  // 대괄호 제거 후 토큰 로그
        }

        // Remove "Bearer " prefix if it exists
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " (7 characters) 제거
            log.info("Token after removing Bearer: {}", token);  // Bearer 제거 후 토큰 로그
        }

        return token;
    }



}