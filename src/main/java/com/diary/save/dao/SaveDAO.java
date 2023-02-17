package com.diary.save.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveDAO {
	
	// 저장 눌렀는지 여부
	public int existSave(
			@Param("userId") Integer userId, 
			@Param("postId") int postId);
	
	// 삭제하기
	public void deleteSaveByUserIdPostId(
			@Param("userId") int userId, 
			@Param("postId") int postId);
	
	// 저장하기(insert)
	public void insertSaveByUserIdPostId(
			@Param("userId") int userId, 
			@Param("postId") int postId);
}
