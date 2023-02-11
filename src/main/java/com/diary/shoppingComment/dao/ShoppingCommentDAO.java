package com.diary.shoppingComment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.diary.shoppingComment.model.ShoppingComment;

@Repository
public interface ShoppingCommentDAO {

	// 코멘드 insert
	public int addSCommentByuserIdProductId(
			@Param("userId") int userId, 
			@Param("productId") int productId,
			@Param("content") String content);

	// 코멘트 select
	public List<ShoppingComment> selectsCommentListByProductId(int productId);
}
