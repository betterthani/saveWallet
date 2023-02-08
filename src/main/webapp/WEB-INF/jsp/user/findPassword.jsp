<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	
	<div class="login-box py-5">
	
		<h3 class="mb-4 mt-5">비밀번호 찾기</h3>
		<input type="text" class="form-control my-4" id="find-password-loginId" placeholder="아이디를 입력해주세요.">

		<input type="text" class="form-control my-4" id="find-password-email" placeholder="이메일 주소를 입력해주세요.">

		<%-- 비밀번호 찾기 버튼 --%>
		<%-- btn-block: 로그인 박스 영역에 버튼을 가득 채운다. --%>
		<input type="button" class="btn btn-block btn-primary find-password-btn" value="비밀번호 찾기"  data-toggle="find-password-modal" data-target="#find-password-modal">

	</div>
</div>
