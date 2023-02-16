package com.diary.save.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveDAO {
	
	// 저장 눌렀는지 여부
	public int existSave(
			@Param("userId") Integer userId, 
			@Param("postId") int postId);
}
