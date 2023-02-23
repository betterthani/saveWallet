<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="d-flex">
	<div id='calendar' class="col-10 my-3"></div>
	<div class="col-2 my-2">
	
	<table class="table text-center">
		<thead>
			<th>${nowDate} 목표 지출액</th>
		</thead>
		<tbody>
			<td><input type="text" placeholder="목표 지출액" class="form-control" id="goalCount" value="<fmt:formatNumber value="${amountinfo.goalCount}"/>"></td>
		</tbody>
	</table>
	
	<table class="table text-center">
		<thead>
			<th>${nowDate} 지출액</th>
		</thead>
		<tbody>
			<td>
				<input type="text" placeholder="${nowDate} 지출액" id="expenditure" class="form-control" value="<fmt:formatNumber value="${sum}"/>" disabled>
			</td>
		</tbody>
	</table>
	
	<table class="table text-center">
		<thead>
			<th>남은 금액</th>
		</thead>
		<tbody>
			<td>
			<input type="text" id="leftCount" placeholder="남은 금액" class="form-control" value="<fmt:formatNumber value="${amountinfo.remainingAmount}"/>" disabled>
		</tbody>
	</table>
	
	<button type="button" class="btn btn-success w-100" id="target-amount-btn">목표금액 설정</button>
</div>
<script>
	document.addEventListener('DOMContentLoaded', function() {

		$.ajax({
			url : "/monthPlan",
			dataType : "json",
			success : function(data) {
				//console.log(data); // log 로 데이터 찍어주기.
				
				let calendarEl = document.getElementById('calendar');

				let calendar = new FullCalendar.Calendar(calendarEl, {

					initialView : 'dayGridMonth',
					locale : "ko",
					events : data //data 로 값이 넘어온다. log 값 전달.

				});
				calendar.render();
			},
			error : function(jqXHR, textStatus, errorThrown) {

				let errorMsg = jqXHR.responseJSON.status;
				alert(errorMsg + ":" + textStatus);
			}

		});//->ajax끝
	});
</script>