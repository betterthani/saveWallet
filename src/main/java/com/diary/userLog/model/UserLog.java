package com.diary.userLog.model;

import com.diary.product.model.CategoryEnum;
import com.diary.user.model.Age;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLog {
	
	private int id;
	private int userId;
	private Age age;
	private CategoryEnum category;
	private int amount;
}
