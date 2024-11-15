package com.trip.place.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.place.mapper.placeMapper;
import com.trip.place.util.PageNavigation;
import com.trip.place.util.SizeConstant;
import com.trip.place.vo.Places;
import com.trip.place.vo.Search;
import com.trip.place.vo.SidoGugunCode;
import com.trip.place.vo.Type;

@Service
public class placeServiceImpl implements placeService {

	@Autowired
	placeMapper mapper;

	@Override
	public ArrayList<Type> typeSelect() throws Exception {
		return mapper.typeSelect();
	}
	
	@Override
	public List<SidoGugunCode> getSido() throws Exception {
		return mapper.getSido();
	}

	@Override
	public List<SidoGugunCode> getGugunInSido(String sido) throws Exception {
		return mapper.getGugunInSido(sido);
	}

	@Override
	public List<Places> listAttr(Search search) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//String key = map.get("key");
		
		String sido = search.getSido();
		String gugun = search.getGugun();
		String type = search.getType();
		String word = search.getWord();
		
//		if("userid".equals(key))
//			key = "b.user_id";
		param.put("sido", sido == null ? "" : sido);
		param.put("gugun", gugun == null ? "" : gugun);
		param.put("type", type == null ? "" : type);
		param.put("word", word == null ? "" : word);
		//param.put("word", map.get("word") == null ? "" : map.get("word"));
		int pgNo = Integer.parseInt(search.getPgno() == null ? "1" : search.getPgno());
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);

		return mapper.listAttr(param);
	}

	@Override
	public PageNavigation makePageNavigation(Search search) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE; //네비게이션 사이즈
		int sizePerPage = SizeConstant.LIST_SIZE; //한 페이지에 보여줄 관광지 정보의 갯수
		int currentPage = Integer.parseInt(search.getPgno() == null ? "1" : search.getPgno()); //현재 페이지

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
//		String key = map.get("key");
//		if ("userid".equals(key))
//			key = "user_id";
//		param.put("key", key == null ? "" : key);
		String sido = search.getSido();
		String gugun = search.getGugun();
		String type = search.getType();
		String word = search.getWord();
		
		param.put("sido", sido == null ? "" : sido);
		param.put("gugun", gugun == null ? "" : gugun);
		param.put("type", type == null ? "" : type);
		param.put("word", word == null ? "" : word);
		
		//param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = mapper.getTotalAttrCount(param); //조건에 맞는 총 관광지의 개수
		pageNavigation.setTotalCount(totalCount);
		
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}
	
}
