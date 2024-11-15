package com.trip.place.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trip.place.util.PageNavigation;
import com.trip.place.vo.Places;
import com.trip.place.vo.Search;
import com.trip.place.vo.SidoGugunCode;
import com.trip.place.vo.Type;

public interface placeService {

	public ArrayList<Type> typeSelect() throws Exception;
	
	List<SidoGugunCode> getSido() throws Exception;
	List<SidoGugunCode> getGugunInSido(String sido) throws Exception;
	
	public List<Places> listAttr(Search search) throws Exception;
	public PageNavigation makePageNavigation(Search search) throws Exception;
}
