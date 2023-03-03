<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="d-flex w-100 my-3 mx-3">
	<!-- 사진 첨부 & 제품명란 -->
	<div class="col-5 product-list-write-left">
	
		<!-- 사진 첨부 -->
		<div class="product-list-write-picDetatil">
			<div class="shopping-img-place">
				<img alt="필수항목 이미지" src="${wishProduct.productImgPath }" class="shopping-img-place w-100">
			</div>
		</div>
		
		<!-- 제품명란 -->
		<div class="product-list-write-title">
			<div class="input-group mb-3 mt-2 ">
				<span class="input-group-text" id="basic-addon1">제품명</span> 
				<div class="form-control" id="shopping-itemName">${wishProduct.itemName }</div>
			</div>
		</div>
		
		
	</div>

	<!-- 나머지 정보들 -->
	<div class="col-7 product-list-write-right ">
	
		<%-- 타입 고정값 : shopping --%>
		<input type="text" class="form-control w-50 mt-2" value="WISH" disabled>
		
		<%-- 카테고리 --%>
		<select class="form-control w-50 mt-3" id="shopping-category" disabled>
		  <option selected value="">카테고리</option>
		  <option value="APPLIANCES" ${wishProduct.category == "APPLIANCES" ? 'selected' : ''}>가전</option>
		  <option value="CLOTHING" ${wishProduct.category == "CLOTHING" ? 'selected' : ''}>의류</option>
		  <option value="GOODS" ${wishProduct.category == "GOODS" ? 'selected' : ''}>잡화</option>
		  <option value="COSMETICS" ${wishProduct.category == "COSMETICS" ? 'selected' : ''}>화장품</option>
		  <option value="ET" ${wishProduct.category == "ET" ? 'selected' : ''}>그 외</option>
		</select>
		
		<%-- 금액 --%>
		<div class="form-control w-50 mt-3" id="shopping-amount" maxlength="10"><fmt:formatNumber value="${wishProduct.amount }"/></div>
		
		<%-- 구매처 --%>
		<select class="form-control w-50 mt-3" id="purchasedCategory-detail-wish" disabled>
		  <option selected value="">구매처</option>
		  <option value="ONLINE" ${wishProduct.purchasedCategory == "ONLINE" ? 'selected' : ''}>온라인</option>
		  <option value="OFFLINE" ${wishProduct.purchasedCategory == "OFFLINE" ? 'selected' : ''}>오프라인</option>
		</select>
		<%-- 온라인일 경우 구매처 입력--%>
		<div class="form-control w-50 mt-2 d-none" id="wish-detail-purchased">${wishProduct.purchased }</div>
		
		<%-- 오프라인일 경우 구매처 입력 --%>
		<div class="form-control w-50 mt-2 d-none" id="wish-offline-detail-purchased">${wishProduct.purchased }</div>
		
		<div class="map_wrap d-none" id="kakaMap-wishdetail">
		    <div id="map" style="width:100%; height:100%; position:relative; overflow:hidden;"></div>
		
		    <div id="menu_wrap" class="bg_white">
		        <div class="option">
		            <div>
						<input type="text" id="keyword-map" size="25" placeholder="키워드(ex.올리브영)" value="${wishProduct.purchased }" disabled> 
		            	<button type="button" id="keyword-detail-wish-search">검색하기</button> 
		            </div>
		        </div>
		        <hr>
		        <ul id="placesList"></ul>
		        <div id="pagination"></div>
		    </div>
		</div>
		
		<%-- 사이즈 --%>
		<input type="text" class="form-control w-50 mt-3" id="shopping-size" placeholder="사이즈를 입력하세요." value="${wishProduct.size }" disabled>
		
		<%-- 컬러 --%>
		<input type="text" class="form-control w-50 mt-3" id="shopping-color" placeholder="색상을 입력하세요." value="${wishProduct.color }" disabled>
		
		<%-- 업로드 버튼 --%>
		<div class="d-flex justify-content-center mr-5">
			<button type="button" class="btn btn-dark mt-3 " id="wishList-detail-del-btn" data-product-id="${wishProduct.id }">삭제</button>
		</div>
	
	</div>

</div>