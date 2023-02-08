package com.diary.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.diary.product.model.Product;
import com.diary.product.model.TypeEnum;

@Repository
public interface ProductDAO {
	
	// 쇼핑리스트 목록 불러오기
	public List<Product> selectProductListByTypeUserId(int userId);
	
	

}
