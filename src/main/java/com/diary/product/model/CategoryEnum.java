package com.diary.product.model;

import org.apache.ibatis.type.MappedTypes;

import com.diary.common.model.CodeEnum;
import com.diary.common.model.CodeEnumTypeHandler;

import lombok.Getter;
import lombok.Setter;

public enum CategoryEnum implements CodeEnum{
	APPLIANCES("appliances"),
	CLOTHING("clothing"),
	GOODS("goods"),
	COSMETICS("cosmetics"),
	ET("et");
	
	CategoryEnum(String category){
		this.category = category;
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
