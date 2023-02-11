package com.diary.shoppingComment.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.shoppingComment.dao.ShoppingCommentDAO;

@Service
public class ShoppingCommentBO {
	
	@Autowired
	private ShoppingCommentDAO sCommentDAO;
	
	// 코멘드 insert
	public int addSCommentByuserIdProductId(int userId, int productId) {
		return sCommentDAO.addSCommentByuserIdProductId(userId, productId);
	}

}
