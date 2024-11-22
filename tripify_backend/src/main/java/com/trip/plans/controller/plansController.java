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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<String> addPlan(
			@RequestBody Plans p){
		
		try {
			service.addPlan(p);
			return new ResponseEntity<>("새 여행 계획 추가 성공", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("여행 계획 추가 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/plans/delete")
	public ResponseEntity<String> deletePlan(
			@RequestBody Plans p){
		try {
			service.deletePlan(p);
			return new ResponseEntity<>("여행 계획 삭제 성공", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("여행 계획 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
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
	        	if (detail.getVisit_date() != null) {
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
		            activity.put("visit_time", detail.getVisit_time());
		            activity.put("plan_place_id", detail.getPlan_place_id());
		            activity.put("place_name", detail.getPlace_name());
		            activity.put("description", detail.getDescription());
		            activity.put("order_in_day", detail.getOrder_in_day());
		            activities.add(activity);
	        	}
	            
	        }

	        // Itinerary를 응답에 추가
	        map.put("itinerary", new ArrayList<>(itineraryMap.values()));
	    }
		
		return map;
    }
	
	@PostMapping("/plans/detail/add")
	public ResponseEntity<String> addDetailPlan(
			@RequestBody PlanDetail p) {
		
		try {
			service.addDetailPlan(p);
			return new ResponseEntity<>("새로운 세부 여행 계획 추가 성공", HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("새로운 세부 여행 계획 추가 실패", HttpStatus.CREATED);
		}
		
	}
	
	@PostMapping("/plans/detail/addNewActivity")
	public ResponseEntity<String> addNewActivity(
			@RequestBody PlanDetail p) {
		
		try {
			service.addNewActivity(p);
			return new ResponseEntity<>("새로운 세부 여행 계획 추가 성공", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("새로운 세부 여행 계획 추가 실패", HttpStatus.CREATED);
		}
		
	}
	
	@PutMapping("/plans/detail/updateActivity")
	public ResponseEntity<String> updateActivity(
			@RequestBody PlanDetail p) {
		
		try {
			service.updateActivity(p);
			return new ResponseEntity<>("세부 여행 계획 수정 성공", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("세부 여행 계획 수정 실패", HttpStatus.CREATED);
		}
		
	}
	
	@DeleteMapping("/plans/detail/delete")
	public ResponseEntity<String> deleteActivity(
			@RequestBody PlanDetail p) {
		
		try {
			service.deleteActivity(p);
			return new ResponseEntity<>("새로운 세부 여행 계획 삭제 성공", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("새로운 세부 여행 계획 삭제 실패", HttpStatus.CREATED);
		}
		
	}
}
