package com.diary.user.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

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
	
	// 유저 아이디로 조회
	public User selectUserByUserId(int userId); 
	
	// 비밀번호 조회
	public boolean selectPasswordByUserId(
			@Param("userId") int userId, 
			@Param("password") String password);
	
	// 프로필 업데이트
	public void updateUser(
			@Param("userId") int  userId, 
			@Param("nickName") String nickName, 
			@Param("statusMessage") String statusMessage, 
			@Param("password") String password, 
			@Param("profileImgPath") String profileImgPath);
	
	// 비밀번호 업데이트
	public void passwordUpdate(
			@Param("userId") int userId, 
			@Param("changePassword") String changePassword);
	
	// 탈퇴시 회원정보 조회
	public User selectUserByUserIdPassword(
			@Param("userId") int userId, 
			@Param("password") String password);
	
	// 회원 삭제
	public void deleteUserByUserId(int userId);
}
