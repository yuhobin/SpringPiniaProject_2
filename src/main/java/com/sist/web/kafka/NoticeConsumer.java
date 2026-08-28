package com.sist.web.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.sist.web.vo.*;
import lombok.RequiredArgsConstructor;
/*
 * 	카프카 구동
 * 	도커 실행 후
 * 	docker run -d --name kafka -p 9092:9092 apache/kafka
 * 	docker ps -a
 * 	docker logs kafka => 에러시에 확인용
 * 	docker stop kafka 
 * 	docker rmi kafka
 */
@Service
@RequiredArgsConstructor
/*
 * 	NoticeProducer => sendNotice()
 * 		|
 * 	NoticeConsumer => consumerNotice()
 * 			Producer
 * 	send() => topic => @KafkaListener (Consumer)
 * 							|
 * 						convertAndSend()
 * 							|
 * 						subscribe() 구독 : 데이터를 받는 곳
 * 							=> STOMP
 * 		0) user => 브라우저에서 요청 전송
 * 			pinia => 대댓글
 * 		1) Controller에서 요청을 받는다
 * 		2)Kafka로 메세지를 보낸다 : Producer
 * 			=> kafkaTemplate.send(TOPIC, notice.getReceiver(), notice ); 
 * 		3) 큐 => 메세지를 저장하는 공간 : notice-topic
 * 		4) 메세지를 받는다 Consumer
 * 			@KafkaListener(
			topics = "notice-topic", // Producer에서 생성한 키와 동일
			groupId = "notice-group" // Consumer Group
			)
		5) 브라우저로 데이터를 전송
		 	template.convertAndSend(dest, notice.getMessage());
		6) 데이터를 받아서 출력
			this.stomp.subscribe('/sub/notice/'+id, msg=>{
					this.showToast(msg.body)
					this.boardCommentListData(this.board_no)
			})
 */
public class NoticeConsumer {
	private final SimpMessagingTemplate template;
	// STOMP 를 이용해서 브라우저에 메세지 전송
	@KafkaListener(
			topics = "notice-topic", // Producer에서 생성한 키와 동일
			groupId = "notice-group" // Consumer Group
	)
	public void consumerNotice(ChatMessage notice) {
		// Kafka에서 메세지가 들어오면 => Spring에서 자동 호출 
		System.out.println("Kafka 알림 수신:"+notice);
		String dest="/sub/notice/"+notice.getReceiver();
		template.convertAndSend(dest, notice.getMessage());
		System.out.println("STOMP 알림 전송 완료:"+dest);
	}
}
