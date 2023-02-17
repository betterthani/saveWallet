<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="d-flex justify-content-center my-3">
	<div class="contents-box">

		<%-- 타임라인 영역 --%>
			<div class="timeline-box">
				<%-- 카드1 --%>
				<div class="card border rounded mt-3">
					<%-- 글쓴이, 더보기(삭제) --%>
					<div class="p-2 d-flex justify-content-between">
						<span class="font-weight-bold"><a href="" class="text-dark">${cardViewList.user.loginId }</a></span>
	
						<%-- 수정 완료 버튼 --%>
						<button type="button" class="btn" id="post-edit-btn" data-post-id ="${cardViewList.post.id }">완료</button>
					</div>
	
					<%-- 카드 이미지 --%>
					<div class="full-size-box">
						<ul class="slider">
							<li><img src="${image}" class="w-100 time-img-size" alt="본문 이미지" disabled></li>
							<c:if test="${not empty image1}">
							<li><img src="${image1}" class="w-100 time-img-size" alt="본문 이미지" disabled></li>
							</c:if>
							<c:if test="${not empty image2}">
							<li><img src="${image2}" class="w-100 time-img-size" alt="본문 이미지" disabled></li>
							</c:if>
						</ul>
					</div>
					
					<%-- 글 --%>
					<div class="card-post m-3">
						<input class="font-weight-bold form-control w-100 post-edit-title" value="${cardViewList.post.title }">
						<input class="form-control w-100 post-edit-subject" value="${cardViewList.post.subject }">
					</div>
				</div>
				<%--// 카드1 끝 --%>
			</div>
		<%--// 타임라인 영역 끝  --%>
	</div>
</div>
<script>
$(document).ready(function(){
  $('.slider').bxSlider();
});
</script>