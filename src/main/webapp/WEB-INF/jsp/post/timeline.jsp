<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2 class="my-3 bg-success text-center text-white">How about this?</h2>
<div class="d-flex justify-content-center my-3">
	<div class="contents-box">
		<%-- 글쓰기 영역 : 로그인 된 상태에서만 보여짐 --%>
		<c:if test="${not empty userId}">
		<div class="d-flex justify-content-end my-2">
			<a href="/post/write_view" class="text-dark"><i class="fa fa-upload text-secondary btn fa-lg"> 글 올리기</i></a>
		</div>
		</c:if>
		<%--// 글쓰기 영역 끝 --%>

		<%-- 타임라인 영역 --%>
		<c:forEach var="cardView" items="${cardviewList}">
			<div class="timeline-box">
				<%-- 카드1 --%>
				<div class="card border rounded mt-3">
					<%-- 글쓴이, 더보기(삭제) --%>
					<div class="p-2 d-flex justify-content-between">
						<span class="font-weight-bold"><a href="" class="individualBtn text-dark">${cardView.user.loginId }</a></span>
	
						<%-- 더보기(내가 쓴 글일 떄만 노출) --%>
						<a href="#" class="more-btn" data-toggle="modal" data-target="#modal" data-post-id=${cardView.post.id }>
							<img src="https://www.iconninja.com/files/860/824/939/more-icon.png" width="30">
						</a>
					</div>
	
					<%-- 카드 이미지 --%>
					<div class="full-size-box">
						<ul class="slider">
							<c:forEach var="postImage" items="${cardView.postImageList }">
								<li><img src="${postImage.postImgPath }" class="w-100 time-img-size" alt="본문 이미지"></li>
							</c:forEach>
						</ul>
					</div>
					
					<%-- 저장하기 --%>
					<div class="d-flex justify-content-end align-items-center">					
						<%-- 저장하기 기능 --%>
						<div>
						
							<a href="#" class="save-post-btn" data-user-id="${userId }" data-post-id="${cardView.post.id }">
								<c:choose>
									<c:when test="${cardView.filledSave eq true}">
										<%-- 저장하기 눌렀을때 --%>
										<i class="fa fa-bookmark text-dark btn fa-2x"></i>
									</c:when>
									
									<c:otherwise>
										<i class="fa fa-bookmark-o text-dark btn fa-2x"></i>
									</c:otherwise>
								</c:choose>
							</a>
							
						</div>
					</div>
					
					<%-- 글 --%>
					<div class="card-post m-3">
						<h4 class="font-weight-bold">${cardView.post.title }</h4>
						<span>${cardView.post.subject }</span>
					</div>
	
					<%-- 댓글 --%>
					<div class="card-comment-desc border-bottom">
						<div class="ml-3 mb-1 font-weight-bold">댓글</div>
					</div>
	
					<%-- 댓글 목록 --%>
					<div class="card-comment-list m-2">
					
					<%-- 댓글 내용 --%>
					<c:forEach var="postComment" items="${cardView.postCommentList }">
						<div class="card-comment m-1">
								<span class="font-weight-bold">${postComment.user.loginId }:</span>
								<span>${postComment.postComment.content }</span>
							
								<%-- 댓글 삭제 버튼 --%>
								<a href="#" class="commentDelBtn">
									<img src="https://www.iconninja.com/files/603/22/506/x-icon.png" width="10px" height="10px">
								</a>
						</div>
					</c:forEach>
						<%-- 댓글 쓰기 --%>
							<div class="comment-write d-flex border-top mt-2">
								<input type="text" class="form-control border-0 mr-2" placeholder="댓글 달기"> 
								<button type="button" class="comment-btn btn btn-light" data-post-id="${cardView.post.id }">게시</button>
							</div>
					</div>
					<%--// 댓글 목록 끝 --%>
				</div>
				<%--// 카드1 끝 --%>
			</div>
			</c:forEach>
		<%--// 타임라인 영역 끝  --%>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="modal">
	<%--modal-sm : 작은 모달 창 --%>
	<%--modal-dialog-centered: 모달 창 수직으로 가운데 정렬 --%>
  <div class="modal-dialog modal-sm modal-dialog-centered">
    <div class="modal-content text-center">
    	<div class="py-3 border-bottom">
    		<a href="#" id="deletePostBtn">삭제하기</a>
    	</div>
    	<div class="py-3">
    		<%-- data-dismiss="modal" 모달 창 닫힘 --%>
    		<a href="#" data-dismiss="modal">취소하기</a>
    	</div>
    </div>
  </div>
</div>
<script>
$(document).ready(function(){
  $('.slider').bxSlider();
});
</script>
