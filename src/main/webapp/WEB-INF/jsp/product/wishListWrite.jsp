<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<input type="text" class="form-control w-50 mt-2" value="WISH" disabled>
		
		<%-- 카테고리 --%>
		<select class="form-control w-50 mt-3" id="shopping-category">
		  <option selected value="">카테고리 (필수)</option>
		  <option value="APPLIANCES">가전</option>
		  <option value="CLOTHING">의류</option>
		  <option value="GOODS">잡화</option>
		  <option value="COSMETICS">화장품</option>
		  <option value="ET">그 외</option>
		</select>
		
		<%-- 금액 --%>
		<input type="number" class="form-control w-50 mt-3" id="shopping-amount" maxlength="10" placeholder="금액을 입력하세요. (필수)">
		
		<%-- 구매처 --%>
		<select class="form-control w-50 mt-3" id="shopping-purchasedCategory">
		  <option selected value="">구매처 (필수)</option>
		  <option value="ONLINE">온라인</option>
		  <option value="OFFLINE">오프라인</option>
		</select>
		<%-- 온라인일 경우 구매처 입력--%>
		<input type="text" class="form-control w-50 mt-2 d-none" id="shopping-purchased" placeholder="온라인 구매처를 입력하세요. (필수)">
		
		<%-- 오프라인일 경우 구매처 입력 --%>
		<div class="form-control w-50 mt-2 d-none" id="shopping-offline-purchased" placeholder="오프라인 구매처를 입력하세요. (필수)">${purchased}</div>
		
		<div class="map_wrap d-none" id="kakaMap">
		    <div id="map" style="width:100%; height:100%; position:relative; overflow:hidden;"></div>
		
		    <div id="menu_wrap" class="bg_white">
		        <div class="option">
		            <div>
						<input type="text" id="keyword-map" size="25" placeholder="키워드(ex.올리브영)"> 
		            	<button type="button" id="keyword-map-search">검색하기</button> 
		            </div>
		        </div>
		        <hr>
		        <ul id="placesList"></ul>
		        <div id="pagination"></div>
		    </div>
		</div>
		
		<%-- 사이즈 --%>
		<input type="text" class="form-control w-50 mt-3" id="shopping-size" placeholder="사이즈를 입력하세요.">
		
		<%-- 컬러 --%>
		<input type="text" class="form-control w-50 mt-3" id="shopping-color" placeholder="색상을 입력하세요.">
		
		<%-- 업로드 버튼 --%>
		<div class="d-flex justify-content-center mr-5">
			<button type="button" class="btn btn-success mt-3 " id="wishList-upload-btn">업로드</button>
		</div>
	
	</div>

</div>
    