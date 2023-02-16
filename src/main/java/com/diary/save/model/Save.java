package com.diary.save.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Save {

	private int id;
	private int postId;
	private int userId;
	private Date createdAt;
	private Date updatedAt;
	
}
