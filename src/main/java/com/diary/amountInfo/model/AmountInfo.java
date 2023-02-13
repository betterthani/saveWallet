package com.diary.amountInfo.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmountInfo {
	
	private int id;
	private int userId;
	private int goalCount; // 목표금액
	private int remainingAmount; // 남은금액
	private Date createdAt;
}
