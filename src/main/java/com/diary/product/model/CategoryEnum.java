package com.diary.product.model;

import org.apache.ibatis.type.MappedTypes;

import com.diary.common.model.CodeEnum;
import com.diary.common.model.CodeEnumTypeHandler;

import lombok.Getter;
import lombok.Setter;

public enum CategoryEnum implements CodeEnum{
	APPLIANCES("APPLIANCES"),
	CLOTHING("CLOTHING"),
	GOODS("GOODS"),
	COSMETICS("COSMETICS"),
	ET("ET");
	
	CategoryEnum(String category){
		this.category = category;
	}
	
	public static CategoryEnum ofCategory(String category) {
		if(category == null) {
			throw new IllegalArgumentException("category null입니다.");
		}
		
		for(CategoryEnum categoryType : CategoryEnum.values()) {
			if(categoryType.getCategory().equals(category)) {
				return categoryType;
			}
		}
		
		throw new IllegalArgumentException("타입과 일치하는 것이 존재하지 않습니다.");
	}
	
	@Getter
	@Setter
	private String category;
	
	@MappedTypes(TypeEnum.class)
	public static class TypeHandler extends CodeEnumTypeHandler<CategoryEnum>{
		public TypeHandler() {
			super(CategoryEnum.class);
		}
	}

	@Override
	public String getCode() {
		return category;
	}
	
	
}
