<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="d-flex justify-content-center">
	<div class="login-box py-5">
		<h3 class="mb-4 mt-5">로그인</h3>
		
			<div class="input-group mb-3">
				<%-- input-group-prepend: input box 앞에 ID 부분을 회색으로 붙인다. --%>
				<div class="input-group-prepend">
					<span class="input-group-text"><i class="fa fa-user"></i></span>
				</div>
				<input type="text" class="form-control" id="loginId" name="loginId">
			</div>
	
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text"><i class="fa fa-lock"></i></span>
				</div>
				<input type="password" class="form-control" id="password" name="password">
			</div>
			
			
			<%-- 로그인 버튼 --%>
			<%-- btn-block: 로그인 박스 영역에 버튼을 가득 채운다. --%>
			<input type="button" class="btn btn-block btn-primary loginBtn" value="로그인">
			
			<%-- 비밀번호 찾기 --%>
			<div class="d-flex justify-content-center pt-3">
				<a href="/user/find_password_view">비밀번호를 잊으셨나요?</a>
			</div>
			
			<%-- 회원가입 링크 --%>
			<div class="d-flex justify-content-center pt-2">
				<div>계정이 없으신가요?</div>		
				<a href="/user/sign_up_view" class="pl-3">회원가입</a>
			</div>
			
	</div>
</div>
