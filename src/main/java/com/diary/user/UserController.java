package com.diary.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.diary.post.bo.PostBO;
import com.diary.post.model.CardView;
import com.diary.save.bo.SaveBO;
import com.diary.user.bo.UserBO;
import com.diary.user.model.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private SaveBO saveBO;
	
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
	
	/**
	 * 내 타임라인 글 화면
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/timeline_view")
	public String timeLineView(
			Model model,
			HttpSession session,
			@RequestParam("userId") int userId) {
		
		int sessionId = (int)session.getAttribute("userId");
		
		// 게시글, 게시글에 대한 사진
		List<CardView> cardViewList = postBO.generateMyTimeLine(userId);
		model.addAttribute("cardViewList", cardViewList);
		
		// 유저 정보
		User user = userBO.getUserByUserId(userId);
		model.addAttribute("user",user);
		
		if(sessionId == userId) {
			model.addAttribute("viewName", "user/userTimeLine");
			return "template/layoutUserPage";
		} else {
			model.addAttribute("viewName", "user/userTimeLine");
			return "template/layout";
		}
		
	}
	
	
	/**
	 * 저장한 글 화면
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/save_view")
	public String saveView(
			Model model,
			HttpSession session) {
		int userId = (int)session.getAttribute("userId");
		
		// 유저 정보
		User user = userBO.getUserByUserId(userId);
		model.addAttribute("user",user);
		
		// save list 
		List<CardView> cardViewList = postBO.generateMySave(userId);
		model.addAttribute("cardViewList",cardViewList);
		
		model.addAttribute("viewName", "user/myPageSave");
		return "template/layoutUserPage";
	}
	
}
