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
}
