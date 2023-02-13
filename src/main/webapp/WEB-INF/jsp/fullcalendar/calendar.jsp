<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="d-flex">
	<div id='calendar' class="col-10 my-3"></div>
	<div class="col-2 my-2">
	
	<table class="table">
		<thead>
			<th>목표 지출액</th>
		</thead>
		<tbody>
			<td><input type="number" placeholder="목표 지출액" class="form-control"></td>
		</tbody>
	</table>
	
	<table class="table">
		<thead>
			<th>해당월 지출액</th>
		</thead>
		<tbody>
			<td>${sum}</td>
		</tbody>
	</table>
	
	<table class="table">
		<thead>
			<th>남은 금액</th>
		</thead>
		<tbody>
			<td><input type="number" placeholder="목표 지출액" class="form-control"></td>
		</tbody>
	</table>
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