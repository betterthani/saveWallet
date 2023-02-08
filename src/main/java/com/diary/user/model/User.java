package com.diary.user.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class User {
	private int id;
	private String loginId;
	private String nickName;
	private String password;
	private String email;
	private Age age;
	private Gender gender;
	private String statusMessage;
	private String profileImgPath;
	private Date createdAt;
	private Date updatedAt;
}
