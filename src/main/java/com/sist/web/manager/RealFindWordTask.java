package com.sist.web.manager;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sist.web.vo.RealFindVO;

import java.util.*;
@Component
// 	Task
// 	단점 => 브라우저로 전송이 안된다 => 데이터베이스만 파일 변경
// 	데이터베이스 백업 
// 	소규모에서 쓰임 => 대규모에선 Spring Batch 사용 
public class RealFindWordTask {
	private static int index=1;
	@Async
	@Scheduled(fixedRate = 60*1*1000)
	public void task() {
		List<RealFindVO> list=DataCollection.dataCollect();
		for(RealFindVO vo:list) {
			System.out.println("============"+index+"===========");
			System.out.println("Rank:"+vo.getRank());
			System.out.println("Word:"+vo.getWord());
			System.out.println("================================");
		}
	} 
}
