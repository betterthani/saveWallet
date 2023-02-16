package com.diary.postComment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.diary.postComment.model.PostComment;

@Repository
public interface PostCommentDAO {

	// 댓글 가져오기
	public List<PostComment> selectPostCommentListByPostId(int postId);
}
