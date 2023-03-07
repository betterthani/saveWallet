package com.diary.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
