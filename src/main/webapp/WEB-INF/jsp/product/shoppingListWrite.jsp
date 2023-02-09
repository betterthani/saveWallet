<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="d-flex w-100 my-3 mx-3">
	<!-- 사진 첨부 & 제품명란 -->
	<div class="col-5 product-list-write-left">
	
		<!-- 사진 첨부 -->
		<div class="product-list-write-pic">
			<div class="shopping-img-place">
				<img alt="필수항목 이미지" src="" class="shopping-img-place w-100">
			</div>
			<div class="mb-3">
			  <input class="form-control" type="file" id="formFile-shopping-list" accept=".jpg, .png, .jpeg, .gif" >
			</div>
		</div>
		
		<!-- 제품명란 -->
		<div class="product-list-write-title">
			<div class="input-group mb-3 mt-2 ">
				<span class="input-group-text" id="basic-addon1">제품명</span> 
				<input type="text" class="form-control" id="shopping-itemName" placeholder="제품명 입력해주세요. (필수)">
			</div>
		</div>
		
		
	</div>

	<!-- 나머지 정보들 -->
	<div class="col-7 product-list-write-right ">
	
		<%-- 타입 고정값 : shopping --%>
		<input type="text" class="form-control w-50 mt-2" value="SHOPPING" disabled>
		
		<%-- 카테고리 --%>
		<select class="form-control w-50 mt-3" id="shopping-category">
		  <option selected value="">카테고리 (필수)</option>
		  <option value="appliances">가전</option>
		  <option value="clothing">의류</option>
		  <option value="goods">잡화</option>
		  <option value="cosmetics">화장품</option>
		  <option value="et">그 외</option>
		</select>
		
		<%-- 금액 --%>
		<input type="number" class="form-control w-50 mt-3" id="shopping-amount" maxlength="10" placeholder="금액을 입력하세요. (필수)">
		
		<%-- 구매처 --%>
		<select class="form-control w-50 mt-3" id="shopping-purchasedCategory">
		  <option selected value="">구매처 (필수)</option>
		  <option value="online">온라인</option>
		  <option value="offline">오프라인</option>
		</select>
		<input type="text" class="form-control w-50 mt-2 d-none" id="shopping-purchased" placeholder="구매처를 입력하세요. (필수)">
		
		<%-- 사이즈 --%>
		<input type="text" class="form-control w-50 mt-3" id="shopping-size" placeholder="사이즈를 입력하세요.">
		
		<%-- 구매일 --%>
		<input type="text" id="shopping-date-datepicker" class="form-control w-50 mt-3" placeholder="구매일을 선택하세요. (필수)">
		
		<%-- 반품가능일 --%>
		<input type="text" id="shopping-returnn-datepicker" class="form-control w-50 mt-3" placeholder="반품가능 기한을 선택하세요.">
		
		<%-- 당근희망여부 --%>
		<select class="form-control w-50 mt-3" id="shopping-usedHope">
		  <option selected value="">당근 희망여부 (필수)</option>
		  <option value="1">여</option>
		  <option value="0">부</option>
		</select>
		
		<%-- 업로드 버튼 --%>
		<div class="d-flex justify-content-center mr-5">
			<button type="button" class="btn btn-success mt-3 " id="shoppingList-upload-btn">업로드</button>
		</div>
	
	</div>

</div>