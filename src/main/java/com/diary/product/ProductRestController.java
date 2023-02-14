package com.diary.product;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diary.product.bo.ProductBO;
import com.diary.product.dao.ProductDAO;
import com.diary.product.model.ShoppingListDTO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/product")
public class ProductRestController {
	
	@Autowired
	private ProductBO productBO;
	
	/**
	 * shoppingList 글 게시 API
	 * @param shoppingProduct
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/shopping_list_write")
	public Map<String, Object> shoppingListWrite(
			@ModelAttribute("shoppingProduct") ShoppingListDTO shoppingProduct,
			@RequestParam(value= "file", required =false) MultipartFile file,
			HttpSession session) {
		
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		Map<String, Object> result = new HashMap<>();
		int row = productBO.addShoppingProduct(
				shoppingProduct.getItemName(),
				shoppingProduct.getCategory(),
				shoppingProduct.getAmount(),
				shoppingProduct.getPurchasedCategory(),
				shoppingProduct.getPurchased(),
				shoppingProduct.getMap(),
				shoppingProduct.getSize(),
				shoppingProduct.getColor(),
				shoppingProduct.getDatePurchased(),
				shoppingProduct.getReturnableDeadline(),
				shoppingProduct.isUsedHope(),
				file,
				userLoginId,
				userId
				);
		
		
		if(row > 0) {
			result.put("code", 1);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "게시 실패했습니다. 관리자에게 문의하세요.");
		}
		
		return result;
	}
	
	/**
	 * 쇼핑리스트 글 수정 API
	 * @param shoppingProduct
	 * @param file
	 * @param productId
	 * @param session
	 * @return
	 */
	// /product/shopping_list_update
	@PutMapping("/shopping_list_update")
	public Map<String, Object> sListUpdate(
			@ModelAttribute("shoppingProduct") ShoppingListDTO shoppingProduct,
			@RequestParam(value= "file", required =false) MultipartFile file,
			@RequestParam("productId") int productId,
			HttpSession session) {
		
		int userId = (int) session.getAttribute("userId");
		String userLoginId = (String) session.getAttribute("userLoginId");
		
		// update
		int row = productBO.updateShoppingList(
				userId, 
				userLoginId, 
				productId, 
				shoppingProduct.getItemName(), 
				shoppingProduct.getCategory(), 
				shoppingProduct.getAmount(), 
				shoppingProduct.getSize(), 
				shoppingProduct.getColor(), 
				shoppingProduct.getDatePurchased(), 
				shoppingProduct.getReturnableDeadline(), 
				shoppingProduct.isUsedHope(), 
				file);
		
		Map<String, Object> result = new HashMap<>();
		if(row > 0) {
			result.put("code", 1);
		}else {
			result.put("code", 500);
		}
		
		return result;
	}
	
	/**
	 * 글과 해당 코멘트 삭제API
	 * @param productId
	 * @param session
	 * @return
	 */
	@DeleteMapping("/shopping_list/delete")
	public Map<String, Object> sDelete(
			@RequestParam("productId") int productId,
			HttpSession session){
		int userId = (int) session.getAttribute("userId");
		
		// 기존의 내용 있는지 확인 후 삭제
		productBO.generateDelete(userId, productId);
		Map<String, Object> result = new HashMap<>();
		result.put("code", 1);
		return result;
	}
	
	/**
	 * wishList 글 게시 API
	 * @param shoppingProduct
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/wish_list_write")
	public Map<String, Object> wProductWrite(
			@ModelAttribute("shoppingProduct") ShoppingListDTO shoppingProduct,
			@RequestParam(value= "file", required =false) MultipartFile file,
			HttpSession session
			){
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		Map<String, Object> result = new HashMap<>();
		int row = productBO.addwishList(
				shoppingProduct.getItemName(), 
				shoppingProduct.getCategory(), 
				shoppingProduct.getAmount(), 
				shoppingProduct.getPurchasedCategory(), 
				shoppingProduct.getPurchased(), 
				shoppingProduct.getSize(), 
				shoppingProduct.getColor(), 
				file, 
				userLoginId, 
				userId);
		
		if(row > 0) {
			// 저장 성공
			result.put("code", 1);
		} else {
			result.put("errorMessage", "저장에 실패했습니다.");
		}
		
		return result;
	}
	
}
