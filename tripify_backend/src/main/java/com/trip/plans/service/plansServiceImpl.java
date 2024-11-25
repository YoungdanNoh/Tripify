package com.trip.plans.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.plans.mapper.plansMapper;
import com.trip.plans.vo.PlanDetail;
import com.trip.plans.vo.Plans;
import com.trip.plans.vo.Today;

@Service
public class plansServiceImpl implements plansService {

	@Autowired
	plansMapper mapper;
	
	@Override
	public List<Today> todaySchedule(Plans p) throws Exception {
		return mapper.todaySchedule(p);
	}
	
	@Override
	public ArrayList<Plans> plansSelect(int userId) throws Exception {
		return mapper.plansSelect(userId);
	}

	@Override
	public void addPlan(Plans p) throws Exception {
		mapper.addPlan(p);
	}
	
	@Override
	public void modifyPlan(Plans p) throws Exception {
		mapper.modifyPlan(p);
	}
	
	@Override
	public void deletePlan(Plans p) throws Exception {
		mapper.deletePlan(p);
	}

	@Override
	public ArrayList<PlanDetail> getPlanDetails(Map<String, Object> params) throws Exception {
		return mapper.getPlanDetails(params);
	}

	@Override
	public void addDetailPlan(PlanDetail p) throws Exception {
		mapper.addDetailPlan(p);
	}
	
	@Override
	public void addNewActivity(PlanDetail p) throws Exception {
		mapper.addNewActivity(p);
	}
	
	@Override
	public void updateActivity(PlanDetail p) throws Exception {
		mapper.updateActivity(p);
	}
	
	@Override
	public void deleteActivity(PlanDetail p) throws Exception {
		mapper.deleteActivity(p);
	}

}
