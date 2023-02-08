package com.diary.product.model;

import org.apache.ibatis.type.MappedTypes;

import com.diary.common.model.CodeEnum;
import com.diary.common.model.CodeEnumTypeHandler;
import com.diary.user.model.Age;

import lombok.Getter;
import lombok.Setter;

public enum TypeEnum implements CodeEnum{
	SHOPPING("shopping"),
	WISH("wish");
	
	@Getter
	@Setter
	private String type;

	TypeEnum(String type){
		this.type = type;
	}
	
	// 요청파라미터로 타입을 받아 조회(컨트롤에서 해야할 문장 여기서 대체)
	public static TypeEnum ofType(String type) {
		if(type == null) {
			// 예외처리
			throw new IllegalArgumentException("type null 입니다.");
		}
		
		for (TypeEnum typeEnum : TypeEnum.values()) { // 타입 값 배열로 갖고오기
			if(typeEnum.getType().equals(type)) {
				return typeEnum;
			}
		}
		
		throw new IllegalArgumentException("타입과 일치하는 것이 존재하지 않습니다.");
	}
	
	@MappedTypes(TypeEnum.class)
	public static class TypeHandler extends CodeEnumTypeHandler<TypeEnum>{
		public TypeHandler() {
			super(TypeEnum.class);
		}
	}

	@Override
	public String getCode() {
		return type;
	}
	

}
