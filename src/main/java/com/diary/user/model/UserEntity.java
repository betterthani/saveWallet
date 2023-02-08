package com.diary.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// 테이블 정리
@Entity
@Getter
@Setter
@Table(name="user")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@Column
	private String email;
	
/*
	@Column(name="loginId")
	private String login_id;
	
	@Column
	private String nickName;
	
	@Column
	private String password;
	
	
	@Enumerated(EnumType.STRING)
	private Age age;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column
	private String statusMessage;
	
	@Column
	private String profileImgPath;
*/
}
