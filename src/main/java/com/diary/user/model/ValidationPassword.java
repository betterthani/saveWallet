package com.diary.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
public class ValidationPassword {
	
	@NotBlank(message = "validatePassword")
	private String password;
	
	@NotBlank(message = "validateChangePassword")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}", message = "changepasswordLength")
	private String changePassword;
}
