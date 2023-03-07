package com.diary.save;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.diary.save.bo.SaveBO;

@RestController
public class SaveRestController {
	
	@Autowired
	private SaveBO saveBO;
	
	/**
	 * 저장하기, 해제 API
	 * @param postId
	 * @param session
	 * @return
	 */
	@GetMapping("/save/{postId}")
	public Map<String, Object> save(
			@PathVariable int postId,
			HttpSession session){
		int userId = (int)session.getAttribute("userId");
		
		Map<String, Object> result = new HashMap<>();
		boolean row = saveBO.saveToggle(userId, postId);
		if(row) {
			result.put("code", 1);
		} else {
			result.put("code", 2);
		}
			
		return result;
	}
}
