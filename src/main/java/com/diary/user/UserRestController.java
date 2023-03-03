package com.diary.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diary.common.EncryptUtils;
import com.diary.common.model.ValidateHandler;
import com.diary.post.bo.PostBO;
import com.diary.product.bo.ProductBO;
import com.diary.user.bo.MailBO;
import com.diary.user.bo.UserBO;
import com.diary.user.model.Age;
import com.diary.user.model.Gender;
import com.diary.user.model.Mail;
import com.diary.user.model.User;
import com.diary.user.model.ValidationPassword;
import com.diary.user.model.ValidationUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ValidateHandler validateHandler;
	
	@Autowired
	private MailBO mailBO;
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private ProductBO productBO;
	
	/**
	 * 로그인API
	 * @param loginId
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("/sign_in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request){
		
		// 해싱
		String hashedPassword = EncryptUtils.md5(password);
		
		// select DB
		User user = userBO.getUserByLoginIdPassword(loginId, hashedPassword);
		
		Map<String, Object> result = new HashMap<>();
		if(user != null) {
			result.put("code", 1);
			
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userNickName", user.getNickName());
		} else {
			result.put("code", 2);
		}
		
		// return
		return result;
	}
	
	/**
	 * 회원가입API
	 * @param loginId
	 * @param password
	 * @param nickName
	 * @param email
	 * @param gender
	 * @param age
	 * @return
	 */
	@PostMapping("/sign_up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("nickName") String nickName,
			@RequestParam("email") String email,
			@RequestParam("gender") String gender,
			@RequestParam("age") String age) {
		
		// 해싱
		String hashedPassword = EncryptUtils.md5(password);
		
		User user = User.builder()
				.loginId(loginId)
				.nickName(nickName)
				.email(email)
				.password(hashedPassword)
				.gender((gender.equals("female")? Gender.FEMALE : Gender.MALE ))
				.age(Age.ofAge(age))
				.build();
		
		// insert
		Map<String, Object> result = new HashMap<>();
		int row = userBO.addUserCount(user);
		if(row > 0) {
			result.put("code", 1);
		} else {
			result.put("code", 2);
		}
		
		// return
		return result;
	}
	
	/**
	 * 회원가입 유효성 검사API
	 * @param validationUser
	 * @param errors
	 * @return
	 */
	@PostMapping("/sign_up_validation")
	public Map<String,Object> signUpValidation(
			@ModelAttribute @Valid ValidationUser validationUser
			, Errors errors){
		Map<String,Object> result=new HashMap<String,Object>();
		
		// 유효성 검사
		if(errors.hasErrors()) {
			
			// 유효성 통과 못한 필드와 메세지 핸들링
			Map<String, String> validatorResult = validateHandler.validateHandling(errors);
			for(String key : validatorResult.keySet()) {
				result.put(key, validatorResult.get(key));
			}
			result.put("code",500);
			
			// 유효성 체크 중 오류 발생시 아래 코드 실행 안 해도됨.
			return result;
		}
		
		// 유효성 검사 완료 -> 아이디 중복체크
		boolean isDuplicated = userBO.existLoginId(validationUser.getLoginId());
		if(isDuplicated) {
			// 아이디 중복
			result.put("existLoginId", 1);
			result.put("code", 2);
		} else {
			// 중복되지 않고, 유효성 검사도 통과
			result.put("code", 1);
		}
		return result;
		
	}
	/**
	 * 비밀번호 찾기API (Email과 name의 일치여부를 check하는 컨트롤러)
	 * @param loginId
	 * @param email
	 * @return
	 */
	@PostMapping("/find_password")
	public Map<String, Object> findPassword(
			@RequestParam("loginId") String loginId,
			@RequestParam("email") String email){
		Map<String, Object> result = new HashMap<>();
		boolean duplicatedLoginIdEmail = userBO.existFindPasswordByLoginIdEmail(loginId, email);
		
		result.put("result", duplicatedLoginIdEmail);
		return result;
	}
	
	/**
	 * 비밀번호 찾기 API(등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러)
	 * @param email
	 */
	@Transactional
	@PostMapping("/find_password/sendEmail")
	public void sendEmail(
			@RequestParam("email") String email) {
		Mail maildto = mailBO.createMailChangePassword(email);
		mailBO.mailSend(maildto);
	}
	
	/**
	 * 프로필 수정 API
	 * @param session
	 * @param nickName
	 * @param statusMessage
	 * @param password
	 * @param file
	 * @return
	 */
	@PutMapping("/in/myPage/update")
	public Map<String, Object> myPageUpdate(
			HttpSession session,
			@RequestParam("nickName") String nickName,
			@RequestParam("statusMessage") String statusMessage,
			@RequestParam("password") String password,
			@RequestParam(value="file", required = false) MultipartFile file){
		
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// 해싱
		String hashedPassword = EncryptUtils.md5(password);
		
		Map<String, Object> result = new HashMap<>();
		boolean existPassword = userBO.getPasswordByPassword(userId, hashedPassword);
		if(existPassword) {
			userBO.updateUser(userId, nickName, userLoginId, statusMessage, hashedPassword, file);
			result.put("code", 1);
		} else {
			result.put("code", 2);
		}
		return result;
	}
		
	/**
	 * 비밀번호 수정API
	 * @param password
	 * @param changePassword
	 * @param session
	 * @return
	 */
	@PutMapping("/in/password_update")
	public Map<String, Object> passwordUpdate(
			@RequestParam("password") String password,
			@RequestParam("changePassword") String changePassword,
			HttpSession session){
		
		int userId = (int)session.getAttribute("userId");
		
		// 해싱
		String hashedPassword = EncryptUtils.md5(password);
		String newHashedPassword = EncryptUtils.md5(changePassword);
		
		Map<String, Object> result = new HashMap<>();
		
		// 비밀번호 맞는지 조회
		boolean existPassword = userBO.getPasswordByPassword(userId, hashedPassword);
		if(existPassword) {
			// 맞으면 비밀번호 업데이트
			userBO.passwordUpdate(userId, newHashedPassword);
			result.put("code", 1);
		} else {
			// 기존 비밀번호 비일치
			result.put("code", 2);
		}
		//
		return result;
	}
	
	/**
	 * 비밀번호 유효성 검사 API
	 * @param validationPassword
	 * @param errors
	 * @return
	 */
	@PostMapping("/in/passwordUpdate_validation")
	public Map<String,Object> passwordUpdateValidation(
			@ModelAttribute @Valid ValidationPassword validationPassword
			, Errors errors){
		Map<String,Object> result=new HashMap<String,Object>();
		
		// 유효성 검사
		if(errors.hasErrors()) {
			
			// 유효성 통과 못한 필드와 메세지 핸들링
			Map<String, String> validatorResult = validateHandler.validateHandling(errors);
			for(String key : validatorResult.keySet()) {
				result.put(key, validatorResult.get(key));
			}
			result.put("code",500);
		}
		
		return result;
		
	}
	
	/**
	 * 회원탈퇴 API
	 * @param session
	 * @param password
	 * @return
	 */
	@DeleteMapping("/in/secession")
	public Map<String,Object> userSecession(
			HttpSession session,
			@RequestParam("password") String password){
		
		int userId = (int)session.getAttribute("userId");
		
		// 해싱
		String hashedPassword = EncryptUtils.md5(password);
		
		Map<String,Object> result = new HashMap<String,Object>();
		User user = userBO.getUserByUserIdPassword(userId, hashedPassword);
		// 회원정보 조회
		if(user != null) {
			// 있을 경우 회원 삭제, 프로필 삭제
			userBO.deleteUserByUserId(userId, hashedPassword);
			
			// amountInfo ,post, postcomment, postImage, save삭제
			postBO.generateDelete(userId);
			
			// product삭제 , shoppingComment삭제
			productBO.generateDelete(userId);
			
			result.put("code", 1);
		} else {
			result.put("code", 2);
			result.put("result", "기존 비밀번호가 아닙니다.");
		}
		return result;
	}
	
	
	
}
