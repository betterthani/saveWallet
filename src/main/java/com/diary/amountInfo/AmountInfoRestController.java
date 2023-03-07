package com.diary.amountInfo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diary.amountInfo.bo.AmountInfoBO;

@RestController
public class AmountInfoRestController {
	
	@Autowired
	private AmountInfoBO amountInfoBO;
	
	/**
	 * 캘린더상 목표금액, 남은금액 조회, 삭제, 생성API
	 * @param goalCount
	 * @param remainingAmount
	 * @param session
	 * @return
	 */
	@PostMapping("/amountInfo")
	public Map<String, Object> amountInfo(
			@RequestParam("goalCount") int goalCount
			,@RequestParam("remainingAmount") int remainingAmount
			,HttpSession session){
		
		int userId = (int)session.getAttribute("userId");
		
		Map<String, Object> result = new HashMap<>();
		// db있는지 조회
		boolean row = amountInfoBO.existAmountInfoByUserId(userId);
		if(row) {
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
