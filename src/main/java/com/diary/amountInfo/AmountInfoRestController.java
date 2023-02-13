package com.diary.amountInfo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diary.amountInfo.bo.AmountInfoBO;

import jakarta.servlet.http.HttpSession;

@RestController
public class AmountInfoRestController {
	
	@Autowired
	private AmountInfoBO amountInfoBO;
	
	@PostMapping("/amountInfo")
	public Map<String, Object> amountInfo(
			@RequestParam("goalCount") int goalCount
			,@RequestParam("remainingAmount") int remainingAmount
			,HttpSession session){
		
		int userId = (int)session.getAttribute("userId");
		
		Map<String, Object> result = new HashMap<>();
		// db있는지 조회
		Integer row = amountInfoBO.getAmountInfoByUserId(userId);
		if(row != null) {
			// 데이터 있으면 삭제
			amountInfoBO.deleteAmountInfoUserId(userId);
			// insert
			amountInfoBO.addAmountInfoByUserId(userId, goalCount, remainingAmount);
			result.put("code", 1);
		} else {
			// 데이터 없으면 그대로 insert
			amountInfoBO.addAmountInfoByUserId(userId, goalCount, remainingAmount);
			result.put("code", 1);
		}
		
		return result;
	}
}
