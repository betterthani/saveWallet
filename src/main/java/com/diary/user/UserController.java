package com.diary.user;

import java.lang.reflect.Member;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.diary.user.bo.UserBO;
import com.diary.user.model.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserBO userBO;
	
	/**
	 * 로그인 화면
	 * @param model
	 * @return
	 */
	// http://localhost:8080/user/sign_in_view
	@GetMapping("/sign_in_view")
	public String signInView(Model model) {
		
		model.addAttribute("viewName", "user/signIn");
		return "template/layout";
	}
	
	/**
	 * 로그아웃
	 * @param session
	 * @return
	 */
	@GetMapping("/sign_out")
	public String signOut(HttpSession session) {
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userNickName");
		
		return "redirect:/user/sign_in_view";
	}
	
	/**
	 * 회원가입 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/sign_up_view")
	public String signUpView(Model model) {
		model.addAttribute("viewName", "user/signUp");
		return "template/layout";
	}
	
	/**
	 * 비밀번호 찾기 화면
	 */
	@GetMapping("/find_password_view")
	public String findPasswordView(Model model) {
		model.addAttribute("viewName", "user/findPassword");
		return "template/layout";
	}
	
	/**
	 * 마이페이지 화면
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/mypage_view")
	public String myPageView(
			Model model,
			HttpSession session) {
		
		int userId = (int) session.getAttribute("userId");
		
		User user = userBO.getUserByUserId(userId);
		model.addAttribute("user", user);
		
		model.addAttribute("viewName", "user/myPage");
		return "template/layoutUserPage";
	}
	
}
