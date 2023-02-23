package com.diary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.diary.common.FileManagerService;
import com.diary.interceptor.PermissionInterceptor;
import com.diary.product.converter.TypeConverter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private PermissionInterceptor interceptor;
	
	// 이미지
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**") // 웹 이미지 주소 http://localhost/images/aaaa_16205468768/sun.png
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 파일 위치(경로)
	}
	
	// type컨버터 addConverter로 재정의하여 추가
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new TypeConverter());
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
		.addPathPatterns("/**")			// 		/** 아래 디렉토리까지 확인(모든 디렉토리 확인)
		.excludePathPatterns("/favicon.ico", "/error", "/static/**", "/user/sign_out");
	}
	
}
