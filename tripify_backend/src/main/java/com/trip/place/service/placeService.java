package com.trip.place.service;

import java.util.ArrayList;
import java.util.List;

import com.trip.place.util.PageNavigation;
import com.trip.place.vo.PlaceComment;
import com.trip.place.vo.PlaceCommentWithUserName;
import com.trip.place.vo.PlaceWithLikeStatus;
import com.trip.place.vo.Places;
import com.trip.place.vo.Search;
import com.trip.place.vo.SidoGugunCode;
import com.trip.place.vo.Type;

public interface placeService {

	public ArrayList<Type> typeSelect() throws Exception;
	
	List<SidoGugunCode> getSido() throws Exception;
	List<SidoGugunCode> getGugunInSido(String sido) throws Exception;
	
	public List<PlaceWithLikeStatus> listAttr(Search search) throws Exception;
	public PageNavigation makePageNavigation(Search search) throws Exception;
	
	// 여행지 댓글 가져오기
    List<PlaceCommentWithUserName> getCommentsByPlaceId(Integer placeId);

	public PlaceWithLikeStatus getPlaceById(Integer placeId);
	
	// 여행지 댓글 작성
    Integer addPlaceComment(PlaceComment placeComment);
    
    void deleteComment(int commentId) throws Exception;
    
    // 좋아요 추가
    void addLike(int userId, int placeId);

    // 좋아요 삭제
    void removeLike(int userId, int placeId);

    // 좋아요 개수 조회
    int getLikeCount(int placeId);

//	List<PlaceWithLikeStatus> getPlaceListWithLikeStatus(Search search, Integer userId);
	
	List<PlaceWithLikeStatus> getLikedPlaces(int userId);
}
