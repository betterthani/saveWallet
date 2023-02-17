package com.diary.postComment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.postComment.dao.PostCommentDAO;
import com.diary.postComment.model.PostComment;
import com.diary.postComment.model.PostCommentView;
import com.diary.user.bo.UserBO;
import com.diary.user.model.User;

@Service
public class PostCommentBO {
	
	@Autowired
	private PostCommentDAO postCommentDAO;
	
	@Autowired
	private UserBO userBO;
	
	// 댓글 가져오기
	public List<PostComment> getPostCommentListByPostId(int postId){
		return postCommentDAO.selectPostCommentListByPostId(postId);
	}
	
	// 댓글 정보 가공하기
	public List<PostCommentView> generatePostCommet(int postId){
		// 결과물
		List<PostCommentView> postCommentViewList = new ArrayList<>();
		
		// 댓글 목록
		List<PostComment> postCommentList = getPostCommentListByPostId(postId);
		for(PostComment postComment : postCommentList) {
			PostCommentView postCommentView = new PostCommentView();
			
			// 댓글
			postCommentView.setPostComment(postComment);
			
			// 댓글쓴이
			User user = userBO.getUserByUserId(postComment.getUserId());
			postCommentView.setUser(user);
			
			// 넣기
			postCommentViewList.add(postCommentView);
		}
		
		// 결과 리턴
		return postCommentViewList;
	}
	
	// 댓글 insert
	public int addPostCommentByUserIdPostId(int userId, int postId, String content) {
		return postCommentDAO.insertPostCommentByUserIdPostId(userId, postId, content);
	}
	
	// 해당 댓글 삭제하기
	public void deletePostCommentByUserIdPostIdCommentId(int userId, int postId, int postCommentId) {
		postCommentDAO.deletePostCommentByUserIdPostIdCommentId(userId, postId, postCommentId);
	}
	
	// 해당 댓글 조회
	public int getPostCommentByUserIdPostIdCommentId(int userId, int postId, int postCommentId) {
		return postCommentDAO.selectPostCommentByUserIdPostIdCommentId(userId, postId, postCommentId);
	}
	
	// 해당 댓글 삭제 가공
	public boolean generateDeleteComment(int userId, int postId, int postCommentId) {
		int row = getPostCommentByUserIdPostIdCommentId(userId, postId, postCommentId);
		if(row > 0) {
			// 조회할 경우 있으면 삭제
			deletePostCommentByUserIdPostIdCommentId(userId, postId, postCommentId);
			return true;
		} else {
			return false;
		}
	}
	
	
}
