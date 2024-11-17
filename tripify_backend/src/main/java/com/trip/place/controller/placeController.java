package com.trip.place.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.place.service.placeService;
import com.trip.place.util.PageNavigation;
import com.trip.place.vo.Places;
import com.trip.place.vo.Search;
import com.trip.place.vo.SidoGugunCode;
import com.trip.place.vo.Type;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:80"}, allowCredentials = "true")
@RestController
public class placeController {
	
	@Autowired
	placeService service;
	
	@GetMapping("/place/sido")
	public ResponseEntity<List<SidoGugunCode>> sido() throws Exception {
		return new ResponseEntity<List<SidoGugunCode>>(service.getSido(), HttpStatus.OK);
	}

	@GetMapping("/place/gugun")
	public ResponseEntity<List<SidoGugunCode>> gugun(
			@RequestParam("sido") String sido) throws Exception {
		return new ResponseEntity<List<SidoGugunCode>>(service.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@GetMapping("/place/type")
	public ResponseEntity<List<Type>> type() throws Exception{
		return new ResponseEntity<List<Type>>(service.typeSelect(), HttpStatus.OK);
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
