<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Save Wallet</title>
		<!-- jquery : bootstrap, datepicker -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>  
		
		<!-- bootstrap -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <!-- 아이콘 모양 부트스트랩 -->
		<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
		<%-- datepicker --%>

		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        
        <!-- style -->
        <link rel="stylesheet" type="text/css" href="/static/css/style.css">
		<script src="/static/javascript/saveWallet.js" type="text/javascript"></script>
		
		<!-- fullcalender -->
		<link href='/static/fullcalendar-5.6.0/lib/main.css' rel='stylesheet' />
		<script src='/static/fullcalendar-5.6.0/lib/main.js'></script>
		
</head>
<body>
	<div id="wrap">
		<header class="d-flex align-items-center justify-content-between pl-3">
			<jsp:include page="../include/header.jsp"/>
		</header>
		
		<section class="contents d-flex">
			<%-- 메뉴부분 --%>
			<jsp:include page="../include/menu.jsp"/>
			
			<%-- 메인 뷰 --%>
			<div class="col-10">
				<jsp:include page="../${viewName}.jsp" />
			</div>
		</section>
		
		<footer class="d-flex align-items-center justify-content-center">
			<jsp:include page="../include/footer.jsp"/>
		</footer>
	</div>
</body>
</html>