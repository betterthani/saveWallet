package com.diary.userLog.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.product.model.CategoryEnum;
import com.diary.user.model.Age;
import com.diary.userLog.dao.UserLogDAO;
import com.diary.userLog.model.UserLog;

@Service
public class UserLogBO {
	
	@Autowired
	private UserLogDAO userLogDAO;
	
	public int addUserLog(int userId, String age, String category, int amount) {
		
		UserLog userlogAdd = UserLog.builder()
				.userId(userId)
				.age(Age.ofAge(age))
				.category(CategoryEnum.ofCategory(category))
				.amount(amount)
				.build();
		
		return userLogDAO.insertUserLog(userlogAdd);
	}
}
