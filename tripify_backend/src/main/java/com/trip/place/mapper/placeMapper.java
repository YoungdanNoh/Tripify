package com.trip.place.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.trip.place.vo.PlaceComment;
import com.trip.place.vo.PlaceCommentWithUserName;
import com.trip.place.vo.PlaceWithLikeStatus;
import com.trip.place.vo.Places;
import com.trip.place.vo.SidoGugunCode;
import com.trip.place.vo.Type;

@Mapper
public interface placeMapper {
	
	// 콘텐츠 타입 조회
    ArrayList<Type> typeSelect();

    // 시도 리스트 조회
    List<SidoGugunCode> getSido();

    // 특정 시도의 구군 리스트 조회
    List<SidoGugunCode> getGugunInSido(String sido);
	
    List<Places> listAttr(Map<String, Object> params);

    int getTotalAttrCount(Map<String, Object> params);

    List<PlaceCommentWithUserName> selectCommentsByPlaceId(int placeId);

    Places selectPlaceById(int placeId);

    Integer insertPlaceComment(PlaceComment placeComment);
    
    int deleteComment(int commentId);
    
 // 좋아요 추가
    void addLike(Map<String, Object> params);

    void updateLikeCount(int placeId);
    // 좋아요 삭제
    void removeLike(Map<String, Object> params);

    void decrementLikeCount(int placeId);
    
    // 좋아요 개수 조회
    int getLikeCount(int placeId);

	List<PlaceWithLikeStatus> getPlacesWithOptionalLikeStatus(Map<String, Object> params);
	
	List<PlaceWithLikeStatus> getLikedPlaces(int userId);
}
