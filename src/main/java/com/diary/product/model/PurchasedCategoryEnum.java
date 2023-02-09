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
	
	public static PurchasedCategoryEnum ofPurchasedCategory(String purCate) {
		if(purCate == null) {
			throw new IllegalArgumentException("PurchasedCategory null입니다.");
		}
		
		for(PurchasedCategoryEnum purCateType : PurchasedCategoryEnum.values()) {
			if(purCateType.getPurchasedCategory().equals(purCate)) {
				return purCateType;
			}
		}
		
		throw new IllegalArgumentException("타입과 일치하는 것이 존재하지 않습니다.");
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
