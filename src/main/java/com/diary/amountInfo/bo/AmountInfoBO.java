package com.diary.amountInfo.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.amountInfo.dao.AmountInfoDAO;
import com.diary.amountInfo.model.AmountInfo;

@Service
public class AmountInfoBO {

	@Autowired
	private AmountInfoDAO amountInfoDAO;

	// insert
	public void addAmountInfoByUserId(int userId, int goalCount, int remainingAmount) {
		amountInfoDAO.insertAmountInfoByUserId(userId, goalCount, remainingAmount);
	}

	// select
	public Integer getAmountInfoByUserId(int userId) {
		return amountInfoDAO.selectAmountInfoByUserId(userId);
	}

	// delete
	public void deleteAmountInfoUserId(int userId) {
		amountInfoDAO.deleteAmountInfoUserId(userId);
	}

	public AmountInfo getamountInfoByUserId(int userId) {
		return amountInfoDAO.selectamountInfoByUserId(userId);
	}

}
