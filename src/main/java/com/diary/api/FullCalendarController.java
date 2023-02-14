package com.diary.api;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diary.amountInfo.bo.AmountInfoBO;
import com.diary.amountInfo.model.AmountInfo;
import com.diary.product.bo.ProductBO;
import com.diary.product.model.MonthDTO;
import com.diary.product.model.Product;

import jakarta.servlet.http.HttpSession;

@Controller
public class FullCalendarController {

	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private AmountInfoBO amountInfoBO;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/calendar")
	public String fullCalendar(
			HttpSession session
			,Model model) {
		
		int userId = (int)session.getAttribute("userId");
		
		// date기준 월 구하기
		DateFormat sd = new SimpleDateFormat("yyyy-MM");
		Date now = new Date();
		String nowDate = sd.format(now);
		
		// select 
		List<MonthDTO> monthList = productBO.getGroupBySum(userId);
		if(monthList.get(0).getM().equals(nowDate)) {
			model.addAttribute("sum", monthList.get(0).getSum());
		}
		
		model.addAttribute("nowDate", nowDate);
		
		// select
		AmountInfo amountinfo = amountInfoBO.getamountInfoByUserId(userId);
		model.addAttribute("amountinfo",amountinfo);
		
		model.addAttribute("viewName", "fullcalendar/calendar");
		return "template/layout";

	}

	@GetMapping("/monthPlan")
	@ResponseBody
	public List<Map<String, Object>> monthPlan(
			HttpSession session, 
			Model model) {

		int userId = (int)session.getAttribute("userId");
		
		List<Product> productList = productBO.getSProductListByUserId(userId, "SHOPPING");
		JSONArray jsonArr = new JSONArray();

		HashMap<String, Object> hash = new HashMap<>();
		DateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("###,###");
		
		int sum = 0;
		
		for (int i = 0; i < productList.size(); i++) {
			
			hash.put("title", df.format(productList.get(i).getAmount()));
			
			hash.put("start", sd.format(productList.get(i).getDatePurchased()));
			hash.put("url", "/product/shopping_list_write/detail_view?productId=" + productList.get(i).getId());

			JSONObject jsonObj = new JSONObject(hash);
			jsonArr.add(jsonObj);
		}
		
		logger.info("jsonArrCheck: {}", jsonArr);
		return jsonArr;
	}

}
