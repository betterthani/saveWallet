package com.diary.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diary.user.model.UserEntity;

// entity 조회, 변경, 삭제 등
public interface UserRepository extends JpaRepository<UserEntity, String> {
	
	UserEntity findByEmail(String email);
	
}
