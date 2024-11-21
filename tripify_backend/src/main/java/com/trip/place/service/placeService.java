package com.trip.place.service;

import java.util.ArrayList;
import java.util.List;

import com.trip.place.util.PageNavigation;
import com.trip.place.vo.PlaceComment;
import com.trip.place.vo.PlaceCommentWithUserName;
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
	
	// 여행지 댓글 가져오기
    List<PlaceCommentWithUserName> getCommentsByPlaceId(Integer placeId);

	public Places getPlaceById(Integer placeId);
	
	// 여행지 댓글 작성
    Integer addPlaceComment(PlaceComment placeComment);
    
    void deleteComment(int commentId) throws Exception;
}
