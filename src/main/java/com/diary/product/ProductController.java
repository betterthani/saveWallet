package com.diary.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.diary.product.bo.ProductBO;
import com.diary.product.model.Product;
import com.diary.product.model.TypeEnum;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductBO productBO;

	/**
	 * 쇼핑리스트 (게시목록 화면)
	 * @param model
	 * @param session
	 * @return
	 */
	// http://localhost:8080/product/shopping_list_view
	@GetMapping("/shopping_list_view")
	public String shoppingListVeiw(
			@RequestParam(value="prevId", required = false) Integer prevIdParam,
			@RequestParam(value="nextId", required = false) Integer nextIdParam,
			Model model, 
			HttpSession session) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/user/sign_in_view";
		}
		
		int prevId = 0;
		int nextId = 0;
		
		// select 
		List<Product> productList = productBO.getProductListByTypeUserId(userId, prevIdParam, nextIdParam);
		if(productList.isEmpty() == false) {
			prevId = productList.get(0).getId();
			nextId = productList.get(productList.size()-1).getId();
			
			if(productBO.isPrevLastPage(prevId, userId)) {
				prevId = 0;
			}
			
			if(productBO.isNextLastPage(nextId, userId)) {
				nextId = 0;
			}
		}
		model.addAttribute("prevId", prevId);
		model.addAttribute("nextId", nextId);

		// model 넣기 
		model.addAttribute("productList", productList);

		model.addAttribute("viewName", "product/shoppingList");
		return "template/layout";
	}
}