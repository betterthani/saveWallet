$(document).ready(function() {

	// 사진 선택
	$('.pictureBtn').on('click', function() {
		$('#file').click();
	});

	// 이미지 선택시 사진 보여지기
	$('#file').on('change', function() {
		setImageFromFile(this, '.profileBox');
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

	// 비밀번호 변경버튼 누를때 변경할 비밀번호 창 띄우기
	$('#passwordEditBtn').on('click', function() {
		$('.change-password').removeClass('d-none');
		$('#warning-length-text').removeClass('d-none');
	});

	//프로필 변경 버튼 눌렀을때
	$('#profileEditBtn').on('click', function() {
		let nickName = $('.nickName').val();
		let statusMessage = $('.statusMessage').val();
		let password = $('.password').val();
		let file = $('#file').val();

		if (nickName == '') {
			alert("닉네임을 입력해주세요.");
			return;
		}

		if (password == '') {
			alert("비밀번호를 입력해주세요.");
			return;
		}

		if (file != '') {
			let ext = file.split(".").pop().toLowerCase();
			if ($.inArray(ext, ['jpg', 'jpeg', 'png', 'gif']) == -1) {
				alert("지원하는 확장자가 아닙니다.");
				$('#file').val("");
				return;
			}
		}

		let formData = new FormData();
		formData.append("nickName", nickName);
		formData.append("statusMessage", statusMessage);
		formData.append("password", password);
		formData.append("file", $('#file')[0].files[0]);

		$.ajax({
			type: "PUT"
			, url: "/user/myPage/update"
			, data: formData
			, enctype: "multipart/form-data"
			, processData: false
			, contentType: false
			, success: function(data) {
				if (data.code == 1) {
					// 성공
					alert("수정 완료되었습니다.");
					document.location.reload();
				} else if (data.code == 2) {
					// 비밀번호 조회 실패
					alert("기존 비밀번호와 맞지 않습니다.");
					document.location.reload();
				} else {
					// 실패
					alert("수정 실패.");
					return;
				}
			}
			, error: function(jqXHR, textStatus, errorThrown) {
				var errorMsg = jqXHR.responseJSON.status;
				alert(errorMsg + ":" + textStatus);
			}
		});

	});//프로필 변경 버튼끝

	// 비밀번호 변경 버튼 누를때
	$('#passwordEditBtn').on('click', function() {
		let password = $('.password').val();
		let changePassword = $('.change-password').val();

		if (password == '') {
			alert("기존 비밀번호를 입력해주세요.");
			return;
		}

		if (changePassword == '') {
			alert("변경할 비밀번호를 입력해주세요.");
			return;
		}

		let formData = new FormData();
		formData.append("password", password);
		formData.append("changePassword", changePassword);

		$.ajax({
			type: "POST"
			, url: "/user/passwordUpdate_validation"
			, data: formData
			, contentType: false
			, processData: false
			, success: function(data) {
				if (data.code == 500) {
					// 유효성검사 실패(ValidateHandler,ValidateUser연결)
					// 공백의 경우
					if (data.validKeyName == 'validatePassword' || data.validKeyName == 'validateChangePassword') {
						alert("비어있는란을 모두 채워주세요.");
						return;
					}

					// 비밀번호 길이 문제
					// 초기화
					$('.password').css("color", "black");
					if (data.validKeyName == 'passwordLength') {
						$('.password').css("color", "red");
						return;
					}

					// 변경 비밀번호 길이 문제
					// 초기화
					$('.change-password').css("color", "black");
					if (data.validKeyName == 'changepasswordLength') {
						$('.change-password').css("color", "red");
						$('#warning-length-text').css("color", "red");
						return;
					}
				} else {
					// 유효성 검사 통과
					$.ajax({
						type: "PUT"
						, url: "/user/password_update"
						, data: formData
						, contentType: false
						, processData: false
						, success: function(data) {
							if (data.code == 1) {
								alert("비밀번호 변경이 되었습니다.");
								document.location.reload();
							} else if (data.code == 2) {
								alert("기존 비밀번호가 일치하지 않습니다.");
								return;
							} else {
								alert("비밀번호 변경에 실패했습니다.");
								return;
							}
						}
						, error: function(jqXHR, textStatus, errorThrown) {
							let errorMsg = jqXHR.responseJSON.status;
							alert(errorMsg + ":" + textStatus);
						}
					});//->ajax end
				}
			}

		});
	});// 비밀번호 변경 버튼 끝

	// 회원탈퇴 버튼 눌렀을때
	$('#exitUserBtn').on('click', function() {
		let password = $('.password').val();

		if (password == '') {
			alert("기존 비밀번호를 입력해주세요.");
			return;
		}
		if (confirm("탈퇴 하시겠습니까?")) {
			let formData = new FormData();
			formData.append("password", password);

			$.ajax({
				type: "DELETE"
				, url: "/user/secession"
				, data: formData
				, contentType: false
				, processData: false
				, success: function(data) {
					if (data.code == 1) {
						alert("회원 탈퇴가 정상적으로 되었습니다.");
						location.href = "/user/sign_out";
					} else if (data.code == 2) {
						alert(data.result);
						return;
					} else {
						alert("회원탈퇴에 실패했습니다.");
						return;
					}
				}
				, error: function(jqXHR, textStatus, errorThrown) {
					let errorMsg = jqXHR.responseJSON.status;
					alert(errorMsg + ":" + textStatus);
				}
			});
		}
	}); // -> 회원탈퇴 끝

});