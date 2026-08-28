package com.sist.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
/*
 * 	실행
 * 	-----
 * 	Vue => 댓글 작성
 * 	 |
 * 	BoardCommentRestController
 * 	 |
 * 	DB 댓글 저장
 * 	--------------------
 * 	 | 전송 (알림)
 * 	알림을 생성
 * 	NoticeProducer
 * 	 | 	  --------- Kafka에서 메세지를 보는 것
 * 	kafka => notice-topic
 *   |
 *  NoticeConsumer
 *   |	  --------- Kafka에서 메세지를 읽는다 
 *  SimpleMessageTemplate
 *   |
 *  STOMP
 *   |
 *  Vue => boardStore.js
 *   |
 *  showToast
 * 	
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
	private String sender; // 보낸 사람
	private String receiver; // 받는 사람
	private String message; // 채팅 메세지
}
