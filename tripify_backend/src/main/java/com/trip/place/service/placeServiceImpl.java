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
import com.trip.place.vo.PlaceComment;
import com.trip.place.vo.PlaceCommentWithUserName;
import com.trip.place.vo.PlaceWithLikeStatus;
import com.trip.place.vo.Places;
import com.trip.place.vo.Search;
import com.trip.place.vo.SidoGugunCode;
import com.trip.place.vo.Type;

@Service
public class placeServiceImpl implements placeService {

	@Autowired
	private placeMapper mapper;

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
		Map<String, Object> param = new HashMap<>();

		// 검색 조건 설정
		String sido = search.getSido();
		String gugun = search.getGugun();
		String type = search.getType();
		String word = search.getWord();

		param.put("sido", sido == null ? "" : sido);
		param.put("gugun", gugun == null ? "" : gugun);
		param.put("type", type == null ? "" : type);
		param.put("word", word == null ? "" : word);

		// 페이지네이션 계산
		int pgNo = Integer.parseInt(search.getPgno() == null ? "1" : search.getPgno());
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listSize", SizeConstant.LIST_SIZE);

		return mapper.listAttr(param);
	}

	@Override
	public PageNavigation makePageNavigation(Search search) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE; // 네비게이션 사이즈
		int sizePerPage = SizeConstant.LIST_SIZE; // 페이지당 항목 수
		int currentPage = Integer.parseInt(search.getPgno() == null ? "1" : search.getPgno()); // 현재 페이지

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);

		Map<String, Object> param = new HashMap<>();
		String sido = search.getSido();
		String gugun = search.getGugun();
		String type = search.getType();
		String word = search.getWord();

		param.put("sido", sido == null ? "" : sido);
		param.put("gugun", gugun == null ? "" : gugun);
		param.put("type", type == null ? "" : type);
		param.put("word", word == null ? "" : word);

		// 총 항목 수 계산
		int totalCount = mapper.getTotalAttrCount(param);
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

	@Override
	public List<PlaceCommentWithUserName> getCommentsByPlaceId(Integer placeId) {
		return mapper.selectCommentsByPlaceId(placeId);
	}

	@Override
	public Places getPlaceById(Integer placeId) {
		return mapper.selectPlaceById(placeId);
	}

	@Override
	public Integer addPlaceComment(PlaceComment placeComment) {
		return mapper.insertPlaceComment(placeComment);
	}

	@Override
	public void deleteComment(int commentId) throws Exception {
		int rowsAffected = mapper.deleteComment(commentId);
		if (rowsAffected == 0) {
			throw new Exception("해당 댓글을 찾을 수 없습니다.");
		}
	}

	@Override
	public List<PlaceWithLikeStatus> getPlaceListWithLikeStatus(Search search, Integer userId) {

		Map<String, Object> param = new HashMap<>();

		// 검색 조건 설정
		String sido = search.getSido();
		String gugun = search.getGugun();
		String type = search.getType();
		String word = search.getWord();

		param.put("sido", sido == null ? "" : sido);
		param.put("gugun", gugun == null ? "" : gugun);
		param.put("type", type == null ? "" : type);
		param.put("word", word == null ? "" : word);
		param.put("userId", userId);

		// 페이지네이션 계산
		int pgNo = Integer.parseInt(search.getPgno() == null ? "1" : search.getPgno());
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listSize", SizeConstant.LIST_SIZE);

		return mapper.getPlacesWithOptionalLikeStatus(param);
	}

	@Override
	public void addLike(int userId, int placeId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("placeId", placeId);

		// 'likes' 테이블에 새로운 레코드를 추가
		mapper.addLike(params);

		// 'attractions' 테이블의 like_count 증가
		mapper.updateLikeCount(placeId);
	}

	@Override
	public void removeLike(int userId, int placeId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("placeId", placeId);

		// 'likes' 테이블에서 레코드 삭제
		mapper.removeLike(params);

		// 'attractions' 테이블의 like_count 감소
		mapper.decrementLikeCount(placeId);
	}

	@Override
	public int getLikeCount(int placeId) {
		return mapper.getLikeCount(placeId);
	}

	@Override
	public List<PlaceWithLikeStatus> getLikedPlaces(int userId) {
		return mapper.getLikedPlaces(userId);
	}
}
