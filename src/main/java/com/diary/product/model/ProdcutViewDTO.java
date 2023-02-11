package com.diary.product.model;

import java.util.List;

import com.diary.shoppingComment.model.ShoppingComment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProdcutViewDTO {
	// 1개의 포스트
	private Product product;
	// 여러개의 댓글
	private List<ShoppingComment> shopppingCommentList;
}
