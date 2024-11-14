package com.trip.place.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trip.place.service.placeService;
import com.trip.place.util.PageNavigation;
import com.trip.place.vo.Places;
import com.trip.place.vo.Search;
import com.trip.place.vo.Siguns;
import com.trip.place.vo.Type;

@RestController
public class placeController {
	
	@Autowired
	placeService service;
	
	@GetMapping("/place")
	public Map<String, Object> attr(){
		//(sidos, guguns), contenttypes 조회하기 -> 셀렉트 리스트에 넣어줌
		
		Map<String, Object> map = new HashMap<>();
		List<Siguns> sigun = null;
		List<Type> type = null;
		
		try {
			sigun = service.sigunSelect();
			type = service.typeSelect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		map.put("sigun", sigun);
		map.put("contentType", type);
		
		return map;
	}
	
	@PostMapping("/place/search")
	public Map<String, Object> search(@RequestBody Search search) throws Exception{
		//시도, 구군, 관광지타입, 키워드를 받아 해당 내용으로 DB select 하기
		Map<String, Object> map = new HashMap<>();
		
		List<Places> list = service.listAttr(search); //6개씩 페이지 번호에 맞춰서 DB 데이터 가져오기
		PageNavigation pageNavigation = service.makePageNavigation(search); //페이지 번호 만들기
		map.put("attrList", list); //가져온 관광지정보 리스트
		map.put("navigation", pageNavigation);
		map.put("pgno", search.getPgno()); //페이지 넘버
		map.put("sido", search.getSido()); //시도
		map.put("gugun", search.getGugun()); //구군
		map.put("type", search.getType()); //어트랙션 종류
		map.put("word", search.getWord()); //검색어
		return map;
	}
}
