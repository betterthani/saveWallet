package com.diary.userLog;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diary.userLog.bo.UserLogBO;

@RestController
public class UserLogRestController {
	
	@Autowired
	private UserLogBO userLogBO;

	@PostMapping("/userLog/create")
	public Map<String, Object> userLogCreate(
			@RequestParam("age") String age,
			@RequestParam("category") String category,
			@RequestParam("amount") int amount,
			HttpSession session) {
		
		int userId = (int) session.getAttribute("userId");
		
		int row = userLogBO.addUserLog(userId, age, category, amount);
		Map<String, Object> result = new HashMap<>();
		if(row > 0) {
			result.put("code", 1);
		}else {
			result.put("code", 500);
		}
		
		return result;
	}
}
