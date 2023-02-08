package com.diary.product.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.product.dao.ProductDAO;
import com.diary.product.model.Product;
import com.diary.product.model.TypeEnum;

@Service
public class ProductBO {

	@Autowired
	private ProductDAO productDAO;

	// 쇼핑리스트 화면 불러오기
	public List<Product> getProductListByTypeUserId(int userId) {
		return productDAO.selectProductListByTypeUserId(userId);
	}

	
}
