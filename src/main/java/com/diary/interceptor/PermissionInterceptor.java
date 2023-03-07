package com.diary.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component 
public class PermissionInterceptor implements HandlerInterceptor{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws IOException {
		
		// 요청 url을 가져온다.
		String uri = request.getRequestURI(); // /post/post_list_view
		logger.info("[####### preHandle: uri:{}]", uri);
		
		// 세션이 있는지 확인 => 있으면 로그인
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		
		// 비로그인 && /calendar로 온 경우 -> 로그인 페이지로 리다이렉트 return false
		if (userId == null && uri.startsWith("/calendar")) {
			response.sendRedirect("/user/sign_in_view");
			return false; // 컨트롤러 수행 x
		}
		
		// 비로그인 && /product로 온 경우 -> 글 목록 페이지로 리다이렉트 return false
		if (userId == null && uri.startsWith("/product")) {
			response.sendRedirect("/user/sign_in_view");
			return false; // 컨트롤러 수행 x
		}
		
		// 로그인 && /user로 온 경우 -> 글 목록 페이지로 리다이렉트 return false
		if (userId != null && uri.startsWith("/user") && !uri.startsWith("/user/in")) {
			response.sendRedirect("/calendar");
			return false; // 컨트롤러 수행 x
		}
		
		// 비로그인 && /user/in로 온 경우 -> 로그인 페이지로 리다이렉트 return false
		if (userId == null && uri.startsWith("/user/in")) {
			response.sendRedirect("/user/sign_in_view");
			return false; // 컨트롤러 수행 x
		}
		
		
		
		
		return true; // 컨트롤러 수행 o
	}
	
	// 아래는 비로그인 기반일 경우 사용하지 않아도 된다.
	@Override // html되기 전 이기 떄문에 model갖고있따.
	public void postHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			ModelAndView mav) {
		
		logger.info("[$$$$$$$$$ postHandle]");
		
	}
	
	@Override	
	public void afterCompletion(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception ex) {
		logger.info("[@@@@@@@@ afterCompletion]");
	}
	
}
