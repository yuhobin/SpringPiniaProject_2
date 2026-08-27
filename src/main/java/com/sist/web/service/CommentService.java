package com.sist.web.service;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sist.web.vo.BootCommentVO;
import com.sist.web.vo.CommentVO;

public interface CommentService {
public List<CommentVO> commentListData(int start, int fno); 
	
	public int commentRowCount(int fno);
	
	public void commentInsert(CommentVO vo);
	
	public void commentDelete(int no);
	
	public void commentUpdate(CommentVO vo);
	
	/*@Select("SELECT group_id, group_step, group_tab "
			+"FROM bootComment "
			+"WHERE no=#{no}") */
	//public BootCommentVO boardParentInfoData(int no);
	
	/*@Update("UPDATE bootComment SET "
			+"group_step=group_step+1 "
			+"WHERE group_id=#{group_id} AND group_step>#{group_step}") */
	/*public void boardGroupStepIncrement (
		int group_id,
		int group_step 
	); */
	
	/*@SelectKey(keyProperty = "no" , resultType = int.class , before = true,
			statement = "SELECT NVL(MAX(no)+1,1) as no FROM bootComment")
	@Insert("INSERT INTO bootComment VALUES("
			+"#{no}, #{board_no}, #{id}, #{name}, #{msg},"
			+"SYSDATE, #{group_id}, #{group_step},"
			+"#{group_tab}, #{root}, 0"
			+")") */
	//public void boardCommentReReply(int pno, BootCommentVO vo);
	
	/*@Update("UPDATE bootComment SET "
			+"depth=depth+1 "
			+"WHERE no=#{no}") */
	//public void boardDepthIncrement(int no);
}
