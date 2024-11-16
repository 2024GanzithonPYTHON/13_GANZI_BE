package site.talent_trade.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import site.talent_trade.api.util.chat.FilterChannelInterceptor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //todo
    //jwt 검증 interceptor
    //private final FilterChannelInterceptor filterChannelInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/topic"); //메시지 구독 요청 : 메시지 송신
        registry.setApplicationDestinationPrefixes("/app"); //메시지 발행 요청: 메시지 수신
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        //ws://localhost:8080/chat를 호출하면 websocket 연결
        registry.addEndpoint("/chat") //socket 연결 url
                .setAllowedOriginPatterns("http://localhost:*")//CORS 허용 범위
                .withSockJS(); //낮은 버전 브라우저도 사용할 수 있도록
    }

    //todo
    //추후 jwt 검증
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(filterChannelInterceptor);
//    }


}
