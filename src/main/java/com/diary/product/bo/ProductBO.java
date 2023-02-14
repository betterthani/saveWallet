package com.diary.product.bo;

import java.util.ArrayList;
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
import com.diary.product.model.MonthDTO;
import com.diary.product.model.ProductViewDTO;
import com.diary.product.model.Product;
import com.diary.product.model.PurchasedCategoryEnum;
import com.diary.shoppingComment.bo.ShoppingCommentBO;
import com.diary.shoppingComment.model.ShoppingComment;

@Service
public class ProductBO {

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ShoppingCommentBO sCommentBO;
	
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
	
	// 페이징 이전 마지막 페이지 여부
	public boolean isPrevLastPage(int prevId, int userId) { 
		int maxProductId = productDAO.selectProductIdByUserIdSort(userId, "DESC");
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

	// 글 가져오기
	public List<Product> getProductListByUserIdProductId(int userId, int productId, String type){
		return productDAO.selectProductListByUserIdProductId(userId, productId, type);
	}
	
	// 쇼핑리스트 글 가져오기 (가공 ver)
	public List<ProductViewDTO> generateSProudct(int userId, int productId, String type){
		List<ProductViewDTO> sProductView = new ArrayList<>();
		
		// 글 여러개 가져오기
		List<Product> sProductList = getProductListByUserIdProductId(userId, productId, type);
		for(Product sProduct : sProductList) {
			ProductViewDTO productView = new ProductViewDTO();
			
			// 글 1개
			productView.setProduct(sProduct);
			
			// 댓글 여러개
			List<ShoppingComment> sCommentList = sCommentBO.getsCommentListByProductId(productId);
			productView.setShopppingCommentList(sCommentList);
			
			sProductView.add(productView);
		}
		
		return sProductView;
	}
	
	// 기존 글 가져오기
	public Product getProductByUserIdProductId(int userId, int productId, String type) {
		return productDAO.selectProductByUserIdProductId(userId, productId, type);
	}
	
	// shoppinglist글 수정하기
	public int updateShoppingList(
			String type,
			int userId,
			String userLoginId,
			int productId,
			String itemName,
			String category,
			int amount,
			String size,
			String color,
			Date datePurchased,
			Date returnableDeadline,
			boolean usedHope,
			MultipartFile file) {
		
		// 기존 글
		type = "SHOPPING";
		Product product = getProductByUserIdProductId(userId, productId,type);
		if(product == null) {
			logger.warn("[update product] 수정할 product가 존재하지 않습니다. productId:{},userId:{}", productId,userId);
			
		}
		
		// 수정할게 있을 경우
		String imgPath = null;
		if(file != null) {
			imgPath = fileManagerService.savaFile(userLoginId, file);
			
			if(imgPath != null && product.getProductImgPath() != null) {
				fileManagerService.deleteFile(product.getProductImgPath());
			}
		}
		return productDAO.updateShoppingList(type, userId, productId, itemName, CategoryEnum.ofCategory(category), amount, size, color, datePurchased, returnableDeadline, usedHope, imgPath);
	}
	
	// 쇼핑리스트 글 삭제(가공)
	public void generateDelete(int userId, int productId, String type) {

		// 글 조회
		type = "SHOPPING";
		Product sProduct = getProductByUserIdProductId(userId, productId, type);
		
		if(sProduct == null) {
			logger.warn("[글 삭제] post is null. productId:{}, userId:{}", productId, userId);
		}
		
		// 이미지 있으면 삭제
		if (sProduct.getProductImgPath() != null) {
			fileManagerService.deleteFile(sProduct.getProductImgPath());
		}
		
		// 글 삭제
		productDAO.deletesProductByUserIdProductId(userId, productId);

		// 코멘트 있으면 삭제
		sCommentBO.deleteSCommnetByUserIdProductId(userId, productId);
	}
	
	// 유저의 쇼핑목록 가져오기
	public List<Product> getSProductListByUserId(int userId, String type){
		return productDAO.selectSProductListByUserId(userId, type);
	}
	
	// 월별 합계
	public List<MonthDTO> getGroupBySum(int userId){
		return productDAO.selectGroupBySum(userId);
	}
	
	// 위시리스트 insert
	public int addwishList(
			String itemName,
			String category,
			int amount,
			String purchasedCategory,
			String purchased,
			String size,
			String color,
			MultipartFile file, 
			String userLoginId, 
			int userId
			) {
		
		// 파일 저장
		String imagePath = null;
			if(file != null) {
			imagePath = fileManagerService.savaFile(userLoginId, file);
		}
		
		// 가공
		Product wishProduct = Product.builder()
				.itemName(itemName)
				.category(CategoryEnum.ofCategory(category))
				.amount(amount)
				.purchasedCategory(PurchasedCategoryEnum.ofPurchasedCategory(purchasedCategory))
				.purchased(purchased)
				.size(size)
				.color(color)
				.productImgPath(imagePath)
				.userId(userId)
				.build();
		
		return productDAO.insertwishList(wishProduct);
	}
	
	// 위시리스트 list개수 조회
	public int gettWishProductCountByUserId(int userId) {
		return productDAO.selectWishProductCountByUserId(userId);
	}
	
	// 위시리스트 삭제(가공)
	public void generateWishDelete(int userId, int productId, String type) {

		// 글 조회
		type = "WISH";
		Product wProduct = getProductByUserIdProductId(userId, productId, type);
			
		if(wProduct == null) {
			logger.warn("[글 삭제] post is null. productId:{}, userId:{}", productId, userId);
		}
		
		// 이미지 있으면 삭제
		if (wProduct.getProductImgPath() != null) {
			fileManagerService.deleteFile(wProduct.getProductImgPath());
		}
		
		// 글 삭제
		productDAO.deletesProductByUserIdProductId(userId, productId);
	}
	
}
