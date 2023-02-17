package com.diary.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.diary.post.model.Post;

@Repository
public interface PostDAO {
	
	// 글 insert
	public void insertPost(Post post);
	
	// 글 목록 가져오기 select
	public List<Post> selectpostList();
	
	// 해당 글 삭제하기
	public void deletePostByUserIdPostId(
			@Param("userId") int userId, 
			@Param("postId") int postId);
	
	// 해당 글 있는지 select
	public int selectPostByUserIdPostId(
			@Param("userId") int userId, 
			@Param("postId") int postId);
}
