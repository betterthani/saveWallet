package com.diary.post.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.diary.post.model.Post;

@Repository
public interface PostDAO {
	
	// 글 insert
	public void insertPost(Post post);
	
	// 글 목록 가져오기 select
	public List<Post> selectpostList();
}
