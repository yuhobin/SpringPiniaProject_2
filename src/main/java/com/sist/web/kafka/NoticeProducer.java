package com.sist.web.kafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sist.web.vo.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeProducer {
	// NoticeProducer : Kafka Producer 역할 수행 (메세지를 생성)
	private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
	// Kafka로 데이터를 보내는 객체
	private static final String TOPIC="notice-topic";
	// /sub/provate
	// Controller에서 호출
	public void sendNotice(ChatMessage notice) {
	// ChatMessage객체 전송
		kafkaTemplate.send(TOPIC, notice.getReceiver(), notice ); // 메세지 까지 포함
		/*
		 * 	 Vue
		 * 	  |
		 * 	RestController
		 * 	  |	ChatMessage
		 *  Kafka => Producer
		 * 	  |
		 * 	notice-topic
		 * 	  |
		 * 	Consumer = STOMP = Vue
		 *   
		 */
		System.out.println("Kafka 알림 전송:"+notice);
	}
}
