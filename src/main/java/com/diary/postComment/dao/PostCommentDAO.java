package com.diary.postComment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.diary.postComment.model.PostComment;

@Repository
public interface PostCommentDAO {

	// 댓글 가져오기
	public List<PostComment> selectPostCommentListByPostId(int postId);
	
	// 댓글 insert
	public int insertPostCommentByUserIdPostId(
			@Param("userId") int userId, 
			@Param("postId") int postId,
			@Param("content") String content);
	
	// 해당 댓글 삭제하기
	public void deletePostCommentByUserIdPostIdCommentId(
			@Param("userId") int userId, 
			@Param("postId") int postId,
			@Param("postCommentId") int postCommentId);
	
	// 해당 댓글 조회
	public int selectPostCommentByUserIdPostIdCommentId(
			@Param("userId") int userId, 
			@Param("postId") int postId,
			@Param("postCommentId") int postCommentId);
	
	// 게시물 댓글 삭제
	public void deletePostCommentByPostId(int postId);
}
