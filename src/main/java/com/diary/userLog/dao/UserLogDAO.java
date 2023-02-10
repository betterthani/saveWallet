package com.diary.userLog.dao;

import org.springframework.stereotype.Repository;

import com.diary.userLog.model.UserLog;

@Repository
public interface UserLogDAO {
	
	public int insertUserLog(UserLog userlogAdd);

}
