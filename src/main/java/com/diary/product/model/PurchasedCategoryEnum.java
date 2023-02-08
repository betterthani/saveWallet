package com.diary.product.model;

import org.apache.ibatis.type.MappedTypes;

import com.diary.common.model.CodeEnum;
import com.diary.common.model.CodeEnumTypeHandler;

import lombok.Getter;
import lombok.Setter;

public enum PurchasedCategoryEnum implements CodeEnum{
	ONLINE("online"),
	OFFLINE("offline");
	
	@Getter
	@Setter
	private String purchasedCategory;
	
	PurchasedCategoryEnum(String purchasedCategory){
		this.purchasedCategory = purchasedCategory;
	}
	
	@MappedTypes(PurchasedCategoryEnum.class)
	public static class TypeHandler extends CodeEnumTypeHandler<PurchasedCategoryEnum>{
		public TypeHandler() {
			super(PurchasedCategoryEnum.class);
		}
	}

	@Override
	public String getCode() {
		return purchasedCategory;
	}

}
