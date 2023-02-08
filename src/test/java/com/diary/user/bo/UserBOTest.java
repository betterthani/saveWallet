package com.diary.user.bo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.diary.user.model.User;

@SpringBootTest
class UserBOTest {
	@Autowired
	UserBO userBO;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
/*	
	@Test
	void test() {
		User user = userBO.getUserByLoginIdPassword("test", "b59c67bf196a4758191e42f76670ceba");
		logger.info("::::::::::::::::test" + user);
	}
*/
	@Test
	void test1() {
		boolean duplicatedLoginIdEmail = userBO.existFindPasswordByLoginIdEmail("test", "test@naver.com");
		logger.info("::::::::::::::::test" + duplicatedLoginIdEmail);
	}
}
