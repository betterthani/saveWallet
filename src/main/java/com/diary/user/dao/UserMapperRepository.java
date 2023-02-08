package com.diary.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapperRepository {
	/*
	@Update
	("update user set password = #{password}, updated_at = now() where id = #{id}")
	*/
	public void updatePassword(
			@Param("id") int id, 
			@Param("password") String password);
	

	
	
}
