package com.diary.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.diary.product.bo.ProductBO;
import com.diary.product.model.ProdcutViewDTO;
import com.diary.product.model.Product;

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
			@RequestParam(value="keyword", required = false) String keyword,
			@RequestParam(value="orderCategory", required = false) String orderCategory,
			Model model, 
			HttpSession session) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/user/sign_in_view";
		}
		
		int prevId = 0;
		int nextId = 0;
		
		// select 
		List<Product> productList = productBO.getProductListByUserId(userId, prevIdParam, nextIdParam, keyword, orderCategory);
		
		if(productList.isEmpty() == false) {
			prevId = productList.get(0).getId();
			nextId = productList.get(productList.size()-1).getId();
			
			if(productBO.isPrevLastPage(prevId, userId) || productBO.keywordProductCount(userId, keyword) ) {
				prevId = 0;
			}
			
			if(productBO.isNextLastPage(nextId, userId) || productBO.keywordProductCount(userId, keyword) ) {
				nextId = 0;
			}
		}
		// model 넣기 
		model.addAttribute("prevId", prevId);
		model.addAttribute("nextId", nextId);
		model.addAttribute("productList", productList);
		model.addAttribute("keyword", keyword);

		model.addAttribute("viewName", "product/shoppingList");
		return "template/layout";
	}
	
	/**
	 *  쇼핑리스트 글 작성 화면
	 * @param purchased
	 * @param model
	 * @param session
	 * @return
	 */
	 
	//localhost:8080/product/shopping_list_write_view
	@GetMapping("/shopping_list_write_view")
	public String shoppingListWriteView(
			@RequestParam(value="purchased", required = false) String purchased, 
			Model model, 
			HttpSession session) {
		
		int userId = (int)session.getAttribute("userId");
		
		model.addAttribute("purchased", purchased);
		
		model.addAttribute("viewName", "product/shoppingListWrite");
		return "template/layoutMap";
	}
	
	/**
	 * 글 상세, 수정가능 화면
	 * @param model
	 * @param session
	 * @param productId
	 * @return
	 */
	@GetMapping("/shopping_list_write/detail_view")
	public String shoppingDetailView(
			Model model
			, HttpSession session
			, @RequestParam("productId") int productId) {
		
		int userId = (int)session.getAttribute("userId");
		String userLogindId = (String)session.getAttribute("userLoginId");
		model.addAttribute("userId", userId);
		model.addAttribute("userLogindId", userLogindId);
		
		// 가공 select
		List<ProdcutViewDTO> sProductList = productBO.generateSProudct(userId, productId);
		model.addAttribute("sProductList",sProductList);
		
		model.addAttribute("viewName", "product/shoppingDetail");
		return "template/layoutMap";
	}
	
	//위시리스트 목록화면
	@GetMapping("/wish_list_view")
	public String wishListVeiw(
			Model model
			, HttpSession session) {
		
		int userId = (int)session.getAttribute("userId");
		
		model.addAttribute("viewName", "product/wishList");
		return "template/layout";
	}
	
	/**
	 * 위시리스트 글 작성 화면
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/wish_write_view")
	public String wishWriteView(
			Model model
			, HttpSession session) {
		
		int userId = (int)session.getAttribute("userId");
		
		model.addAttribute("viewName", "product/wishListWrite");
		return "template/layoutMap";
	}
	
}
