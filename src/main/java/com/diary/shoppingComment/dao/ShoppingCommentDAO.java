package com.diary.shoppingComment.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCommentDAO {

	// 코멘드 insert
	public int addSCommentByuserIdProductId(
			@Param("userId") int userId, 
			@Param("productId") int productId);

}
