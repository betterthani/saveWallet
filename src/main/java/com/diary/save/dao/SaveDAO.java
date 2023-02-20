package com.diary.save.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.diary.save.model.Save;

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
	
	// 게시물 save삭제
	public void deleteSaveByPostId(int postId);
	
	// 회원탈퇴시 삭제
	public void deleteSaveByUserId(int userId);
	
	// savelist가져오기
	public List<Save> selectSaveListByUserId(int userId);
}
