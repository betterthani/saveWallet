<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="d-flex w-100 my-3 mx-3">
	<!-- 사진 첨부 & 제품명란 -->
	<div class="col-5 product-list-write-left">
	
		<!-- 사진 첨부 -->
		<div class="product-list-write-pic">
			<div class="picture-box">
				사진들어갈 곳
			</div>
			<div class="mb-3">
			  <input class="form-control" type="file" id="formFile">
			</div>
		</div>
		
		<!-- 제품명란 -->
		<div class="product-list-write-title">
			<div class="input-group mb-3 mt-2">
				<span class="input-group-text" id="basic-addon1">제품명</span> 
				<input type="text" class="form-control">
			</div>
		</div>
		
		
	</div>

	<!-- 나머지 정보들 -->
	<div class="col-7 product-list-write-right ">
		<input type="text" class="form-control w-50 mt-2" value="SHOPPING" disabled>
		
		<select class="form-control w-50 mt-3" id="category">
		  <option selected value="">카테고리</option>
		  <option value="APPLIANCES">가전</option>
		  <option value="CLOTHING">의류</option>
		  <option value="GOODS">잡화</option>
		  <option value="COSMETICS">화장품</option>
		  <option value="ET">그 외</option>
		</select>
		
		<input type="text" class="form-control w-50 mt-3" placeholder="금액을 입력하시오.">
		
		<select class="form-control w-50 mt-3" id="purchasedCategory">
		  <option selected value="">구매처</option>
		  <option value="ONLINE">온라인</option>
		  <option value="OFFLINE">오프라인</option>
		</select>
		
		<input type="text" class="form-control w-50 mt-3" placeholder="사이즈를 입력하시오.">
		
		<input type="text" id="datePurchased-datepicker" class="form-control w-50 mt-3">
		
		<input type="text" id="returnableDeadline-datepicker" class="form-control w-50 mt-3">
		
		<select class="form-control w-50 mt-3" id="purchasedCategory">
		  <option selected value="">당근 희망여부</option>
		  <option value="1">여</option>
		  <option value="0">부</option>
		</select>
		<div class="d-flex justify-content-center mr-5">
			<button type="button" class="btn btn-success mt-3">업로드</button>
		</div>
	
	</div>

</div>