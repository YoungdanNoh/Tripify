package com.trip.place.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trip.place.util.PageNavigation;
import com.trip.place.vo.Places;
import com.trip.place.vo.Search;
import com.trip.place.vo.Siguns;
import com.trip.place.vo.Type;

public interface placeService {

	public ArrayList<Siguns> sigunSelect() throws Exception;
	public ArrayList<Type> typeSelect() throws Exception;
	public List<Places> listAttr(Search search) throws Exception;
	public PageNavigation makePageNavigation(Search search) throws Exception;
}
