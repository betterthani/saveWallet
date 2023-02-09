$(document).ready(function() {
	//-----------------------------------------------로그인
	// 로그인 관련
	$('.loginBtn').on('click', function() {
		let loginId = $('#loginId').val().trim();
		let password = $('#password').val();

		if (loginId == '') {
			alert("아이디를 입력해주세요.");
			return;
		}

		if (password == '') {
			alert("비밀번호를 입력해주세요.");
			return;
		}

		let formData = new FormData();
		formData.append("loginId", loginId);
		formData.append("password", password);
		$.ajax({
			type: "POST"
			, url: "/user/sign_in"
			, data: formData
			, contentType: false
			, processData: false
			, success: function(data) {
				if (data.code == 1) {
					document.location.href = "/product/shopping_list_view";
				} else if (data.code == 2) {
					alert("아이디 혹은 비밀번호를 재확인해주세요.");
					document.location.reload();
				}
			}
			, error: function(jqXHR, textStatus, errorThrown) {
				var errorMsg = jqXHR.responseJSON.status;
				alert(errorMsg + ":" + textStatus);
			}

		});
	});

	//-----------------------------------------------회원가입
	// id 길이(중복 확인)
	$('#signUp-loginId').on('input', function() {
		//초기화
		$('#idCheckLength').addClass('d-none');
		$('#idCheckDuplicated').addClass('d-none');
		$('#idCheckOk').addClass('d-none');

		let loginId = $('input[name=signUp-loginId]').val().trim();
		if (loginId.length < 4) {
			loginId = loginId.slice(0, 4);
			$(this).val(loginId);
			$('#idCheckLength').removeClass('d-none');
			return;
		}
	});

	// 회원가입 버튼 누를때
	$('#signUpBtn').on('click', function() {
		let loginId = $('input[name=signUp-loginId]').val().trim();
		let nickName = $('#signUp-nickName').val().trim();
		let password = $('#signUp-password').val();
		let email = $('#signUp-email').val();
		let gender = $('#gender option:selected').val();
		let age = $('#age option:selected').val();

		if (loginId == '') {
			alert("아이디를 입력해주세요.");
			return;
		}

		if (nickName == '') {
			alert("닉네임을 입력해주세요.");
			return;
		}

		if (password == '') {
			alert("비밀번호를 입력해주세요.");
			return;
		}

		if (email == '') {
			alert("이메일을 입력해주세요.");
			return;
		}

		if (age == undefined) {
			alert("성별을 선택해주세요.");
			return;
		}

		if (gender == undefined) {
			alert("연령대를 선택해주세요.");
			return;
		}



		let formData = new FormData();
		formData.append("loginId", loginId);
		formData.append("password", password);
		formData.append("nickName", nickName);
		formData.append("email", email);
		formData.append("gender", gender);
		formData.append("age", age);

		$.ajax({
			type: "POST"
			, url: "/user/sign_up_validation"
			, data: formData
			, contentType: false
			, processData: false
			, success: function(data) {
				if (data.code == 500) {
					// 유효성검사 실패(ValidateHandler,ValidateUser연결)
					// 공백의 경우
					if (data.validKeyName == 'validateLoginId' || data.validKeyName == 'validateNickName'
						|| data.validKeyName == 'validatePassword' || data.validKeyName == 'validateEmail'
						|| data.validKeyName == 'validateGender' || data.validKeyName == 'validateAge') {
						alert("비어있는란을 모두 채워주세요.");
						return;
					}

					// 아이디 길이 문제
					//초기화
					$('#idCheckLength').addClass('d-none');
					if (data.validKeyName == 'userIdLength') {
						$('#idCheckLength').removeClass('d-none');
						return;
					}

					// 아이디 존재
					//초기화
					$('#idCheckDuplicated').addClass("d-none");
					if (data.existLoginId == 'existLoginId') {
						$('#idCheckDuplicated').removeClass("d-none");
						return;
					}

					// 닉네임 길이 문제
					//초기화
					$('#nickNameCheckLength').addClass('d-none');
					if (data.validKeyName == 'nickNameLength') {
						$('#nickNameCheckLength').removeClass('d-none');
						return;
					}

					// 비밀번호 길이 문제
					// 초기화
					$('#password-warning').css("color", "black");
					if (data.validKeyName == 'passwordLength') {
						$('#password-warning').css("color", "red");
						return;
					}

					// 이메일 규칙 위반
					//초기화
					$('#emailCheckLength').addClass('d-none');
					if (data.validKeyName == 'emailValidation') {
						$('#emailCheckLength').removeClass('d-none');
						return;
					}

				} else if (data.code == 1) {
					// 아이디 중복 모두 통과
					$.ajax({
						type: "POST"
						, url: "/user/sign_up"
						, data: formData
						, contentType: false
						, processData: false
						, success: function(data) {
							if (data.code == 1) {
								alert(nickName + "님 회원가입을 축하드립니다!");
								location.href = "/user/sign_in_view";
							} else {
								alert("회원가입에 실패했습니다. 관리자에 문의해주세요.");
								return;
							}
						}
						, error: function(jqXHR, textStatus, errorThrown) {
							var errorMsg = jqXHR.responseJSON.status;
							alert(errorMsg + ":" + textStatus);
						}
					}); //-> 회원가입ajax 끝
				}
			}
			, error: function(jqXHR, textStatus, errorThrown) {
				var errorMsg = jqXHR.responseJSON.status;
				alert(errorMsg + ":" + textStatus);
			}
		}); //-> 유효성 검사ajax 끝

	}); //-> 회원가입 버튼 끝

	//-----------------------------------------------비밀번호 찾기
	$('.find-password-btn').on('click', function() {

		let loginId = $('#find-password-loginId').val().trim();
		let email = $('#find-password-email').val().trim();

		if (loginId == '') {
			alert("아이디를 입력해주세요.");
			return;
		}

		if (email == '') {
			alert("이메일주소를 입력해주세요.");
			return;
		}

		let formdata = new FormData();
		formdata.append("loginId", loginId);
		formdata.append("email", email);

		$.ajax({
			type: "POST"
			, url: "/user/find_password"
			, data: formdata
			, contentType: false
			, processData: false
			, success: function(data) {
				// 아이디, 비밀번호 일치하는 부분이있다면 메일 보내기
				if (data.result) {
					$.ajax({
						type: "POST"
						, url: "/user/find_password/sendEmail"
						, data: { "email": email }
						, success: function(data) {
							alert("이메일 발송이 완료되었습니다.");
							window.location = "/user/sign_in_view";
						}
						, error: function(jqXHR, textStatus, errorThrown) {
							var errorMsg = jqXHR.responseJSON.status;
							alert(errorMsg + ":" + textStatus);
						}
					})//-> 임시 비번보내기 ajax끝

				} else {
					alert("일치하는 정보가 없습니다.");
				}
			}
			, error: function(jqXHR, textStatus, errorThrown) {
				var errorMsg = jqXHR.responseJSON.status;
				alert(errorMsg + ":" + textStatus);
			}

		}); //-> id, email확인 ajax 끝

	});//->비밀번호 찾기 버튼 끝
	//----------------------------------------------- 환율조회
	// 데이트피커 설정
	$('#datepicker').datepicker({
		dateFormat: "yy-mm-dd" // 2022-11-08
		, dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
		, showButtonPanel: true // 오늘 버튼 활성화
		, currentText: "오늘"
		, maxDate: 0 // 오늘 이후 날짜 선택불가
		, beforeShowDay: function(date) { // 주말 비활성화
			let day = date.getDay();
			return [(day != 0 && day != 6)];
		}
		, onSelect: function(data) {

			let day = data;
			let allData = { "searchdate": day };

			$.ajax({
				url: "/exchangeRate/paste_view"
				, data: allData
				, contentType: "application/json;charset=UTF-8"
				, success: function(data) {
					location.href = "/exchangeRate/paste_view?searchdate=" + day;
				}
				, error: function(jqXHR, textStatus, errorThrown) {
					var errorMsg = jqXHR.responseJSON.status;
					alert(errorMsg + ":" + textStatus);
				}
			});

		}

	});//-> datepicker 설정 끝


	// 총액버튼 누를때
	$('#amountBtn').on('click', function() {

		let inputAmount = $('#inputAmount').val().trim(); // 입력된 금액(String)
		inputAmount = parseFloat(inputAmount).toFixed(); // 소수점 반올림(String)
		inputAmount = parseFloat(inputAmount); // number형 

		let dollar = $('#dollar').text().split(" ")[1].replace(/,/g, '');// (Stirng)콤마 삭제
		dollar = parseFloat(dollar).toFixed(); // 달러 소수점 반올림(String)
		dollar = parseFloat(dollar); // number형으로 변환
		//alert("변환" + typeof dollar);

		let euro = $('#euro').text().split(" ")[1].replace(/,/g, ''); // 유로
		euro = parseFloat(euro).toFixed();
		euro = parseFloat(euro);

		let yen = $('#yen').text().split(" ")[1].replace(/,/g, ''); // 엔화
		yen = parseFloat(yen).toFixed();
		yen = parseFloat(yen);

		let yuan = $('#yuan').text().split(" ")[1].replace(/,/g, ''); // 위안
		yuan = parseFloat(yuan).toFixed();
		yuan = parseFloat(yuan);

		dollarTotal = (inputAmount * dollar).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');// 구한값 세자리수씩 , 붙이기
		euroTotal = (inputAmount * euro).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');// 구한값 세자리수씩 , 붙이기
		yenTotal = (inputAmount * yen).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');// 구한값 세자리수씩 , 붙이기
		yuanTotal = (inputAmount * yuan).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');// 구한값 세자리수씩 , 붙이기

		$('input[name=dollarTotal]').attr("value", dollarTotal);
		$('#euroTotal').attr("value", euroTotal);
		$('#yenTotal').attr("value", yenTotal);
		$('#yuanTotal').attr("value", yuanTotal);

	});//->총액 버튼 클릭 끝

	// 달러 복사버튼 누를때
	$('#dollarCopyBtn').on('click', function(e) {
		e.preventDefault();
		let copyText = document.getElementById("dollarTotal");
		copyText.select();
		document.execCommand("Copy");
		console.log("dollar copy complete");
	});//-> 달러 복사버튼 끝

	// 유로 복사버튼 누를때
	$('#euroCopyBtn').on('click', function(e) {
		e.preventDefault();
		let copyText = document.getElementById("euroTotal");
		copyText.select();
		document.execCommand("Copy");
		console.log("euro copy complete");
	});//-> 유로 복사버튼 끝

	// 엔화 복사버튼 누를때
	$('#yenCopyBtn').on('click', function(e) {
		e.preventDefault();
		let copyText = document.getElementById("yenTotal");
		copyText.select();
		document.execCommand("Copy");
		console.log("yen copy complete");
	});//-> 유로 복사버튼 끝

	// 위안 복사버튼 누를때
	$('#yuanCopyBtn').on('click', function(e) {
		e.preventDefault();
		let copyText = document.getElementById("yuanTotal");
		copyText.select();
		document.execCommand("Copy");
		console.log("yuan copy complete");
	});//-> 유로 복사버튼 끝

	//----------------------------------------------- 검색기능
	$('#keyword-search-btn').on('click', function() {

		let keyword = $('#keyword-search-text').val().trim();
		if (keyword == '') {
			alert("검색어를 입력해주세요.");
			return;
		}

		location.href = "/product/shopping_list_view?keyword=" + keyword;
	});
	
	// 라디오기능 체크
	$('input[name=sort]').on('click', function() {
		let checkValue = $('input[name=sort]:checked').val();

		alert(checkValue + "순으로 보여집니다.");
		location.href = "/product/shopping_list_view?orderCategory=" + checkValue;
	});

	// 전체체크박스 기능
	$('#allcheck').on('click', function() {
		if ($('#allcheck').prop("checked")) {
			$('input[name=select]').prop('checked', true);
		} else {
			$('input[name=select]').prop('checked', false);
		}
	});

	// 체크됐을때 해당 productid 가져오기 
	$('input[name=select]').on('click', function() {
		// 배열로 값 가져오기
		$('input[name=select]:checked').each(function() {
			let checkValue = $(this).data('product-id');
			//console.log(checkValue);
		});//-> 배열 끝

	});//-> 체크값 가져오기 끝

	//----------------------------------------------- shoppingList 글 게시

	// 이미지 선택시 사진 보이게 하기
	$('#formFile-shopping-list').on('change', function() {
		setImageFromFile(this, '.shopping-img-place');
	});
	function setImageFromFile(input, expression) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$(expression).attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}

	// 구매일 데이트피커 설정
	$('#shopping-date-datepicker').datepicker({
		dateFormat: "yy-mm-dd" // 2022-11-08
		, dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
		, showButtonPanel: true // 오늘 버튼 활성화
		, currentText: "오늘"
	});

	// 반품가능일 데이트피커 설정
	$('#shopping-returnn-datepicker').datepicker({
		dateFormat: "yy-mm-dd" // 2022-11-08
		, dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
		, showButtonPanel: true // 오늘 버튼 활성화
		, currentText: "오늘"
	});
	
	// 구매처 온라인일 경우 적을 구매처오픈
	$('#shopping-purchasedCategory').on('change',function(){
		let purchasedCategory = $('#shopping-purchasedCategory option:selected').val();
		// 초기화
		$('#shopping-purchased').addClass('d-none');
		
		if(purchasedCategory == 'online'){
			$('#shopping-purchased').removeClass('d-none');
		}
	});

	// 업로드버튼 눌렀을 때
	$('#shoppingList-upload-btn').on('click', function() {
		let itemName = $('#shopping-itemName').val().trim();
		let category = $('#shopping-category option:selected').val();
		let amount = $('#shopping-amount').val().trim(); //string
		let purchasedCategory = $('#shopping-purchasedCategory option:selected').val();
		let purchased = $('#shopping-purchased').val();
		let size = $('#shopping-size').val().trim();
		let datePurchased = $('#shopping-date-datepicker').val(); //2023-02-01종류string
		let returnableDeadline = $('#shopping-returnn-datepicker').val(); //2023-02-01종류string
		let usedHope = $('#shopping-usedHope option:selected').val();
		let file = $('#formFile-shopping-list').val(); //C:\fakepath\ASCII.png

		if (file != '') {
			let ext = file.split(".").pop().toLowerCase();
			if (ext != 'jpg' && ext != 'jpeg' && ext != 'gif' && ext != 'png') {
				alert("이미지 파일만 게시할 수 있습니다.");
				$('#file').val('');
				$('#fileName').text('');
				return;
			}
		}

		if (file == '') {
			alert("파일을 선택해주세요.");
			return;
		}
		if (itemName == '') {
			alert("제품명을 입력해주세요.");
			return;
		}
		if (category == '') {
			alert("카테고리를 선택해주세요.");
			return;
		}
		if (amount == '') {
			alert("금액을 입력해주세요.");
			return;
		}
		if (purchasedCategory == '') {
			alert("구매처를 선택해주세요.");
			return;
		}
		if (purchased == '') {
			alert("구매처를 입력해주세요.");
			return;
		}
		if (datePurchased == '') {
			alert("구매일을 선택해주세요.");
			return;
		}
		if (usedHope == '') {
			alert("당근 희망여부를 선택해주세요.");
			return;
		}

		let formData = new FormData();
		formData.append("itemName", itemName);
		formData.append("category", category);
		formData.append("amount", amount);
		formData.append("purchasedCategory", purchasedCategory);
		formData.append("purchased", purchased);
		formData.append("size", size);
		formData.append("datePurchased", datePurchased);
		formData.append("returnableDeadline", returnableDeadline);
		formData.append("usedHope", usedHope);
		formData.append("file", $('#formFile-shopping-list')[0].files[0]);

		$.ajax({
			// request
			type: "POST"
			, url: "/product/shopping_list_write"
			, data: formData
			, enctype: "multipart/form-data"
			, processData: false
			, contentType: false

			// response
			, success: function(data) {
				if (data.code == 1) {
					alert("업로드 되었습니다.");
					location.href = "/product/shopping_list_view";
				} else {
					alert("업로드에 실패하였습니다.");
				}
			}
			, error: function(jqXHR, textStatus, errorThrown) {
				var errorMsg = jqXHR.responseJSON.status;
				alert(errorMsg + ":" + textStatus);
			}

		});

	});//-> 업로드 버튼 끝
});
