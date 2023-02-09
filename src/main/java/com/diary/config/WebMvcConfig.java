package com.diary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.diary.common.FileManagerService;
import com.diary.product.converter.TypeConverter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	// type컨버터 addConverter로 재정의하여 추가
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new TypeConverter());
	}
	
	// 이미지
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**")
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH);
	}
}
