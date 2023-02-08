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
						, data: {"email":email}
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
	
	//-----------------------------------------------
	
	
});
