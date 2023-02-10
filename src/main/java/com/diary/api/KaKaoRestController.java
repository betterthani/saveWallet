package com.diary.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KaKaoRestController {
	
	private static final String restapi = "키값";
	
	// 지도 api가져오기
	//localhost:8080/kakao/kakaoMap/search?query=
	@GetMapping("/kakaoMap/search")
	public String kakaoMapApi(
			@RequestParam("query") String query
			){
		Mono<String> mono = WebClient.builder().baseUrl("https://dapi.kakao.com")
		.build().get()
		.uri(builder -> builder.path("/v2/local/search/keyword.json")
				.queryParam("query", query)
				.build()
				)
		.header("Authorization", "KakaoAK " + restapi)
		.exchangeToMono(response ->{
			return response.bodyToMono(String.class);
		});
		return mono.block();
		
	}
}
