package com.diary.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diary.post.bo.PostBO;

@RestController
@RequestMapping("/post")
public class PostRestController {

	@Autowired
	private PostBO postBO;

	/**
	 * 타임라인 글 등록 API
	 * 
	 * @param title
	 * @param subject
	 * @param fileList
	 * @param session
	 * @return
	 */
	@PostMapping("/timeline_create")
	public Map<String, Object> timelineCreate(@RequestParam("title") String title,
			@RequestParam("subject") String subject, @RequestParam("fileList") List<MultipartFile> fileList,
			HttpSession session) {

		int userId = (int) session.getAttribute("userId");
		String userLoginId = (String) session.getAttribute("userLoginId");

		boolean isPost = false;

		// 글 저장
		isPost = postBO.generateAddPost(userId, userLoginId, title, subject, fileList);

		Map<String, Object> result = new HashMap<>();
		if (isPost) {
			result.put("code", 1);
		} else {
			result.put("code", 500);
			result.put("result", "게시물 기재에 실패했습니다.");
		}

		return result;
	}

	/**
	 * 타임라인 삭제하기 API
	 * 
	 * @param session
	 * @param postId
	 * @return
	 */
	@DeleteMapping("/timeline_delete")
	public Map<String, Object> timelineDelete(
			HttpSession session, 
			@RequestParam(value="postId", required = false) Integer postId) {

		int userId = (int) session.getAttribute("userId");
		Map<String, Object> result = new HashMap<>();

		// 타임라인 삭제 : 해당 댓글, 글, 사진, 저장하기 삭제
		boolean existDeletePost = postBO.generateDeletePost(userId, postId);
		if (existDeletePost) {
			result.put("code", 1);
		} else {
			result.put("code", 2);
		}

		return result;
	}
	
	// 타임라인 수정하기 API
	@PutMapping("/timeline_edit")
	public Map<String, Object> timelineEdit(
			@RequestParam("postId") int postId,
			@RequestParam("title") String title,
			@RequestParam("subject") String subject,
			HttpSession session){
		
		int userId = (int) session.getAttribute("userId");
		Map<String, Object> result = new HashMap<>();
		
		postBO.updatePost(userId, postId, title, subject);
		result.put("code", 1);
		
		return result;
	}
	

}
