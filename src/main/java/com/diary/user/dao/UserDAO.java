package com.diary.user.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.diary.user.model.Mail;
import com.diary.user.model.User;

@Repository
public interface UserDAO {

	// 로그인
	public User selectUserByLoginIdPassword(
			@Param("loginId") String loginId, 
			@Param("password") String password);

	// 회원가입
	public int selectUserCount(User user);

	// 아이디 존재 여부 확인
	public boolean existLoginId(String loginId);
	
	// 비밀번호 찾기
	public boolean existFindPasswordByLoginIdEmail(
			@Param("loginId") String loginId, 
			@Param("email") String email);
	

}
