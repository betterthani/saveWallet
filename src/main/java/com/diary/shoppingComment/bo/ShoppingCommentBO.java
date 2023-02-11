package com.diary.shoppingComment.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.shoppingComment.dao.ShoppingCommentDAO;
import com.diary.shoppingComment.model.ShoppingComment;

@Service
public class ShoppingCommentBO {
	
	@Autowired
	private ShoppingCommentDAO sCommentDAO;
	
	// 코멘드 insert
	public int addSCommentByuserIdProductId(int userId, int productId, String content) {
		return sCommentDAO.addSCommentByuserIdProductId(userId, productId, content);
	}
	
	// 코멘트 select
	public List<ShoppingComment> getsCommentListByProductId(int productId){
		return sCommentDAO.selectsCommentListByProductId(productId);
	}

}
