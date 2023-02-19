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
	
	// 댓글 전체 삭제
	public void deleteSCommnetByUserIdProductId(int userId, int productId) {
		sCommentDAO.deleteSCommnetByUserIdProductId(userId, productId);
	}
	
	
	// 해당 코멘트 삭제
	public void deleteSCommnetByUserIdProductIdsCommentId(int userId, int productId, int sCommentId) {
		sCommentDAO.deleteSCommnetByUserIdProductIdsCommentId(userId, productId, sCommentId);
	}
	
	// 해당 코멘트 조회
	public int existCommentByUserIdProductIdsCommentId(int userId, int productId, int sCommentId) {
		return sCommentDAO.existCommentByUserIdProductIdsCommentId(userId, productId, sCommentId);
	}
	
	// 댓글 삭제 가공
	public boolean generateSComment(int userId, int productId, int sCommentId) {
		// 조회
		int row = existCommentByUserIdProductIdsCommentId(userId, productId, sCommentId);
		if(row > 0) {
			// 삭제
			deleteSCommnetByUserIdProductIdsCommentId(userId, productId, sCommentId);
			return true;
		} else {
			return false;
		}
		
	}
	
	// 회원탈퇴시 삭제
	public void deletesCommentByUserId(int userId) {
		sCommentDAO.deletesCommentByUserId(userId);
	}

}
