package com.diary.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diary.common.EncryptUtils;
import com.diary.common.model.ValidateHandler;
import com.diary.user.bo.MailBO;
import com.diary.user.bo.UserBO;
import com.diary.user.model.Age;
import com.diary.user.model.Gender;
import com.diary.user.model.Mail;
import com.diary.user.model.User;
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
				.age((age.equals("teenageMore")?Age.TEENAGEMORE : Age.THIRTYMORE))
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
			result.put("existLoginId", "existLoginId");
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
	@PostMapping(value ="/find_password")
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
	
}
