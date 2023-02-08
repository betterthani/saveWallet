package com.diary.product.converter;

import org.springframework.core.convert.converter.Converter;

import com.diary.product.model.TypeEnum;

public class TypeConverter implements Converter<String, TypeEnum>{
	
	// type에 대한 converter 생성
	@Override
	public TypeEnum convert(String type) {
		
		return TypeEnum.ofType(type);
	}
/*	
	// url 소문자 지원 가능
	@Override
	public TypeEnum convert(String type) {
        return TypeEnum.valueOf(type);
    }
*/	

}
