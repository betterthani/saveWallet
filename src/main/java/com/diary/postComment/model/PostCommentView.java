package com.diary.postComment.model;

import com.diary.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCommentView {
	
	// 코멘트 1개
	private PostComment postComment;
	
	// 코멘트 단 유저 정보 1개
	private User user;
}
