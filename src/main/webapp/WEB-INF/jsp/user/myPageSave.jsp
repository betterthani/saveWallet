<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<div class="d-flex my-4 mx-3">
		<%-- 프로필 사진 --%>
		<c:choose>
			<c:when test="${empty user.profileImgPath }">
				<img alt="이미지" src="/static/img/profileImg.png" class="roundBox">
			</c:when>
			<c:otherwise>
				<img alt="이미지" src="${user.profileImgPath }" class="roundBox">
			</c:otherwise>
		</c:choose>
		<div class="mypageBox mx-3">
			<%-- 닉네임 --%>
			<h4 class="">${user.nickName } 저장한 글</h4>
			
			<%-- 상태메세지 --%>
			<div class="">${user.statusMessage }</div>
		</div>
	</div>

	<div class="mypage-save-box mb-4">
		
		<%-- 내가 쓴글 타임라인 --%>
		<div class="d-flex d-flex flex-wrap py-4 px-4">
			<c:forEach var="cardView" items="${cardViewList}">
				<c:forEach var="save" items="${cardView.saveList}">
					<c:if test="${save.postId eq cardView.post.id}">
						<a href="/post/timeline_detail_view?userId=${cardView.post.userId }&postId=${cardView.post.id }"><img alt="타임라인" src="${cardView.postImageList[0].postImgPath }" class="imgbox mx-2"></a>
					</c:if>
				</c:forEach>
			</c:forEach>
		</div>
	</div>
</div>