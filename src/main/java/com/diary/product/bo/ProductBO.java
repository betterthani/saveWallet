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
	public List<Product> getProductListByTypeUserId(int userId, Integer prevId, Integer nextId) {
		
		String direction = null;
		Integer standardId = null;
		if(prevId != null) {
			// 이전
			direction = "prev";
			standardId = prevId;
			List<Product> productList = productDAO.selectProductListByTypeUserId(userId, direction, standardId, POST_MAX_SIZE);
			Collections.reverse(productList);
			return productList;
		} else if(nextId != null) {
			direction = "next";
			standardId = nextId;
		}
		return productDAO.selectProductListByTypeUserId(userId, direction, standardId, POST_MAX_SIZE);
	}
	
	// 페이징 이전 마지막 페이지 여부 (내 글의 정렬이기 때문 userId필요)
	public boolean isPrevLastPage(int prevId, int userId) { // 컨트롤러에 받아온 타입

		int maxProductId = productDAO.selectProductIdByUserIdSort(userId, "DESC"); // 다오 입장 필요한 파라미터 보내기

		return maxProductId == prevId ? true : false;
	}

	// 페이징 다음 마지막 페이지 여부 (내 글의 정렬이기 때문 userId필요)
	public boolean isNextLastPage(int nextId, int userId) {
		int minProductid = productDAO.selectProductIdByUserIdSort(userId, "ASC");

		return minProductid == nextId ? true: false;
	}
	
}
