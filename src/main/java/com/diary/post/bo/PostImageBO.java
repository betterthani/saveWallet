package com.diary.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.diary.common.FileManagerService;
import com.diary.post.dao.PostImageDAO;
import com.diary.post.model.PostImage;

@Service
public class PostImageBO {

	@Autowired
	private PostImageDAO postImageDAO;
	
	@Autowired
	private FileManagerService fileManagerService;

	// 사진 insert
	public void addPostImageByUserIdUserLoginIdFIle(int userId, String userLoginId, MultipartFile file, int postId) {
		
		// 파일 저장
		String postImgPath = null;
		if (file != null) {
			postImgPath = fileManagerService.savaFile(userLoginId, file);
		}

		postImageDAO.addPostImageByUserIdUserLoginIdFIle(userId, postImgPath, postId);
	}
	
	// 사진 list select
	public List<PostImage> getPostImageList(int postId){
		return postImageDAO.selectPostImageList(postId);
	}
	
	// 해당 사진 조회
	public List<PostImage> getPostImageListByUserIdPostId(int userId, Integer postId){
		return postImageDAO.selectPostImageListByUserIdPostId(userId, postId);
	}
	
	// 해당 사진 삭제 
	public void deletePostImageByUserIdPostId(int userId, Integer postId, String postImgPath) {
		
		if(postImgPath != null) {
			fileManagerService.deleteFile(postImgPath);
		}
		
		postImageDAO.deletePostImageByUserIdPostId(userId, postId);
	}
	
}
