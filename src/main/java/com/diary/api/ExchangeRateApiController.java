package com.diary.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ExchangeRateApiController {
	
	private final static String baseUrl = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON";
	private final String authkey = "CpOkW8AZfVOaLAm2z0M1tkiica6esEVG";
	
	// localhost:8080/exchangeRate/paste_view
	@RequestMapping(value = "/exchangeRate/paste_view", produces = "application/json; charset=utf8")
	public String getApi (Model model
			, @RequestParam(value="searchdate", required = false) String searchdate) {
		String data = "AP01";
		
		WebClient client = WebClient.builder()
				.baseUrl("baseUrl")
				.defaultCookie("cookieKey", "cookieValue")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
				.build();
		
		DefaultUriBuilderFactory fac = new DefaultUriBuilderFactory(baseUrl);
		fac.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
		
		WebClient web = WebClient.builder()
				.uriBuilderFactory(fac)
				.baseUrl(baseUrl)
				.build();
		
		// 바디 받아오기
		String response = web.get()
				.uri(uriBuilder -> uriBuilder
						.queryParam("authkey", authkey)
						.queryParam("searchdate", searchdate)
						.queryParam("data", data)
						.build())
				.retrieve()
				.bodyToMono(String.class)
				.block();
		
		// json 형식
		List<Map<String,String>> listJson = new ArrayList<>();
		String curUnit = null;
		String deal = null;
		
		try {
			listJson = new ObjectMapper().readValue(response, listJson.getClass());
			for(Map<String,String> value : listJson) {
				curUnit = value.get("cur_unit");
				deal = value.get("deal_bas_r");
				if(curUnit.equals("USD")) {
					model.addAttribute("dollar_cur_unit",curUnit);
					model.addAttribute("dollar_deal",deal);
				} else if (curUnit.equals("EUR")) {
					model.addAttribute("euro_cur_unit", curUnit);
					model.addAttribute("euro_deal", deal);
				} else if (curUnit.equals("JPY(100)")) {
					model.addAttribute("yen_cur_unit", curUnit);
					model.addAttribute("yen_deal", deal);
				} else if (curUnit.equals("CNH")) {
					model.addAttribute("yuan_cur_unit", curUnit);
					model.addAttribute("yuan_deal", deal);
				}
				
			}
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("searchdate",searchdate);
		model.addAttribute("viewName", "exchangeRate/ratecopy");
		return "template/layout";
		
		
				
	}
}
