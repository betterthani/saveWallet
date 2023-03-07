package com.diary.user.bo;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.diary.common.FileManagerService;
import com.diary.user.dao.UserDAO;
import com.diary.user.model.User;

@Service
public class UserBO {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	
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
	
	// 유저 아이디로 조회
	public User getUserByUserId(int userId) {
		return userDAO.selectUserByUserId(userId);
	}
	
	// 비밀번호 조회
	public boolean getPasswordByPassword(int userId,String password) {
		return userDAO.selectPasswordByUserId(userId, password);
	}
	
	// 유저 프로필 업데이트
	public void updateUser(int userId, String nickName, String userLoginId, String statusMessage, String password, MultipartFile file) {
		// 기존 유저
		User user = getUserByUserId(userId);
		if(user == null) {
			logger.warn("[update User] 수정할 유저 정보가 없습니다. userId:{}",userId);
			return;
		}
		
		// 수정할게 있을 때
		String profileImgPath = null;
		if(file != null) {
			profileImgPath = fileManagerService.savaFile(userLoginId, file);
			
			if(profileImgPath != null && user.getProfileImgPath() != null) {
				fileManagerService.deleteFile(user.getProfileImgPath());
			}
		}
		
		// db update
		userDAO.updateUser(userId, nickName, statusMessage, password, profileImgPath);
	}
	
	// 비밀번호 업데이트
	public void passwordUpdate(int userId, String changePassword) {
		userDAO.passwordUpdate(userId, changePassword);
	}
	
	// 탈퇴시 회원정보 조회
	public User getUserByUserIdPassword(int userId, String password) {
		return userDAO.selectUserByUserIdPassword(userId, password);
	}
	
	// 회원 삭제
	@Transactional
	public void deleteUserByUserId(int userId, String password) {
		
		// 회원정보 조회
		User user = getUserByUserIdPassword(userId, password);
		
		// 있을경우 프로필 사진 삭제
		if(user.getProfileImgPath() != null) {
			fileManagerService.deleteFile(user.getProfileImgPath());
		}
		
		// 유저삭제
		userDAO.deleteUserByUserId(userId);
	}
	
	
}
