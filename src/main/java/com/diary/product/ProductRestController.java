package com.diary.product;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diary.product.bo.ProductBO;
import com.diary.product.model.ShoppingListDTO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/product")
public class ProductRestController {
	@Autowired
	private ProductBO productBO;
	
	// shoppingList 글 게시 API
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
	
}
