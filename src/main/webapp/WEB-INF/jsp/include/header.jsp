<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 상호명 --%>
<h1 id="logo">
<a href="/post/timeline_view">
	<span>Save Wallet</span>
</a>

</h1>
<div class="pr-4">
	<%-- 로그인 --%>
	<c:if test="${empty userId}">
		<a href="/user/sign_in_view">로그인</a>
	</c:if>
	
	<%-- 로그아웃, 개인정보 --%>
	<c:if test="${not empty userId}">
		<div class="mr-3">
			<span><a href="/user/mypage_view?userId=${userId}" class="text-dark font-weight-bold">${userNickName}</a>님 안녕하세요.</span> 
			<a href="/user/sign_out" class="ml-2 font-weight-bold text-success">로그아웃</a>
		</div>
	</c:if>
</div>