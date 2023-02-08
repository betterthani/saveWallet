package com.diary.product.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
	private int id;
	private int userId;
	private TypeEnum type;
	private String itemName;
	private CategoryEnum category;
	private int amount;
	private PurchasedCategoryEnum purchasedCategory; //구매카테고리
	private String purchased; // 구매처
	private String map;
	private String size;
	private String color;
	private Date datePurchased; //구매일
	private Date returnableDeadline; //반품가능일
	private boolean usedHope; //당근희망일
	private String productImgPath;
	private Date createdAt;
	private Date updatedAt;
	
	
}
