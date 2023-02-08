package com.diary.user.model;

import org.apache.ibatis.type.MappedTypes;

import com.diary.common.model.CodeEnum;
import com.diary.common.model.CodeEnumTypeHandler;

import lombok.Getter;
import lombok.Setter;

public enum Gender implements CodeEnum{
	FEMALE("female"),
	MALE("male");
	
	@Getter
	@Setter
	private String gender;
	
	Gender(String gender){
		this.gender = gender;
	}
	
	// mappedType : 기존 정의 된 타입을 새로운 타입으로 변환
	// Java Config로 myBatis의 TypeHandler를 설정하기 위해서는 @MappedTypes(PaymentMethodType.class) 와 같이 명시적으로 정의해주어야 합니다.
	@MappedTypes(Gender.class)
	public static class TypeHandler extends CodeEnumTypeHandler<Gender>{
		public TypeHandler() {
			super(Gender.class);
		}
	}
	
	
	@Override 
	public String getCode() {
		return gender;
	}
}
