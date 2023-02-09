package com.diary.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diary.test.dao.TestDAO;

@Controller
public class TestController {
	@Autowired
	private TestDAO testDAO;

	// String 점검
	@GetMapping("/test1")
	@ResponseBody
	public String test1() {
		return "hello";
	}

	// map
	@GetMapping("/test2")
	@ResponseBody
	public Map<String, Object> test2() {
		Map<String, Object> result = new HashMap<>();
		result.put("키1", 1);
		result.put("키2", "hey");
		result.put("키3", 3333);
		return result;
	}

	// jsp 리턴 점검
	@GetMapping("/test3")
	public String test3() {
		return "test/test";
	}

	// DB 접속 정보
	@GetMapping("/test4")
	@ResponseBody
	public List<Map<String, Object>> test4() {
		return testDAO.selectPostListTest();
	}

}
