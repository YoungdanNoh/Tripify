package com.trip.plans.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trip.plans.service.plansService;
import com.trip.plans.vo.PlanDetail;
import com.trip.plans.vo.Plans;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:80"}, allowCredentials = "true")
@RestController
public class plansController {

	@Autowired
	plansService service;
	
	@PostMapping("/plans")
	public Map<String, Object> plans(
			@RequestBody Plans p) throws Exception {
		//int uId = Integer.parseInt(userId);
		//System.out.println(user.getUser_id());
		Map<String, Object> map = new HashMap<>();
		
		List<Plans> plans = service.plansSelect(p.getUser_id());
		
		map.put("plans", plans); //검색어
		
		return map;
	}
	
	@PostMapping("/plans/add")
	public ResponseEntity<String> insertPlan(
			@RequestBody Plans p){
		
		try {
			service.insertPlan(p);
			return new ResponseEntity<>("새 여행 계획 추가 성공", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("여행 계획 추가 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/plans/detail")
    public Map<String, Object> getPlanDetails(@RequestBody Plans p) {
		Map<String, Object> map = new HashMap<>();
		
		Map<String, Object> params = new HashMap<>();
        params.put("user_id", p.getUser_id());
        params.put("plan_id", p.getPlan_id());
		
		ArrayList<PlanDetail> plan = service.getPlanDetails(params);
		
		//map.put("planDetail", plan);
		if (!plan.isEmpty()) {
	        // 공통 계획 정보를 설정
	        PlanDetail firstDetail = plan.get(0);
	        map.put("plan_id", firstDetail.getPlan_id());
	        map.put("title", firstDetail.getTitle());
	        map.put("location", firstDetail.getLocation());
	        map.put("start_date", firstDetail.getStart_date());
	        map.put("end_date", firstDetail.getEnd_date());
	        map.put("img", firstDetail.getImg());

	     // Itinerary를 생성
	        Map<String, Map<String, Object>> itineraryMap = new LinkedHashMap<>();
	        for (PlanDetail detail : plan) {
	            String visitDate = detail.getVisit_date().toString();

	            // Itinerary에 visit_date별로 그룹화
	            if (!itineraryMap.containsKey(visitDate)) {
	                Map<String, Object> dayDetails = new HashMap<>();
	                dayDetails.put("visit_date", visitDate);
	                dayDetails.put("activities", new ArrayList<>());
	                itineraryMap.put(visitDate, dayDetails);
	            }

	            // 활동 추가
	            List<Map<String, Object>> activities = (List<Map<String, Object>>) itineraryMap.get(visitDate).get("activities");
	            Map<String, Object> activity = new HashMap<>();
	            activity.put("plan_place_id", detail.getPlan_place_id());
	            activity.put("place_name", detail.getPlace_name());
	            activity.put("description", detail.getDescription());
	            activities.add(activity);
	        }

	        // Itinerary를 응답에 추가
	        map.put("itinerary", new ArrayList<>(itineraryMap.values()));
	    }
		
		return map;
    }
}
