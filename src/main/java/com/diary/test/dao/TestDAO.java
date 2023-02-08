package com.diary.test.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface TestDAO {
	// test용
	public List<Map<String, Object>> selectPostListTest();
}
