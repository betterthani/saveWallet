<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="d-flex justify-content-center w-100 my-5">
	<div class="edit-box">
		<!-- 이미지 박스 -->
		<div class="d-flex justify-content-center">
			<c:choose>
				<c:when test="${empty user.profileImgPath }">
					<img alt="프로필 사진 없을때" src="/static/img/profileImg.png" class="profileBox">
				</c:when>
				<c:otherwise>
					<img alt="프로필 사진" src="${user.profileImgPath }" class="profileBox">
				</c:otherwise>
			</c:choose>
		</div>
		
		<!-- 파일 선택 버튼 -->
		<div class="d-flex justify-content-end">
			<input type="file" id="file" class="d-none" accept=".gif, .jpg, .png, .jpeg">
			<button class="btn-sm pictureBtn">사진 선택</button>
		</div>
		
		<!-- input박스 -->
		<div class="d-flex justify-content-center">
			<div class="w-75 my-3">
				<input type="text" class="form-control nickName" placeholder="닉네임을 입력해주세요." value="${user.nickName }">
				<input type="text" class="form-control mt-3 statusMessage" maxlength="30" placeholder="상태메세지를 입력해주세요. (30자 이내)" value="${user.statusMessage }">
				<input type="password" class="form-control mt-3 password" placeholder="기존 비밀번호를 입력해주세요.">
				<input type="password" class="form-control mt-3 change-password d-none" placeholder="변경할 비밀번호를 입력해주세요.">
				<small id="warning-length-text" class="d-none font-weight-bold">숫자/영문/특수문자를 모두 포함해야 하며,<br>최소 8자리 이상입니다.</small><br>
			</div>
		</div>
		
		
		<!-- 버튼 -->
		<div class="buttonBox my-2">
				<button type="button" class="btn btn-primary w-75" id="profileEditBtn">프로필 수정</button>
				<button type="button" class="btn-secondary w-75 my-2" id="passwordEditBtn">비밀번호 수정</button>
				<button type="button"  class="btn btn w-75" id="exitUserBtn">회원탈퇴</button>
		</div>
	</div>
</div>