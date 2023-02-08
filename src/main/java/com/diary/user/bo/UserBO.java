package com.diary.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.user.dao.UserDAO;
import com.diary.user.model.User;

@Service
public class UserBO {
	
	@Autowired
	private UserDAO userDAO;
	
	
	// 로그인
	public User getUserByLoginIdPassword(String loginId, String password) {
		return userDAO.selectUserByLoginIdPassword(loginId, password);
	}
	
	// 회원가입
	public int addUserCount(User user) {
		return userDAO.selectUserCount(user);
	}
	
	// 아이디 존재 여부 확인
	public boolean existLoginId(String loginId) {
		return userDAO.existLoginId(loginId);
	}
	
	// 비밀번호 찾기 (아이디, 이메일 존재여부 확인)
	public boolean existFindPasswordByLoginIdEmail(String loginId, String email) {
		return userDAO.existFindPasswordByLoginIdEmail(loginId, email);
	}
	
	
}
