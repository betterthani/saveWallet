package com.diary.product.bo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.product.dao.ProductDAO;
import com.diary.product.model.Product;
import com.diary.product.model.TypeEnum;

@Service
public class ProductBO {

	@Autowired
	private ProductDAO productDAO;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final int POST_MAX_SIZE = 5;

	// 쇼핑리스트 화면 불러오기
	public List<Product> getProductListByUserId(int userId, Integer prevId, Integer nextId, String keyword, String orderCategory) {
		
		String direction = null;
		Integer standardId = null;
		
		if(prevId != null) {
			// 이전
			direction = "prev";
			standardId = prevId;
			List<Product> productList = productDAO.selectProductList(userId, direction, standardId, POST_MAX_SIZE, keyword, orderCategory);
			Collections.reverse(productList);
			return productList;
			
		} else if(nextId != null) {
			direction = "next";
			standardId = nextId;
		}
		return productDAO.selectProductList(userId, direction, standardId, POST_MAX_SIZE, keyword, orderCategory);
	}
	
	// 페이징 이전 마지막 페이지 여부 (내 글의 정렬이기 때문 userId필요)
	public boolean isPrevLastPage(int prevId, int userId) { // 컨트롤러에 받아온 타입

		int maxProductId = productDAO.selectProductIdByUserIdSort(userId, "DESC"); // 다오 입장 필요한 파라미터 보내기

		return maxProductId == prevId ? true : false;
	}

	// 페이징 다음 마지막 페이지 여부
	public boolean isNextLastPage(int nextId, int userId) {
		
		int minProductid = productDAO.selectProductIdByUserIdSort(userId, "ASC");

		return minProductid == nextId ? true: false;
	}
	
	// keyword검색시 POST_MAX_SIZE보다 작을 경우
	public boolean keywordProductCount(int userId, String keyword) {
		int row = productDAO.keywordProductCount(userId, keyword);
		if(row <= POST_MAX_SIZE) { // 키워드 개수가 포스트 개수보다 적을 경우 true -> prevId, nextId 0으로 만들기 위해
			return true; 
		}
		return false;
	}

	
}
