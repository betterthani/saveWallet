package com.diary.post.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.diary.post.dao.PostDAO;
import com.diary.post.model.CardView;
import com.diary.post.model.Post;
import com.diary.post.model.PostImage;
import com.diary.postComment.bo.PostCommentBO;
import com.diary.postComment.model.PostCommentView;
import com.diary.save.bo.SaveBO;
import com.diary.user.bo.UserBO;
import com.diary.user.model.User;

import jakarta.transaction.Transactional;

@Service
public class PostBO {
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private PostImageBO postImageBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private PostCommentBO postCommentBO;
	
	@Autowired
	private SaveBO saveBO;
	
	// 글 insert
	public void addPost(Post post) {
		postDAO.insertPost(post);
	}
	
	// 포토까지 추가한 글 insert
	@Transactional
	public boolean generateAddPost(int userId, String userLoginId, String title, String subject, List<MultipartFile> fileList) {
		Post post = new Post();
		post.setUserId(userId);
		post.setTitle(title);
		post.setSubject(subject);
		
		// 글 저장
		addPost(post);
		int postId = post.getId();
		
		if(fileList != null) {
			
			for (MultipartFile file : fileList) {
				// 사진 추가
				postImageBO.addPostImageByUserIdUserLoginIdFIle(userId, userLoginId, file, postId);
			}
		} 
		return true;
	}
	
	// 글 목록 가져오기 select
	public List<Post> getpostList(){
		return postDAO.selectpostList();
	}
	
	// 타임라인 select(가공)
	public List<CardView> generateCardList(Integer userId){
		List<CardView> cardViewList = new ArrayList<>();
		
		// 글 목록
		List<Post> postList = getpostList();
		for(Post post : postList) {
			CardView cardview = new CardView();
			
			// 글
			cardview.setPost(post);
			
			// 사진 여러개
			List<PostImage> postImageList = postImageBO.getPostImageList(post.getId());
			cardview.setPostImageList(postImageList);
			
			// 글쓴이
			User user = userBO.getUserByUserId(post.getUserId());
			cardview.setUser(user);
			
			// 댓글들(유저정보까지 가공ver)
			List<PostCommentView> postCommentViewList = postCommentBO.generatePostCommet(post.getId());
			cardview.setPostCommentList(postCommentViewList);
				
			// 저장하기 눌렀는지 여부
			cardview.setFilledSave(saveBO.existSave(userId, post.getId()));
			
			// 채우기
			cardViewList.add(cardview);
		}
		return cardViewList;
	}
	
	// 이거어때? 카드 삭제하기
	public boolean generateDeletePost(int userId, int postId) {
		// 포스트 있는지 여부
		int row = postDAO.selectPostByUserIdPostId(userId, postId);
		if(row > 0) {
			// 해당 포스트 삭제
			postDAO.deletePostByUserIdPostId(userId, postId);
			
			// 사진 삭제
			List<PostImage> postImageList = postImageBO.getPostImageListByUserIdPostId(userId, postId);
			for(PostImage postImage : postImageList) {
				postImageBO.deletePostImageByUserIdPostId(userId, postId, postImage.getPostImgPath());
			}
			
			// 댓글 삭제
			postCommentBO.deletePostCommentByPostId(postId);
			
			// 저장하기 삭제
			saveBO.deleteSaveByPostId(postId);
			
			return true;
			
		} else {
			// 해당 게시물 없음
			return false;
		}
	}
	
}
