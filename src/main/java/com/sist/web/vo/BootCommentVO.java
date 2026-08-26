package com.sist.web.vo;
/*
 *  NO         NOT NULL NUMBER       
	BOARD_NO            NUMBER       
	ID                  VARCHAR2(20) 
	NAME       NOT NULL VARCHAR2(51) 
	MSG        NOT NULL CLOB         
	REGDATE             DATE         
	GROUP_ID            NUMBER       
	GROUP_STEP          NUMBER       
	GROUP_TAB           NUMBER       
	ROOT                NUMBER       
	DEPTH               NUMBER     
	
	=> Front : 변경이 없다
	=> Back : 변경
				=> Controller
				=> Kafka => Controller
 */
import java.util.*;

import lombok.Data;
@Data
public class BootCommentVO {
	private int no, board_no, group_id, group_step, group_tab, root, depth;
	private String id, name, msg;
	private Date regdate;
}
