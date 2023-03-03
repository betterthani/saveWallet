<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div>
	<c:forEach var="shoppingDetail" items="${sProductList}">
	<div class="d-flex w-100 my-3 mx-3">
		<!-- 사진 첨부 & 제품명란 -->
		<div class="col-5 product-list-write-left">
		
			<!-- 사진 첨부 -->
			<div class="product-list-write-pic">
				<div class="shopping-img-place">
					<img alt="필수항목 이미지" src="${shoppingDetail.product.productImgPath}" class="shopping-img-place w-100" id="shopping-img-edit">
				</div>
				<div class="mb-3">
				  <input class="form-control" type="file" id="formFile-shopping-edit" accept=".jpg, .png, .jpeg, .gif">
				</div>
			</div>
			
			<!-- 제품명란 -->
			<div class="product-list-detail-title">
				<div class="input-group mb-3 mt-2">
					<span class="input-group-text" id="basic-addon1">제품명</span> 
					<input type="text" class="form-control" id="shopping-detail-itemName" value="${shoppingDetail.product.itemName}" placeholder="제품명 입력해주세요. (필수)">
				</div>
			</div>
			
			
		</div>
	
		<!-- 나머지 정보들 -->
		<div class="col-7 product-list-write-right ">
			<input type="text" class="form-control w-50 mt-2" value="SHOPPING" id="shopping-type" disabled>
			
			
			<%-- 카테고리 --%>
			<select class="form-control w-50 mt-3" id="category-shopping-detail">
			  <option selected value="">카테고리</option>
			  <option value="APPLIANCES" ${shoppingDetail.product.category == "APPLIANCES" ? 'selected' : ''}>가전</option>
			  <option value="CLOTHING" ${shoppingDetail.product.category == "CLOTHING" ? 'selected' : ''}>의류</option>
			  <option value="GOODS" ${shoppingDetail.product.category == "GOODS" ? 'selected' : ''}>잡화</option>
			  <option value="COSMETICS" ${shoppingDetail.product.category == "COSMETICS" ? 'selected' : ''}>화장품</option>
			  <option value="ET" ${shoppingDetail.product.category == "ET" ? 'selected' : ''}>그 외</option>
			</select>
			
			<%-- 금액 --%>
			<input type="text" class="form-control w-50 mt-3"id="shopping-detail-amount" maxlength="10" value="${shoppingDetail.product.amount}" placeholder="금액을 입력하세요. (필수)">
			
			<%-- 구매처 --%>
			<select class="form-control w-50 mt-3" id="purchasedCategory-detail"  disabled>
			  <option selected value="">구매처</option>
			  <option value="ONLINE" ${shoppingDetail.product.purchasedCategory == "ONLINE" ? 'selected' : ''}>온라인</option>
			  <option value="OFFLINE" ${shoppingDetail.product.purchasedCategory == "OFFLINE" ? 'selected' : ''}>오프라인</option>
			</select>
			<%-- 온라인일 경우 구매처 입력--%>
			<input type="text" class="form-control w-50 mt-2 d-none" id="shopping-detail-purchased" placeholder="온라인 구매처를 입력하세요. (필수)" value="${shoppingDetail.product.purchased}" disabled>
			
			<%-- 오프라인일 경우 구매처 입력 --%>
			<div class="form-control w-50 mt-2 d-none" id="shopping-offline-detail-purchased">${shoppingDetail.product.purchased}</div>
			
			<div class="map_wrap d-none" id="kakaMap-detail">
			    <div id="map" style="width:100%; height:100%; position:relative; overflow:hidden;"></div>
			
			    <div id="menu_wrap" class="bg_white">
			        <div class="option">
			            <div>
							<input type="text" id="keyword-map" size="25" placeholder="키워드(ex.올리브영)" value="${shoppingDetail.product.purchased}" disabled>
			            	<button type="button" id="keyword-detail-map-search">검색하기</button> 
			            </div>
			        </div>
			        <hr>
			        <ul id="placesList"></ul>
			        <div id="pagination"></div>
			    </div>
			</div>
			
			<%-- 사이즈 --%>
			<input type="text" class="form-control w-50 mt-3" value="${shoppingDetail.product.size}" id="shopping-detail-size" placeholder="사이즈를 입력하세요.">
			
			<%-- 컬러 --%>
			<input type="text" class="form-control w-50 mt-3" value="${shoppingDetail.product.color}" id="shopping-detail-color" placeholder="색상을 입력하세요.">
			
			<%-- 구매일 --%>
			
			<input type="text" id="shopping-detail-datepicker" value="<fmt:formatDate value="${shoppingDetail.product.datePurchased}" pattern ="yyyy-MM-dd"/>" class="form-control w-50 mt-3" placeholder="구매일을 선택하세요. (필수)">
			
			<%-- 반품가능일 --%>
			<input type="text" id="shopping-detail-returnn-datepicker" value="<fmt:formatDate value="${shoppingDetail.product.returnableDeadline}" pattern ="yyyy-MM-dd"/>" class="form-control w-50 mt-3" placeholder="반품가능 기한을 선택하세요.">
			
			<%-- 당근희망여부 --%>
			<select class="form-control w-50 mt-3" id="shopping-detail-usedHope">
			  <option selected value="">당근 희망여부 (필수)</option>
			  <option value="1" ${shoppingDetail.product.usedHope == "true" ? 'selected' : ''}>여</option>
			  <option value="0" ${shoppingDetail.product.usedHope == "false" ? 'selected' : ''}>부</option>
			</select>
			
			<%-- 수정, 삭제 버튼 --%>
			<div class="d-flex justify-content-center mr-5">
				<button type="button" class="btn btn-info mt-3" id="shopping-detail-editBtn" data-product-id="${shoppingDetail.product.id}">수정</button>
				<button type="button" class="btn btn-dark mt-3 ml-3" id="shopping-detail-delBtn" data-user-id="${userId}" data-product-id="${shoppingDetail.product.id}">삭제</button>
			</div>
		
		</div>
	
	</div>
	<div class="mx-3 my-3">
		<h5 class="text-info">COMMENT</h5>
						
		<%-- 댓글 내용 --%>
		<c:forEach var="sComment" items="${shoppingDetail.shopppingCommentList}">
			<div class="pb-2">
				<span class="font-weight-bold" id="shopping-detail-nickName">${userLogindId }</span>
				<span id="shopping-detail-comment">${sComment.content}</span>
				<%-- 댓글 삭제 버튼 --%>
				<i class="fa fa-minus-square text-dark btn commentDelBtn" data-comment-id="${sComment.id}" data-product-id="${shoppingDetail.product.id}"></i>
			</div>
		</c:forEach>
		
		<%-- 댓글 쓰기 --%>
		<div class="d-flex">
			<input type="text" id="shopping-comment-content" class="form-control w-50" placeholder="Please leave a comment.">
			<button type="button" id="shopping-comment-uploadBtn" class="btn btn-success" data-product-id="${shoppingDetail.product.id}">댓글</button>
		</div>
	</div>
	</c:forEach>
</div>