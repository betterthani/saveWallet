package com.diary.amountInfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.diary.amountInfo.model.AmountInfo;

@Repository
public interface AmountInfoDAO {
	
	// insert
	public void insertAmountInfoByUserId(
			@Param("userId") int userId, 
			@Param("goalCount") int goalCount, 
			@Param("remainingAmount") int remainingAmount);
	
	//select
	public Integer selectAmountInfoByUserId(int userId);
	
	// delete
	public void deleteAmountInfoUserId(int userId);
	
	public AmountInfo selectamountInfoByUserId(int userId);

}
