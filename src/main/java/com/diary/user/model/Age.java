package com.diary.user.model;

import org.apache.ibatis.type.MappedTypes;

import com.diary.common.model.CodeEnum;
import com.diary.common.model.CodeEnumTypeHandler;

import lombok.Getter;
import lombok.Setter;

public enum Age implements CodeEnum{
	TEENAGEMORE("teenageMore"),
	THIRTYMORE("thirtyMore");
	
	@Getter
	@Setter
	private String age;
	
	Age(String age) {
		this.age = age;
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

