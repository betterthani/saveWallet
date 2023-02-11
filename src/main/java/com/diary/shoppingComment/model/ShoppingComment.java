package com.diary.shoppingComment.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingComment {
	
	private int id;
	private int productId;
	private int userId;
	private int content;
	private Date createdAt;
	private Date updatedAt;
	
}
