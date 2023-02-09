package com.diary.user.model;

import org.apache.ibatis.type.MappedTypes;

import com.diary.common.model.CodeEnum;
import com.diary.common.model.CodeEnumTypeHandler;

import lombok.Getter;
import lombok.Setter;

public enum Age implements CodeEnum{
	TEENAGE("teenage"),
	TWENTY("twenty"),
	THIRTY("thirty"),
	FORTYMORE("fortyMore");
	
	@Getter
	@Setter
	private String age;
	
	Age(String age) {
		this.age = age;
	}
	
	public static Age ofAge(String age) {
		if(age == null) {
			throw new IllegalArgumentException("age null입니다.");
		}
		
		for(Age ageType : Age.values()) {
			if(ageType.getAge().equals(age)) {
				return ageType;
			}
		}
		throw new IllegalArgumentException("타입과 일치하는 것이 존재하지 않습니다.");
	}
	
	
	@MappedTypes(Age.class)
	public static class TypeHandler extends CodeEnumTypeHandler<Age>{
		public TypeHandler() {
			super(Age.class);
		}
	}
	
	@Override 
	public String getCode() {
		return age;
	}
	
	
}

