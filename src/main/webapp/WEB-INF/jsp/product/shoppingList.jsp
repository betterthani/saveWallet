<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${not empty userId}">
<h3 class="my-3 text-secondary">SHOPPING LIST</h3>
<div class="my-5">
	<!-- 윗부분 -->
	<div class="product-list-up d-flex align-items-center justify-content-between">
		<!-- 검색 -->
		<div class="input-group col-3">
			<!-- 돋보기 아이콘 -->
			<div class="input-group-prepend">
				<span class="input-group-text"><i class="fa fa-search"></i></span>
			</div>
			<!-- 검색창 -->
			<input type="text" class="form-control" id="keyword-search-text" value="${keyword}" placeholder="제품명 검색">
			<input type="button" class="btn btn-success" id="keyword-search-btn" value="검색">
		</div>
		
		<!-- 정렬부분 -->
		<div class="d-flex">
			<label>카테고리순<input type="radio" name="sort" value="category-order" class="mx-3"></label>
			<label>금액순<input type="radio" name="sort" value="amount-order" class="mx-3"></label>
			<label>반품가능일순<input type="radio" name="sort" value="return-order" class="mx-3"></label>
			<label>당근 희망여부<input type="radio" name="sort" value="used-order" class="mx-3"></label>
		</div>
		
	</div>

	<!-- 아래부분 -->
	<div class="product-list-down">
		<table class="table">
			<thead>
				<tr class="text-center">
					<th>
						<label>
							<input type="checkbox" name="select" id="allcheck">
						</label>
					</th>
					<th>번호</th>
					<th>제품명</th>
					<th>카테고리</th>
					<th>구매일</th>
					<th>반품 가능일</th>
					<th>금액</th>
					<th>당근 희망여부</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="shoppingProduct" items="${productList}" varStatus="status">
				<tr class="text-center">
					<td>
						<label>
							<input type="checkbox" name="select" class="checkBox-list" data-product-id="${shoppingProduct.id}">
						</label>
					</td>
					
					<td>
						${shoppingProduct.id}
					</td>
					
					<td><a href="/product/shopping_list_write/detail_view?productId=${shoppingProduct.id}" class="text-dark">${shoppingProduct.itemName}</a></td>
					<td><a href="/product/shopping_list_write/detail_view?productId=${shoppingProduct.id}" class="text-dark">${shoppingProduct.category}</a></td>
					
					<td>
						<fmt:formatDate value="${shoppingProduct.datePurchased}" pattern ="yyyy-MM-dd"/>
					</td>
					
					<td>
						<fmt:formatDate value="${shoppingProduct.returnableDeadline}" pattern ="yyyy-MM-dd"/>
					</td>
					
					<td>
						<fmt:formatNumber value="${shoppingProduct.amount}" />
					
					</td>
					
					<td>
						<c:choose>
							<c:when test="${shoppingProduct.usedHope}">
								<p>여</p>
							</c:when>
							<c:otherwise>
								<p>부</p>
							</c:otherwise>
						</c:choose>
					</td>
					
					
				</tr>
			</c:forEach>
			</tbody>
			
		</table>
		
		<div class="d-flex align-items-center justify-content-end">
			
			<a href="/product/shopping_list_write_view"><input type="button" class="btn btn-succeess create-btn mx-2" value="글작성"></a>
			<input type="button" class="btn btn-secondary mx-2" id="delete-btn" value="삭제">
		</div>
		
		<%-- 페이징 --%>
		<div class="d-flex justify-content-center">

			<c:if test="${prevId ne 0}">
				<a href="/product/shopping_list_view?prevId=${prevId}" class="mr-5 text-scuccess">&lt;&lt;이전</a>
			</c:if>

			<c:if test="${nextId ne 0}">
				<a href="/product/shopping_list_view?nextId=${nextId}" class="text-scuccess">다음&gt;&gt;</a>
			</c:if>

		</div>
	</div>
</div>
</c:if>
