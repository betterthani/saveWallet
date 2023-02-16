package com.diary.post.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostImage {
	private int id;
	private int postId;
	private int userId;
	private String postImgPath;
	private Date createdAt;
	private Date updatedAt;
}
