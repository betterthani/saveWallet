package com.diary.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
public class ValidationUser {

	@NotBlank(message = "validateLoginId")
	@Size(min = 4, max = 15, message = "userIdLength")
	private String loginId;

	@NotBlank(message = "validateNickName")
	@Size(min = 2, max = 20, message = "nickNameLength")
	private String nickName;

	@NotBlank(message = "validatePassword")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}", message = "passwordLength")
	private String password;

	@NotBlank(message = "validateEmail")
	@Email(message = "emailValidation")
	private String email;

	@NotBlank(message = "validateGender")
	private String gender;

	@NotBlank(message = "validateAge")
	private String age;

}
