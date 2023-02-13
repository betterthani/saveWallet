package com.diary.product.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.diary.product.model.CategoryEnum;
import com.diary.product.model.MonthDTO;
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
	
	// 기존 글 가져오기
	public Product selectProductByUserIdProductId(
			@Param("userId") int userId, 
			@Param("productId") int productId);
	
	// shoppinglist글 수정하기
	public int updateShoppingList(
			@Param("userId") int userId,
			@Param("productId") int productId,
			@Param("itemName") String itemName,
			@Param("category") CategoryEnum category,
			@Param("amount") int amount,
			@Param("size") String size,
			@Param("color") String color,
			@Param("datePurchased") Date datePurchased,
			@Param("returnableDeadline") Date returnableDeadline,
			@Param("usedHope") boolean usedHope,
			@Param("imgPath") String imgPath);

	// 해당 글 삭제
	public boolean deletesProductByUserIdProductId(
			@Param("userId") int userId,
			@Param("productId") int productId);
	
	// 유저의 쇼핑목록 가져오기
	public List<Product> selectSProductListByUserId(int userId);
	
	// 월별 합계
	public List<MonthDTO> selectGroupBySum(int userId);
	
}
