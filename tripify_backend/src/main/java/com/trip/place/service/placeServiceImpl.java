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
}
