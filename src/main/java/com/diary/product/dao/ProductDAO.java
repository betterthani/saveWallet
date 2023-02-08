package com.diary.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.diary.product.model.Product;
import com.diary.product.model.TypeEnum;

@Repository
public interface ProductDAO {
	
	// 쇼핑리스트 목록 불러오기
	public List<Product> selectProductListByTypeUserId(
			@Param("userId") int userId,
			@Param("direction") String direction,
			@Param("standardId") Integer standardId,
			@Param("limit") int limit);
	
	// 페이징 이전 마지막 페이지 여부
	public int selectProductIdByUserIdSort(
			@Param("userId") int userId,
			@Param("sort") String sort);
	
	

}
