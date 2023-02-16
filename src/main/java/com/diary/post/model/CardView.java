package com.diary.post.model;

import java.util.List;

import com.diary.postComment.model.PostCommentView;
import com.diary.save.model.Save;
import com.diary.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardView {
	
	// 글 1개
	private Post post;
	
	// 사진 여러개
	private List<PostImage> postImageList;
	
	// 글쓴이 정보
	private User user;
	
	// 댓글 n개
	private List<PostCommentView> postCommentList;
	
	// 내가 저장버튼을 눌렀는지 
	private boolean filledSave;
	
}
