package com.diary.postComment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diary.postComment.bo.PostCommentBO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/postComment")
public class PostCommentRestController {
	
	@Autowired
	private PostCommentBO postCommentBO;
	
	/**
	 * 이거 어때? 댓글 달기
	 * @param postId
	 * @param content
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> postCommentCreate(
			@RequestParam("postId") int postId
			,@RequestParam("content") String content
			,HttpSession session){
		
		int userId = (int)session.getAttribute("userId");
		
		Map<String, Object> result = new HashMap<>();
		int row = postCommentBO.addPostCommentByUserIdPostId(userId, postId, content);
		if(row > 0) {
			// 성공
			result.put("code", 1);
		} else {
			// 실패
			result.put("code", 2);
		}
		
		return result;
	}
	
	/**
	 * 이거 어때? 댓글 삭제
	 * @param session
	 * @param postId
	 * @param postCommentId
	 * @return
	 */
	@DeleteMapping("/delete")
	public Map<String, Object> postCommentDelete(
			HttpSession session,
			@RequestParam("postId") int postId,
			@RequestParam("postCommentId") int postCommentId){
		
		int userId = (int)session.getAttribute("userId");
		
		Map<String, Object> result = new HashMap<>();
		boolean row = postCommentBO.generateDeleteComment(userId, postId, postCommentId);
		if(row) {
			// 성공
			result.put("code", 1);
		} else {
			// 실패
			result.put("code", 2);
		}
		
		return result;
	}
	

}
