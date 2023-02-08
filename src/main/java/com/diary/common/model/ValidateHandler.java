package com.diary.common.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Component
public class ValidateHandler {
	
	// 회원가입시 유효성 체크 (실패한 필드들 Map이용, 키값과 에러메세지값 응답)
	public Map<String, String> validateHandling(Errors errors){
		
		Map<String, String> validatorResult = new HashMap<>();
		for(FieldError error : errors.getFieldErrors()) { // errors.getFieldErrors() : 유효성 검사 실패한 필드 목록 
			
			String validKeyName=String.format("valid_%s", error.getField()); //error.getField(): 실패한 필드명
			validatorResult.put("validKeyName", error.getDefaultMessage()); // error.getDefaultMessage(): 실패한 필드 정의된 메세지
		}
		return validatorResult;
	}
}
