package com.diary.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.diary.product.model.Product;

@Repository
public interface ProductDAO {
	
	// 쇼핑리스트 목록 불러오기
	public List<Product> selectProductList(
			@Param("userId") int userId,
			@Param("direction") String direction,
			@Param("standardId") Integer standardId,
			@Param("limit") int limit,
			@Param("keyword") String keyword,
			@Param("orderCategory") String orderCategory);
	
	// 페이징 이전 마지막 페이지 여부
	public int selectProductIdByUserIdSort(
			@Param("userId") int userId,
			@Param("sort") String sort);
	
	// keyword검색시 POST_MAX_SIZE보다 작을 경우
	public int keywordProductCount(
			@Param("userId") int userId, 
			@Param("keyword") String keyword);
	
	// 글 게시
	public int insertShoppingProduct(Product userProduct);
	
	// 글 가져오기
	public List<Product> selectProductListByUserIdProductId(
			@Param("userId") int userId, 
			@Param("productId") int productId);
	
	
	

}
