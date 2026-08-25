package com.sist.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig
        implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(
            MessageBrokerRegistry registry) {
    	// /topic => 전체 메세지 => /all
    	// /queue => 개인 메세지 => /my
        registry.enableSimpleBroker(
                "/topic",
                "/queue"
        );
        // 클라이언트 => 서버 요청 
        // /app/chat/public  => 전체 채팅
        // /app/chat/private => 1:1 채팅
        // 생략하고 인식
        registry.setApplicationDestinationPrefixes(
                "/app"
        );
        // /user/queue/chat
        registry.setUserDestinationPrefix(
                "/user"
        );
    }
    // webSocket 연결 주소 지정
    @Override
    public void registerStompEndpoints(
            StompEndpointRegistry registry) {

        registry.addEndpoint("/chat-ws")
        		// 모든 사람이 접근이 가능
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
