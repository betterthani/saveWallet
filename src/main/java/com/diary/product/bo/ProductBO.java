package com.diary.product.bo;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.diary.common.FileManagerService;
import com.diary.product.dao.ProductDAO;
import com.diary.product.model.CategoryEnum;
import com.diary.product.model.Product;
import com.diary.product.model.PurchasedCategoryEnum;

@Service
public class ProductBO {

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
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
	
	public boolean isPrevLastPage(int prevId, int userId) { 

		int maxProductId = productDAO.selectProductIdByUserIdSort(userId, "DESC");

		return maxProductId == prevId ? true : false;
	}

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
	
	// shoppingList 글 게시(insert)
	public int addShoppingProduct(
			String itemName,
			String category,
			int amount,
			String purchasedCategory,
			String purchased,
			String map,
			String size,
			String color,
			Date datePurchased,
			Date returnableDeadline,
			boolean usedHope,
			MultipartFile file, 
			String userLoginId, 
			int userId) {
		
		// 파일 저장
		String imagePath = null;
		if(file != null) {
			imagePath = fileManagerService.savaFile(userLoginId, file);
		}
		
		// dao넣을 파라미터 가공
		Product userProduct = Product.builder()
				.itemName(itemName)
				.category(CategoryEnum.ofCategory(category))
				.amount(amount)
				.purchasedCategory(PurchasedCategoryEnum.ofPurchasedCategory(purchasedCategory))
				.purchased(purchased)
				.map(map)
				.size(size)
				.color(color)
				.datePurchased(datePurchased)
				.returnableDeadline(returnableDeadline)
				.usedHope(usedHope)
				.productImgPath(imagePath)
				.userId(userId)
				.build();
		
		return productDAO.insertShoppingProduct(userProduct);
	}

	// 글 디테일 select
	public List<Product> getShoppingProductListByUserIdProductId(int userId, int productId) {
		return productDAO.selectShoppingProductListByUserIdProductId(userId, productId);
	}
}
