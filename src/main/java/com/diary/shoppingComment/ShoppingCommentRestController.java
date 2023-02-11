package com.diary.shoppingComment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diary.shoppingComment.bo.ShoppingCommentBO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/shoppingComment")
public class ShoppingCommentRestController {
	
	@Autowired
	private ShoppingCommentBO sCommentBO;
	
	/**
	 * 댓글 생성API
	 * @param content
	 * @param productId
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> sCommentCreate(
			@RequestParam("content") String content
			,@RequestParam("productId") int productId			
			,HttpSession session){
		
		int userId = (int)session.getAttribute("userId");
		
		// insert
		int row = sCommentBO.addSCommentByuserIdProductId(userId, productId,content);
		Map<String, Object> result = new HashMap<>();
		
		if(row > 0) {
			result.put("code", 1);
		}else {
			result.put("code", 500);
		}
		
		return result;
	}
	
	// 댓글 삭제 API
	@DeleteMapping("/delete")
	public Map<String, Object> sCommentDelete(
			@RequestParam("productId") int productId,
			@RequestParam("sCommentId") int sCommentId,
			HttpSession session){
		int userId = (int)session.getAttribute("userId");
		
		Map<String, Object> result = new HashMap<>();
		// 댓글 조회 -> 있으면 삭제
		boolean existComment = sCommentBO.generateSComment(userId, productId,sCommentId);
		if(existComment) {
			// 삭제 성공
			result.put("code", 1);
		} else {
			result.put("code", 500);
		}
		
		return result;
	}
}
