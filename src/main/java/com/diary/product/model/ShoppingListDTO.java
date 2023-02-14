package com.diary.product.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingListDTO {
	
	private String type;
	private String itemName;
	private String category;
	private int amount;
	private String purchasedCategory; //구매카테고리
	private String purchased; // 구매처
	private String map;
	private String size;
	private String color;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date datePurchased; //구매일
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date returnableDeadline; //반품가능일
	private boolean usedHope; //당근희망일
}
