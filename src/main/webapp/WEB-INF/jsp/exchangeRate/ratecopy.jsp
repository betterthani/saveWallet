<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="py-5 px-5">
	<div class="d-flex align-items-center">
		<h4 class="pr-3">날짜 입력</h4>
		<input type="text" id="datepicker" class="form-control w-25">
	</div>
	<small class="text-secondary">* 비영업일 혹은 영업당일 11시 이전은 조회되지 않습니다.<br><sapn class="text-danger">(미선택시 오늘자 환율표기)</sapn></small>
	
	<div>
	
		<%-- 금액입력 창--%>
		<div class="rateUp d-flex align-items-center">
			<input type="number" step="0.001"class="form-control my-5 w-25" placeholder="금액을 입력해주세요." id="inputAmount">
			<button type="button" class="btn btn-success" id="amountBtn">원화 산출</button>
		</div>
		
		<%-- 달러 부분 --%>
		<div class="rateDown d-flex">
			<div class="w-100">
				<%-- 달러 환율 --%>
				
				<div class="d-flex align-items-center">
				
					<%-- 자동 기입부분 : 달러 --%>
					<div id="dollar" name="dollar" class="form-control my-3 mr-5">${dollar_cur_unit} ${dollar_deal}</div>
					
					<%-- 계산 부분 --%>
					<input type="text" class="form-control ml-5" placeholder="달러 총액" id="dollarTotal" name="dollarTotal" data-result="dollar" >
					<a href="#" class="mr-5" id="dollarCopyBtn">
						<img alt="복사버튼" src="/static/img/copy-icon.png" width="30" height="30">
					</a>
				</div>
				
				<%-- 유로 환율 --%>
				<div class="d-flex align-items-center">
					<%-- 자동 기입부분 : 유로 --%>
					<div id="euro" name="euro" class="form-control my-3 mr-5">${euro_cur_unit } ${euro_deal }</div>
					
					<%-- 계산 부분 --%>
					<input type="text" class="form-control ml-5" placeholder="유로 총액" id="euroTotal" data-result="euro">
					<a href="#" class="mr-5" id="euroCopyBtn">
						<img alt="복사버튼" src="/static/img/copy-icon.png" width="30" height="30">
					</a>
				</div>
				
				<%-- 엔화 환율 --%>
				<div class="d-flex align-items-center">
					<%-- 자동 기입부분 : 엔화 --%>
					<div id="yen" name="yen" class="form-control my-3 mr-5">${yen_cur_unit } ${yen_deal }</div>
					
					<%-- 계산 부분 --%>
					<input type="text" class="form-control ml-5" placeholder="엔화 총액" id="yenTotal">
					<a href="#" class="mr-5" id="yenCopyBtn">
						<img alt="복사버튼" src="/static/img/copy-icon.png" width="30" height="30">
					</a>
				</div>
				
				<%-- 위안 환율 --%>
				<div class="d-flex align-items-center">
					<%-- 자동 기입부분 : 위안 --%>
					<div id="yuan" name="yuan" class="form-control my-3 mr-5">${yuan_cur_unit } ${yuan_deal }</div>
					
					<%-- 계산 부분 --%>
					<input type="text" class="form-control ml-5" placeholder="위안 총액" id="yuanTotal">
					<a href="#" class="mr-5" id="yuanCopyBtn">
						<img alt="복사버튼" src="/static/img/copy-icon.png" width="30" height="30">
					</a>
				</div>
				<div class="d-flex justify-content-end mr-5">
					<small> 계산식은 소수점 반올림되어 보여집니다.</small>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" >
	$(document).ready(function(){
		
		// 데이트피커 설정
		$('#datepicker').datepicker({
			dateFormat:"yy-mm-dd" // 2022-11-08
			,dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
			,showButtonPanel: true // 오늘 버튼 활성화
			,currentText:"오늘"
			,maxDate:0 // 오늘 이후 날짜 선택불가
			, beforeShowDay:function(date){ // 주말 비활성화
			let day = date.getDay();
			return [(day!=0 && day != 6)];
			}
			, onSelect:function(data){
				
				let day = data;
				let allData = {"searchdate":day};
				
				$.ajax({
					url:"/exchangeRate/paste_view"
					,data:allData
					,contentType: "application/json;charset=UTF-8"
					,success:function(data){
						alert(day +" 해당 환율을 표기합니다.");
						location.href = "/exchangeRate/paste_view?searchdate=" + day;
					}
					,error:function(jqXHR, textStatus, errorThrown){
						var errorMsg = jqXHR.responseJSON.status;
						alert(errorMsg + ":" + textStatus);
					}
				});
				
			}
		
		});//-> datepicker 설정 끝
		
		
		// 총액버튼 누를때
		$('#amountBtn').on('click',function(){
	
			let inputAmount = $('#inputAmount').val().trim(); // 입력된 금액(String)
			inputAmount = parseFloat(inputAmount).toFixed(); // 소수점 반올림(String)
			inputAmount = parseFloat(inputAmount); // number형 
			
			let dollar = $('#dollar').text().split(" ")[1].replace(/,/g , '');// (Stirng)콤마 삭제
			dollar = parseFloat(dollar).toFixed(); // 달러 소수점 반올림(String)
			dollar = parseFloat(dollar); // number형으로 변환
			//alert("변환" + typeof dollar);
			
			let euro = $('#euro').text().split(" ")[1].replace(/,/g , ''); // 유로
			euro = parseFloat(euro).toFixed();
			euro = parseFloat(euro);
			
			let yen = $('#yen').text().split(" ")[1].replace(/,/g , ''); // 엔화
			yen = parseFloat(yen).toFixed();
			yen = parseFloat(yen);
			
			let yuan = $('#yuan').text().split(" ")[1].replace(/,/g , ''); // 위안
			yuan = parseFloat(yuan).toFixed();
			yuan = parseFloat(yuan);
			
			dollarTotal = (inputAmount * dollar).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');// 구한값 세자리수씩 , 붙이기
			euroTotal = (inputAmount * euro).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');// 구한값 세자리수씩 , 붙이기
			yenTotal = (inputAmount * yen).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');// 구한값 세자리수씩 , 붙이기
			yuanTotal = (inputAmount * yuan).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');// 구한값 세자리수씩 , 붙이기
			
			$('input[name=dollarTotal]').attr("value",dollarTotal); 
			$('#euroTotal').attr("value",euroTotal); 
			$('#yenTotal').attr("value",yenTotal); 
			$('#yuanTotal').attr("value",yuanTotal);

		});//->총액 버튼 클릭 끝
		
		// 달러 복사버튼 누를때
		$('#dollarCopyBtn').on('click',function(e){
			e.preventDefault();
			let copyText = document.getElementById("dollarTotal");
			copyText.select();
			document.execCommand("Copy");
			console.log("dollar copy complete");
		});//-> 달러 복사버튼 끝
		
		// 유로 복사버튼 누를때
		$('#euroCopyBtn').on('click',function(e){
			e.preventDefault();
			let copyText = document.getElementById("euroTotal");
			copyText.select();
			document.execCommand("Copy");
			console.log("euro copy complete");
		});//-> 유로 복사버튼 끝
		
		// 엔화 복사버튼 누를때
		$('#yenCopyBtn').on('click',function(e){
			e.preventDefault();
			let copyText = document.getElementById("yenTotal");
			copyText.select();
			document.execCommand("Copy");
			console.log("yen copy complete");
		});//-> 유로 복사버튼 끝
		
		// 위안 복사버튼 누를때
		$('#yuanCopyBtn').on('click',function(e){
			e.preventDefault();
			let copyText = document.getElementById("yuanTotal");
			copyText.select();
			document.execCommand("Copy");
			console.log("yuan copy complete");
		});//-> 유로 복사버튼 끝
		
	});//->document end
	
	
</script>