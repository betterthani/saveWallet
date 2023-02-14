<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div>
<h3 class="my-3 text-secondary">WISH LIST</h3>

	<div class="my-3">
		<table class="table text-center" border="1" id="table-mini-box">
			<tr>
				<th>지출 가능 금액</th>
				<td>
					<div id="wish-remainAmount"><fmt:formatNumber value="${amounInfo.remainingAmount }"/></div>
				</td>
			</tr>
			<tr>
				<th>희망 물품 총금액</th>
				<td>
					<div id="wish-amountSum"><fmt:formatNumber value="${wishAmountSum }"/></div>
				</td>
			</tr>
			<tr>
				<th class="bg-warning">차액</th>
				<td class="bg-warning">
					<div id="wish-difference-amount" class="font-weight-bold"><fmt:formatNumber value="${amounInfo.remainingAmount - wishAmountSum }"/></div>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="my-4">
		<div>* 최대 5개까지 입력이 가능합니다.</div>
		<table class="table text-center" id="wish-content-table">
			<thead>
				<tr>
					<th width="50px">
						<label>
							<input type="checkbox" name="select-wish" id="allcheck-wish">
						</label>
					</th>
					<th>물품명</th>
					<th>카테고리</th>
					<th>금액</th>
					<th>구매처</th>
				</tr>
			</thead>
			
			<tbody>
			<c:forEach var="wish" items="${wishProductList }">
				<tr>
					<td>
						<label>
							<input type="checkbox" name="select-wish" data-product-id="${wish.id}">
						</label>
					</td>
					<td>
						<a href="/product/wish_list_write/detail_view?productId=${wish.id }" class="text-dark">${wish.itemName }</a>
					</td>
					<td>
						<a href="/product/wish_list_write/detail_view?productId=${wish.id }" class="text-dark">${wish.category }</a>
					</td>
					<td>
						<fmt:formatNumber value="${wish.amount }" />
					</td>
					<td>${wish.purchased }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		
	</div>
	
	<div class="d-flex justify-content-end">
		<a href="/product/wish_write_view"><input type="button" class="btn btn-success mr-3" value="올리기"></a>
		<input type="button" class="btn btn-dark" value="삭제" id="wish-del-btn">
	</div>

</div>