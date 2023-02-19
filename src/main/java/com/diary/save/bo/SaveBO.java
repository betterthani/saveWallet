package com.diary.save.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.save.dao.SaveDAO;

@Service
public class SaveBO {
	
	@Autowired
	private SaveDAO saveDAO;
	
	// 저장 눌렀는지 여부
	public boolean existSave(Integer userId, int postId) {
		if(userId == null) {
			// 비로그인
			return false; // 빈모양 보내기
		}
		return saveDAO.existSave(userId, postId) > 0 ? true : false;
	}
	
	
	// 저장 토글
	public boolean saveToggle(int userId, int postId) {
		boolean existSave = existSave(userId, postId);
		if(existSave) {
			// 눌려있을 경우 삭제
			saveDAO.deleteSaveByUserIdPostId(userId, postId);
		} else {
			// 안 눌렸을 경우 insert
			saveDAO.insertSaveByUserIdPostId(userId, postId);
		}
		return true;
	}
	
	// 게시물 save삭제
	public void deleteSaveByPostId(int postId) {
		saveDAO.deleteSaveByPostId(postId);
	}
	
	// 회원탈퇴시 삭제
	public void deleteSaveByUserId(int userId) {
		saveDAO.deleteSaveByUserId(userId);
	}
	
}
