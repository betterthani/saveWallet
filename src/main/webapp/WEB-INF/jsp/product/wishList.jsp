<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div>

	<div class="my-3">
		<table class="table text-center" border="1" id="table-mini-box">
			<tr>
				<th>지출 가능 금액</th>
				<td>지출금액</td>
			</tr>
			<tr>
				<th>희망 물품 총금액</th>
				<td>지출금액</td>
			</tr>
			<tr>
				<th>차액</th>
				<td>지출금액</td>
			</tr>
		</table>
	</div>
	
	<div class="my-4">
		<h5>최대 5개까지 입력이 가능합니다.</h5>
		<table class="table text-center" id="wish-content-table">
			<thead>
				<tr>
					<th width="50px">
						<label>
							<input type="checkbox" name="select" id="allcheck">
						</label>
					</th>
					<th>물품명</th>
					<th>카테고리</th>
					<th>금액</th>
					<th>구매처</th>
				</tr>
			</thead>
			
			<tbody>
				<tr>
					<td>
						<label>
							<input type="checkbox" name="select">
						</label>
					</td>
					<td>d</td>
					<td>d</td>
					<td>d</td>
					<td>d</td>
				</tr>
			</tbody>
		</table>
		
	</div>
	
	<div class="d-flex justify-content-end">
		<a href=""><input type="button" class="btn btn-success mr-3" value="올리기"></a>
		<input type="button" class="btn btn-dark" value="삭제">
	</div>

</div>