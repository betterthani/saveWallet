package com.diary.shoppingComment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@PostMapping("/create")
	public Map<String, Object> sCommentCreate(
			@RequestParam("productId") int productId			
			,HttpSession session){
		
		int userId = (int)session.getAttribute("userId");
		
		// insert
		int row = sCommentBO.addSCommentByuserIdProductId(userId, productId);
		Map<String, Object> result = new HashMap<>();
		
		if(row > 0) {
			result.put("code", 1);
		}else {
			result.put("code", 500);
		}
		
		return result;
	}
}
