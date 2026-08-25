package com.sist.web.controller;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sist.web.vo.ChatMessage;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
/*
 * 	 1. 사용자가 채팅 페이지 접속
 * 	 2. Spring Security가 로그인 사용자 확인
 * 	 
 * 	 3. ThymeLeaf => LOGIN_USER 생성 : 로그인 시 아이디 저장
 * 		<script th:inline="javascript">
        const LOGIN_USER =
            [[${#authentication.name}]] '';
    	</script>
     4. Vue.createApp()
     5. Pinia 등록
     6. useChatStore()
     7. onMounted()
     		store.loginUser = 사용자 아이디 저장
     				LOGIN_USER
     				
     		store.chatBodyEl =
                    chatBody.value
     		
     		store.chatBody.value
     				=> private / public
     8. SockJS 연결
     8-1. STOMP 연결 => this.stomp = Stomp.pver(socket)
     9. 서버 채팅 : destination subscribe
     	채널 => 출력위치 설정
     10. 실시간 메세지 대기
     		|
     	store.msg => Enter => store.send()
     		|
     	STOMP SEND
     		|
     	WebSocket에서 처리
     		|
     	상대방 / 전체 메세지 전송
     		|
     	STOMP => Message 수신
     		|
     	store.message에 추가
     		|
     	Vue 수행 => 화면에 출력
 */
public class ChatController {

	// STOMP => 서버에서 특정한 클라이언트에게 메세지를 전송하는 역할
	// 1:1, 알림 => id를 포함 
    private final SimpMessagingTemplate template;
    // 접속자 저장 공간
    private final Set<String> users=ConcurrentHashMap.newKeySet();

    // => 여러 쓰레드에서 동시에 안전하게 사용 할 수 있게 만든다
    // => 중복을 제거해서 관리 : WebSocket 이용시 사용자 정보
    @MessageMapping("/chat/public")
    // => HttpSession을 포함하면 안된다 (GetMapping)
    // 전체 채팅 => /topic
    @SendTo("/topic/chat") // 규칙을 만들어서 통신하는 방법
    public ChatMessage publicChat(
            ChatMessage msg,
            Principal p) {
    		// HttpSession을 사용 할 수 없다
    		// Spring Security 이용 => principal => Session 형식
        msg.setSender(p.getName());
        // /topic/chat => 모든 접속자에게 전송
        return msg;
    }
    // 1:1
    @MessageMapping("/chat/private")
    public void privateChat(
            ChatMessage msg,
            Principal p) {
    	// 현재 로그인 된 사용자 ID
        String sender = p.getName();
        // 서버에서 보내는 사람을 지정
        msg.setSender(sender);
        // 상대방에게 메세지 전송 
        template.convertAndSendToUser(
                msg.getReceiver(),
                "/queue/chat",
                msg
        );
        // 본인에게 메세지 전송
        template.convertAndSendToUser(
                sender,
                "/queue/chat",
                msg
        );
    }
    // 접속자 목록 전송
    @MessageMapping("/chat/join")
    public void join(Principal p) {
    	String username=p.getName();
    	users.add(username);
    	template.convertAndSend("/topic/users", users);
    }
 // 화면 이동 => RouterController
 	 @GetMapping("/chat/chat")
 	 public String chat_page(Model model) {
 		 model.addAttribute("main_html", "chat/chat");
 	     return "main/main";
 	 }
}
