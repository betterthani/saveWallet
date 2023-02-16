package com.diary.post.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {
	private int id;
	private int userId;
	private String title;
	private String subject;
	private Date createdAt;
	private Date updatedAt;
}
