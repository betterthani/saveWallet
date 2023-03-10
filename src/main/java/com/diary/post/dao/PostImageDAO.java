package com.diary.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.diary.post.model.PostImage;

@Repository
public interface PostImageDAO {
	
	// 사진 insert
	public void addPostImageByUserIdUserLoginIdFIle(
			@Param("userId") int userId
			, @Param("postImgPath") String postImgPath
			, @Param("postId") int postId);
	
	// 사진 list select
	public List<PostImage> selectPostImageList(int postId);
	
	// 해당 사진 조회
	public List<PostImage> selectPostImageListByUserIdPostId(
			@Param("userId") int userId,
			@Param("postId") Integer postId);
	
	// 해당 사진 삭제 
	public void deletePostImageByUserIdPostId(
			@Param("userId") int userId,
			@Param("postId") Integer postId);
	
}
