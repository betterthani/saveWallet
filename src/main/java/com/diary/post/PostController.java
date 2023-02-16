

package com.diary.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diary.post.bo.PostBO;
import com.diary.post.model.CardView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostBO postBO; 
	
	// localhost:8080/post/timeline_view
	/**
	 * 타임라인 화면 (이거 어때?)
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/timeline_view")
	public String timelineView(Model model, HttpSession session) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		model.addAttribute("userId",userId);
		
		List<CardView> cardviewList = postBO.generateCardList(userId); 
		model.addAttribute("cardviewList",cardviewList);
		
		model.addAttribute("viewName", "post/timeline");
		return "template/layout";
	}
	
	/**
	 * 글쓰기 화면(이거 어때?)
	 * @param model
	 * @return
	 */
	// localhost:8080/post/write_view
	@GetMapping("/write_view")
	public String postWriteView(Model model) {
		model.addAttribute("viewName", "post/postWrite");
		return "template/layout";
	}
}
